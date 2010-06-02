package jp.programmers.examples.ejb3.sfsb;

import javax.ejb.Stateful;
import javax.ejb.Remote;
import javax.ejb.Remove;

@Remote
@Stateful
public class HelloSFSB implements Hello {

    private String lastMessage;

    public String hello() {
        System.out.println("HelloSLSB#hello()");
        return this.hello("world");
    }

    public String hello(String name) {
        System.out.println("HelloSLSB#hello(String)");
        System.out.println("name=" + name);
        lastMessage = "Hello " + name;
        return lastMessage;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void exception() {
        throw new RuntimeException();
    }

    @Remove
    public void destroy() {
    }
}
