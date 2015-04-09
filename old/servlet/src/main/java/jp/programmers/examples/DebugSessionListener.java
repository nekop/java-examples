package jp.programmers.examples;

import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;

public class DebugSessionListener
    implements HttpSessionBindingListener, HttpSessionActivationListener,
               HttpSessionListener, HttpSessionAttributeListener,
               Serializable {

    // HttpSessionBindingListener methods, need to be set by HttpSession.setAttribute()

    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println("DebugSessionListener.valueBound");
    }
    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println("DebugSessionListener.valueUnbound");
    }

    // HttpSessionActivationListener methods, need to be set by HttpSession.setAttribute()

    public void sessionDidActivate(HttpSessionEvent se)  {
        System.out.println("DebugSessionListener.sessionDidActivate");
    }
    public void sessionWillPassivate(HttpSessionEvent se) {
        System.out.println("DebugSessionListener.sessionWillPassivate");
    }

    // HttpSessionListener methods, need to be registered by ServletContext.addListener()

    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("DebugSessionListener.sessionCreated");
    }
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("DebugSessionListener.sessionDestroyed");
    }

    // HttpSessionAttributeListener methods, need to be registered by ServletContext.addListener()

    public void attributeAdded(HttpSessionBindingEvent event) {
        System.out.println("DebugSessionListener.attributeAdded");
    }
    public void attributeRemoved(HttpSessionBindingEvent event) {
        System.out.println("DebugSessionListener.attributeRemoved");
    }
    public void attributeReplaced(HttpSessionBindingEvent event) {
        System.out.println("DebugSessionListener.attributeReplaced");
    }

}
