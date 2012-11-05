package jp.programmers.examples.ejb3.slsb;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.SessionContext;
import javax.annotation.Resource;

@Remote
@Stateless
public class HelloSLSB implements Hello {

    @Resource
    SessionContext ctx;

    String simpleName = getClass().getSimpleName();

    public String hello() {
        System.out.println(simpleName + "#hello()");
        return this.hello("world");
    }

    public String hello(String name) {
        System.out.println(simpleName + "#hello(String)");
        System.out.println("name=" + name);
        return "Hello " + name;
    }

    @Timeout
    public void ejbTimeout(Timer timer) {
        System.out.println(simpleName + "#ejbTimeout(Timer)");
        System.out.println("timer=" + timer);
    }

    public void initTimer() {
        ctx.getTimerService().createTimer(0, 20 * 1000, null);
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
