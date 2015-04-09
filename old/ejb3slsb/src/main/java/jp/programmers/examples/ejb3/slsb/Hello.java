package jp.programmers.examples.ejb3.slsb;

import javax.ejb.Timer;

public interface Hello {
    public String hello();
    public String hello(String name);
    public void ejbTimeout(Timer timer);
    public void initTimer();
    public void exception();
    public void sleep(long msec);
}
