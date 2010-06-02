package jp.programmers.examples.ejb3.slsb;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Remote
@Stateless
public class HelloSLSB implements Hello {

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

}
