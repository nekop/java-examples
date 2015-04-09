package com.github.nekop.examples;

import javax.ejb.Stateless;
import javax.ejb.RemoteHome;
import javax.ejb.Stateless;
import javax.jws.WebService;

@Stateless
@RemoteHome(HelloEJB2Home.class)
@WebService
public class Hello implements HelloRemote, HelloLocal {

    String simpleName = getClass().getSimpleName();

    @Override
    public String hello(String name) {
        System.out.println(simpleName + "#hello(String)");
        System.out.println("name=" + name);
        return "Hello " + name;
    }
}
