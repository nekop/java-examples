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
