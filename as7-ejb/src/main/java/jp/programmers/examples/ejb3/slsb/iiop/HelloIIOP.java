package jp.programmers.examples.ejb3.slsb.iiop;

import javax.ejb.RemoteHome;
import javax.ejb.Stateless;
import javax.ejb.SessionContext;
import javax.annotation.Resource;

@Stateless
@RemoteHome(HelloHome.class)
public class HelloIIOP {

    @Resource
    SessionContext ctx;

    public String hello() {
        System.out.println("HelloSLSB#hello()");
        return this.hello("world");
    }

    public String hello(String name) {
        System.out.println("HelloSLSB#hello(String)");
        System.out.println("name=" + name);
        return "Hello " + name;
    }

    public void exception() {
        throw new RuntimeException();
    }

    public void sleep(long msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException ignore) { }
    }

}
