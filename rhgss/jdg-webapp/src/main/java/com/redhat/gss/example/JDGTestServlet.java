/*
 * To the extent possible under law, Red Hat, Inc. has dedicated all
 * copyright to this software to the public domain worldwide, pursuant
 * to the CC0 Public Domain Dedication. This software is distributed
 * without any warranty.
 *
 * See <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package com.redhat.gss.example;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebServlet("/jdgtest")
public class JDGTestServlet extends HttpServlet {

    private Logger log =
        LogManager.getLogManager().getLogger(JDGTestServlet.class.getName());

    // TODO: Implement basic Infinispan stuff
    public void doGet(ServletRequest request,
                      ServletResponse response)
        throws IOException, ServletException {
    }

} 
