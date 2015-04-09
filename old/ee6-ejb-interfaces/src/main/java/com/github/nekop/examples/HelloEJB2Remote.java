package com.github.nekop.examples;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface HelloEJB2Remote extends EJBObject {
    public String hello(String name) throws RemoteException;
}
