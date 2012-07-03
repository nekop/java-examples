package jp.programmers.as7.examples;

import javax.ejb.Timer;

public interface Hello {
    public String hello();
    public String hello(String name);
    public void ejbTimeout(Timer timer);
    public void initTimer();
    public void exception();
    public void sleep(long msec);
}
