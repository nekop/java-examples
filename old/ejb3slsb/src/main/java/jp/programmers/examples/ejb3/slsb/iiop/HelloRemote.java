package jp.programmers.examples.ejb3.slsb.iiop;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

// Cannot extend Hello because we have to throw RemoteException in EJB 2.x :(
public interface HelloRemote extends EJBObject {
    public String hello() throws RemoteException;
    public String hello(String name) throws RemoteException;
    public void exception() throws RemoteException;
    public void sleep(long msec) throws RemoteException;
}
