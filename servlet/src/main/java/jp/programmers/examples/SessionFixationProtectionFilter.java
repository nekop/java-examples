package jp.programmers.examples;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This filter issues additional cookie on login, and check it until
 * logout. In short, it's login time cookie. This gives us more robust
 * than single JSESSIONID cookie auth provided by a container.
 *
 * In clustered environment, you should have unique SALT value accross
 * the cluster.
 */
public class SessionFixationProtectionFilter implements Filter {

    public static final String SALT = "SALT";
    public static final String COOKIE_NAME = "COOKIE_NAME";
    public static final String COOKIE_PATH = "COOKIE_PATH";
    public static final String COOKIE_DOMAIN = "COOKIE_DOMAIN";

    public static final String DEFAULT_COOKIE_NAME = "SessionFixationProtection";
    public static final String DEFAULT_SALT = String.valueOf(new Random().nextInt());

    private String salt = null;
    private String cookieName = null;
    private String cookiePath = null;
    private String cookieDomain = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        salt = filterConfig.getInitParameter(SALT);
        if (salt == null) {
            salt = DEFAULT_SALT;
        }
        cookieName = filterConfig.getInitParameter(COOKIE_NAME);
        if (cookieName == null) {
            cookieName = DEFAULT_COOKIE_NAME;
        }
        cookiePath = filterConfig.getInitParameter(COOKIE_PATH);
        cookieDomain = filterConfig.getInitParameter(COOKIE_DOMAIN);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;

        HttpSession session = req.getSession(false);
        if (session == null) {
            // no session, do nothing
            chain.doFilter(request, response);
            return;
        }
        String user = req.getRemoteUser();
        if (user == null) {
            // already logout, just clean up
            session.removeAttribute(cookieName);
            chain.doFilter(request, response);
            return;
        }
        String sessUser = (String)session.getAttribute(cookieName);
        if (!user.equals(sessUser)) {
            // switched user? remove previous info and go through
            session.removeAttribute(cookieName);
            sessUser = null;
        }
        
        if (sessUser == null) {
            // just logged in!
            // going to set login cookie 
            String value = md5(salt + session.getId());
            Cookie cookie = new Cookie(cookieName, value);
            configureLoginCookie(cookie);
            res.addCookie(cookie);
            // mark session as this user should have a login cookie
            session.setAttribute(cookieName, user);
        } else {
            // during login
            // going to check login cookie
            String expectedValue = md5(salt + session.getId());
            boolean found = false;
            for (Cookie c : req.getCookies()) {
                if (c.getName().equals(cookieName)) {
                    if (expectedValue.equals(c.getValue())) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                // possible session fixiation
                handleCookieNotFound(req, res, chain);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * Handles possible session fixiation. Calls HttpSession#invalidate() by default.
     */
    protected void handleCookieNotFound(HttpServletRequest req,
                                        HttpServletResponse res,
                                        FilterChain chain)
        throws IOException, ServletException {
        // force invalidate
        req.getSession().invalidate();
        chain.doFilter(req, res);
    }

    protected void configureLoginCookie(Cookie cookie) {
        cookie.setMaxAge(-1);
        if (cookiePath != null) {
            cookie.setPath(cookiePath);
        }
        if (cookieDomain != null) {
            cookie.setDomain(cookieDomain);
        }
    }

    @Override
    public void destroy() { }

    public static String md5(String s) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            byte[] data = s.getBytes(); 
            m.update(data, 0, data.length);
            BigInteger i = new BigInteger(1, m.digest());
            return String.format("%1$032X", i);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("no MD5", ex);
        }
    }
}

