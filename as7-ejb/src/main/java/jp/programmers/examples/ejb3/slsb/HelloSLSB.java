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

    @Override
    public String hello() {
        System.out.println(simpleName + "#hello()");
        return this.hello("world");
    }

    @Override
    public String hello(String name) {
        System.out.println(simpleName + "#hello(String)");
        System.out.println("name=" + name);
        return "Hello " + name;
    }

    @Timeout
    @Override
    public void ejbTimeout(Timer timer) {
        System.out.println(simpleName + "#ejbTimeout(Timer)");
        System.out.println("timer=" + timer);
    }

    @Override
    public void initTimer() {
        ctx.getTimerService().createTimer(0, 20 * 1000, null);
    }

    @Override
    public void exception() {
        throw new RuntimeException();
    }

    @Override
    public void sleep(long msec) {
        System.out.println(simpleName + "#sleep()");
        try {
            Thread.sleep(msec);
        } catch (InterruptedException ignore) { }
    }

}
