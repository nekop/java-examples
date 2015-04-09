package jp.programmers.examples.ejb2.slsb;

import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.TimedObject;
import javax.ejb.Timer;

/**
 * @ejb.bean name="HelloSLSB" type="Stateless"
 */
public class HelloSLSBBean implements SessionBean, TimedObject {

    private SessionContext ctx;

    /**
     * @ejb.interface-method view-type="remote"
     */
    public String hello() {
        System.out.println("HelloSLSB#hello()");
        return this.hello("world");
    }

    /**
     * @ejb.interface-method view-type="remote"
     */
    public String hello(String name) {
        System.out.println("HelloSLSB#hello(String)");
        System.out.println("name=" + name);
        return "Hello " + name;
    }

    /**
     * @ejb.interface-method view-type="local"
     */
    public String helloLocal() {
        System.out.println("HelloSLSB#helloLocal()");
        return this.hello("world");
    }

    /**
     * @ejb.interface-method view-type="local"
     */
    public String helloLocal(String name) {
        System.out.println("HelloSLSB#helloLocal(String)");
        System.out.println("name=" + name);
        return "Hello " + name;
    }

    /**
     * @ejb.interface-method view-type="remote"
     */
    public Object echo(Object o) {
        return o;
    }

    /**
     * @ejb.interface-method view-type="local"
     */
    public Object echoLocal(Object o) {
        return o;
    }

    /**
     * @ejb.interface-method view-type="remote"
     */
    public void sleep(int msec) {
        System.out.println("HelloSLSB#sleep(int)");
        System.out.println("msec=" + msec);
        try {
            Thread.sleep(msec);
        } catch (InterruptedException ignore) { }
    }

    /**
     * @ejb.interface-method view-type="remote"
     */
    public void exception() {
        throw new RuntimeException();
    }

    /**
     * @ejb.interface-method view-type="remote"
     */
    public void initTimer() {
        ctx.getTimerService().createTimer(0, 20 * 1000, null);
    }

    /**
     * @ejb.interface-method view-type="remote"
     */
    public void ejbTimeout(Timer timer) {
        System.out.println("HelloSLSB#ejbTimeout(Timer)");
        System.out.println("timer=" + timer);
    }

    public void ejbCreate() { }
    public void ejbActivate() throws EJBException, RemoteException { }
    public void ejbPassivate() throws EJBException, RemoteException { }
    public void ejbRemove() throws EJBException, RemoteException { }
    public void setSessionContext(SessionContext context)
        throws EJBException, RemoteException {
        this.ctx = context;
    }
}
