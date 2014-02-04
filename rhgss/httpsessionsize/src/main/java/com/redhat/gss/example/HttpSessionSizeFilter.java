/*
 * To the extent possible under law, Red Hat, Inc. has dedicated all
 * copyright to this software to the public domain worldwide, pursuant
 * to the CC0 Public Domain Dedication. This software is distributed
 * without any warranty.
 *
 * See <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package com.redhat.gss.example;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// For Java EE 6 / Servlet 3.0, remove them if needed
import javax.servlet.annotation.WebFilter;
@WebFilter("/*")

public class HttpSessionSizeFilter implements Filter {

    private Logger log =
        LogManager.getLogManager().getLogger(HttpSessionSizeFilter.class.getName());

    public void init(FilterConfig filterConfig) throws ServletException { }
    
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException {
        chain.doFilter(request, response);
        HttpSession session = ((HttpServletRequest)request).getSession(false);
        if (session == null) {
            return;
        }
        String id = session.getId();
        int size = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        try {
            int previous = 0; // for debug log
            for (Enumeration e = session.getAttributeNames();
                 e.hasMoreElements(); ) {
                String key = (String)e.nextElement();
                try {
                    oos.writeObject(session.getAttribute(key));
                    // If debug enabled, also print each attribute
                    if (log.isLoggable(Level.FINE)) {
                        oos.flush();
                        int current = baos.toByteArray().length;
                        log.log(Level.FINE,
                                "HttpSession attribute {0} = {1} bytes",
                                new Object[] {key, current - previous});
                        previous = current;
                    }
                } catch (NotSerializableException nse) {
                    LogRecord lr = new LogRecord(
                        Level.WARNING, "Failed to serialize HttpSession attribute {0}");
                    lr.setParameters(new Object[] {key});
                    lr.setThrown(nse);
                    log.log(lr);
                }
            }
            oos.flush();
            size = baos.toByteArray().length;
        } finally {
            try {
                oos.close();
            } catch (Exception ignore) { }
        }
        log.log(Level.INFO, "HttpSession {0} = {1} bytes", new Object[] {id, size});
    }

    public void destroy() { }

} 
