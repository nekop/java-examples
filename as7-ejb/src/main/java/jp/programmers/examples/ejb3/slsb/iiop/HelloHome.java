package jp.programmers.examples.ejb3.slsb.iiop;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface HelloHome extends EJBHome {
    public HelloRemote create() throws CreateException, RemoteException;
}
