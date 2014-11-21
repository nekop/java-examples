package com.redhat.gss.example.ejb2dep.ejb2;

import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * @ejb.bean name="EJB2" type="Stateless"
 */
public class EJB2Bean implements SessionBean {

    private SessionContext ctx;

    /**
     * @ejb.interface-method view-type="local"
     */
    public String echo(String name) {
        return name;
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
