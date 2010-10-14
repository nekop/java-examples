package com.redhat.jboss.support;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.apache.catalina.Session;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

/*
 * Work in progress, not tested.
 */
public class RenewSessionWhenFormAuthValve extends ValveBase {

	public void invoke(Request request, Response response)
        throws IOException, ServletException {
		if (request.getDecodedRequestURI().endsWith("/j_security_check")) {
            HttpSession oldHttpSession = request.getSession(false);
            if (oldHttpSession != null) {
                Session oldSession = request.getSessionInternal(false);
                Map<String, Object> attributes = copyAttributes(oldHttpSession);
                Map<String, Object> notes = copyNotes(oldSession);
                oldHttpSession.invalidate();
                HttpSession newHttpSession = request.getSession(true);
                for (Map.Entry<String,Object> e: attributes.entrySet()) {
                    newHttpSession.setAttribute(e.getKey(), e.getValue());
                }
                Session newSession = request.getSessionInternal(true);
                for (Map.Entry<String, Object> e: notes.entrySet()) {
                    newSession.setNote(e.getKey(), e.getValue());
                }
            }
	    }
	    getNext().invoke(request, response);
	}

    protected Map<String, Object> copyAttributes(HttpSession sess) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (Enumeration<String> e = sess.getAttributeNames(); e.hasMoreElements(); ) {
            String name = e.nextElement();
            result.put(name, sess.getAttribute(name));
        }
        return result;
    }

    protected Map<String, Object> copyNotes(Session sess) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (Iterator<String> it = sess.getNoteNames(); it.hasNext();) {
            String name = it.next();
            result.put(name, sess.getNote(name));
        }
        return result;
    }
}
