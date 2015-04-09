package com.github.nekop.examples;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface HelloEJB2Home extends EJBHome {
    public HelloEJB2Remote create() throws CreateException, RemoteException;
}
