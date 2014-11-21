package com.redhat.gss.example.ejb2dep.web;

import java.io.IOException;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.redhat.gss.example.ejb2dep.ejb1.EJB1LocalHome;
import com.redhat.gss.example.ejb2dep.ejb1.EJB1Local;

@WebServlet(urlPatterns={"/test"})
public class TestServlet extends HttpServlet {
    
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        try {
            EJB1LocalHome ejb1home = InitialContext.doLookup("java:comp/env/ejb/EJB1LocalHome");
            EJB1Local ejb1 = ejb1home.create();
            String result = ejb1.echo("foo");
            res.getWriter().println(result);
        } catch (Exception ex) {
            throw new RuntimeException("EJB1 invoke error", ex);
        }
    }
}
