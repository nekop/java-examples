package jp.programmers.examples;

import java.io.IOException;
import java.math.BigInteger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This filter issues additional cookie on login, and check it until
 * logout. In short, it's login time cookie. This gives us more robust
 * than single JSESSIONID cookie auth provided by a container.
 */
public class SessionFixationProtectionFilter implements Filter {

    public static final String COOKIE_NAME = "COOKIE_NAME";
    public static final String COOKIE_PATH = "COOKIE_PATH";
    public static final String COOKIE_DOMAIN = "COOKIE_DOMAIN";
    public static final String LOGIN_URL = "LOGIN_URL";
    public static final String LOGOUT_URL = "LOGOUT_URL";

    public static final String DEFAULT_LOGIN_URL = "/j_security_check";

    private String cookieName = null;
    private String cookiePath = null;
    private String cookieDomain = null;
    private String loginURL = null;
    private String logoutURL = null;

	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
        cookieName = filterConfig.getInitParameter(COOKIE_NAME);
        cookiePath = filterConfig.getInitParameter(COOKIE_PATH);
        cookieDomain = filterConfig.getInitParameter(COOKIE_DOMAIN);
        loginURL = filterConfig.getInitParameter(LOGIN_URL);
        if (loginURL == null) {
            loginURL = DEFAULT_LOGIN_URL;
        } 
        logoutURL = filterConfig.getInitParameter(LOGOUT_URL);

        if (cookieName == null) {
            throw new NullPointerException("cookieName is null");
        }
        if (logoutURL == null) {
            throw new NullPointerException("logoutURL is null");
        }
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        String user = getUser(req);
        
        if (req.getRequestURI().endsWith(loginURL) && user != null) {
            // just logged in!
            // going to set login cookie 
            HttpSession session = req.getSession(false);
            String value = md5(user + session.getId());
            Cookie cookie = new Cookie(cookieName, value);
            configureSessionCookie(cookie);
            res.addCookie(cookie);
        } else if  (user != null) {
            // this user is logging in
            // going to check login cookie 
            HttpSession session = req.getSession(false);
            boolean found = false;
            for (Cookie c : req.getCookies()) {
                if (c.getName().equals(cookieName)) {
                    String expectedValue = md5(user + session.getId());
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
        } else if (req.getRequestURI().endsWith(logoutURL)) {
            // logout
            // going to delete login cookie 
            Cookie cookie = new Cookie(cookieName, "");
            configureSessionCookie(cookie);
            cookie.setMaxAge(0);
            res.addCookie(cookie);
        } else {
            // this user is not logged in
            // do nothing
        }
        chain.doFilter(request, response);
    }

    protected String getUser(HttpServletRequest req) throws IOException, ServletException {
        return req.getRemoteUser();
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

    protected void configureSessionCookie(Cookie cookie) {
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

