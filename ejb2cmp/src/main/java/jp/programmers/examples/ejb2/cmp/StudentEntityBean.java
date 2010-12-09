package jp.programmers.examples.ejb2.cmp;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

/**
 * @ejb.bean
 *   name="Student"
 *   type="CMP"
 *   cmp-version="2.x"
 *   view-type="local"
 *   reentrant="false"
 *   local-jndi-name="Student"
 * @ejb.util generate="physical"
 * @ejb.pk generate="true"
 * @ejb.persistence table-name="STUDENT"
 * @jboss.persistence
 *   create-table="true"
 *   remove-table="false"
 */
public abstract class StudentEntityBean implements EntityBean {

    private EntityContext ctx;

    /**
     * @ejb.pk-field
     * @ejb.persistent-field
     * @ejb.interface-method
     * @ejb.persistence column-name="ID"
     */
    public abstract Integer getId();
    public abstract void setId(Integer id);

    /**
     * @ejb.persistent-field
     * @ejb.interface-method
     * @ejb.persistence column-name="NAME"
     */
    public abstract String getName();
    public abstract void setName(String name);

    // EntityBean implementation ------------------------------------
    /**
     * @ejb.create-method
     */
    public StudentPK ejbCreate(StudentPK pk) throws CreateException {
        setId(pk.getId());
        return null;
    }

    public void ejbPostCreate(StudentPK pk) {}
    public void ejbActivate() throws EJBException, RemoteException {}
    public void ejbLoad() throws EJBException, RemoteException {}
    public void ejbPassivate() throws EJBException, RemoteException {}
    public void ejbRemove() throws RemoveException, EJBException, RemoteException {}
    public void ejbStore() throws EJBException, RemoteException {}

    public void setEntityContext(EntityContext ctx) throws EJBException, RemoteException {
        this.ctx = ctx;
    }

    public void unsetEntityContext() throws EJBException, RemoteException {
        this.ctx = null;
    }
}

