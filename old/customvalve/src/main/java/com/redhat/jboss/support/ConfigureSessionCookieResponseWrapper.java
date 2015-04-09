package com.redhat.jboss.support;

import javax.servlet.http.Cookie;
import org.apache.catalina.Globals;
import org.apache.catalina.connector.Response;

public class ConfigureSessionCookieResponseWrapper extends ResponseWrapper {

    protected String cookiePath;
    protected String cookieDomain;
    protected String cookieSecure;

    public ConfigureSessionCookieResponseWrapper(Response res, String cookiePath, String cookieDomain, String cookieSecure) {
        super(res);
        this.cookiePath = cookiePath;
        this.cookieDomain = cookieDomain;
        this.cookieSecure = cookieSecure;
    }

    // Called from addCookie() and addCookieInternal() methods.
    protected void configureSessionCookie(Cookie cookie) {
        if (Globals.SESSION_COOKIE_NAME.equals(cookie.getName())) {
            if (cookiePath != null) {
                cookie.setPath(cookiePath);
            }
            if (cookieDomain != null) {
                cookie.setDomain(cookieDomain);
            }
            if (cookieSecure != null) {
                if (cookieSecure.equalsIgnoreCase("true")) {
                    cookie.setSecure(true);
                } else if (cookieSecure.equalsIgnoreCase("false")) {
                    cookie.setSecure(false);
                }
            }
        }
    }

    public void addCookie(Cookie cookie) {
        configureSessionCookie(cookie);
        res.addCookie(cookie);
    }

    public void addCookieInternal(Cookie cookie) {
        configureSessionCookie(cookie);
        res.addCookieInternal(cookie);
    }

}
