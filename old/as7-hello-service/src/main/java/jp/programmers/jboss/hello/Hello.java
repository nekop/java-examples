package jp.programmers.jboss.hello;

public class Hello implements HelloMBean {
    public void create() throws Exception {
        System.out.println("Hello.create()");
    }
    public void start() throws Exception {
        System.out.println("Hello.start()");
    }
    public void stop() throws Exception {
        System.out.println("Hello.stop()");
    }
    public void destroy() throws Exception {
        System.out.println("Hello.destroy()");
    }
}
