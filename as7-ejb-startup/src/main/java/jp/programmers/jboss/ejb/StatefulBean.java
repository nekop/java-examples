package jp.programmers.jboss.ejb;

import javax.ejb.Stateful;
import javax.ejb.Remove;
import javax.ejb.SessionSynchronization;
import javax.ejb.EJBException;

@Stateful
public class StatefulBean implements SessionSynchronization {

    public void test() {
        System.out.println("StatefulBean.test()");
    }

    @Remove
    public void remove() {
        System.out.println("StatefulBean.remove()");
    }

    public void afterBegin() throws EJBException, java.rmi.RemoteException {
        System.out.println("StatefulBean.afterBegin()");
    }
    public void beforeCompletion() throws EJBException, java.rmi.RemoteException {
        System.out.println("StatefulBean.beforeCompletion()");
    }
    public void afterCompletion(boolean committed) throws EJBException, java.rmi.RemoteException {
        System.out.println("StatefulBean.afterCompletion()");
    }
}
