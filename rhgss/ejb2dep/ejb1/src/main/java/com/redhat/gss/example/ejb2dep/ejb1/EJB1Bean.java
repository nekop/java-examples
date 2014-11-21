package com.redhat.gss.example.ejb2dep.ejb1;

import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import com.redhat.gss.example.ejb2dep.ejb2.EJB2LocalHome;
import com.redhat.gss.example.ejb2dep.ejb2.EJB2Local;

/**
 * @ejb.bean name="EJB1" type="Stateless"
 * //@ejb.ejb-ref ejb-name="EJB2Local" view-type="local" ref-name="ejb/EJB2LocalHome"
 */
public class EJB1Bean implements SessionBean {

    private SessionContext ctx;

    /**
     * @ejb.interface-method view-type="local"
     */
    public String echo(String name) {
        try {
            EJB2LocalHome ejb2home = InitialContext.doLookup("java:comp/env/ejb/EJB2LocalHome");
            EJB2Local ejb2 = ejb2home.create();
            return ejb2.echo(name);
        } catch (Exception ex) {
            throw new RuntimeException("EJB2 invoke error", ex);
        }
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
