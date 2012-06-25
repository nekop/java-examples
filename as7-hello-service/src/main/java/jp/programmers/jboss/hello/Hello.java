package jp.programmers.jboss.hello;

public class Hello implements HelloMBean {
    public void start() throws Exception {
        System.out.println("Hello.start()");
    }
    public void stop() throws Exception {
        System.out.println("Hello.stop()");
    }
}
