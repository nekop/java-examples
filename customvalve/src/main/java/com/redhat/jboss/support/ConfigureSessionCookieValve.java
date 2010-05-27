package com.redhat.jboss.support;

import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

/**
 * This class allows you to configure session cookie.
 *
 * Why every property start with verbose annoying "cookie" prefix? Why
 * don't use "domain" instead of "cookieDomain"? Well, that's because
 * the ValveBase class has getDomain() method already!
 */
public class ConfigureSessionCookieValve extends ValveBase {
    
    protected String cookiePath = null;
    protected String cookieDomain = null;
    protected String cookieSecure = null;

    public void invoke(Request request, Response response)
        throws IOException, ServletException {
        Response wrapperResponse = new ConfigureSessionCookieResponseWrapper(response, cookiePath, cookieDomain, cookieSecure);
        try {
            request.setResponse(wrapperResponse);
            getNext().invoke(request, wrapperResponse);
        } finally {
            request.setResponse(response);
        }
    }

    public String getCookiePath() {
        return cookiePath;
    }

    public void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    public String getCookieSecure() {
        return cookieSecure;
    }

    public void setCookieSecure(String cookieSecure) {
        this.cookieSecure = cookieSecure;
    }
}
