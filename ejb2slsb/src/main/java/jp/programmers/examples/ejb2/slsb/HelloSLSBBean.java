package jp.programmers.examples.ejb2.slsb;

import javax.ejb.SessionBean;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

/**
 * @ejb.bean name="HelloSLSB" type="Stateless"
 */
public class HelloSLSBBean implements javax.ejb.SessionBean {

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

    public void ejbCreate() { }
    public void ejbActivate() throws EJBException, RemoteException { }
    public void ejbPassivate() throws EJBException, RemoteException { }
    public void ejbRemove() throws EJBException, RemoteException { }
    public void setSessionContext(SessionContext context)
        throws EJBException, RemoteException { }
}
