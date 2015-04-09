package jp.programmers.jboss.hello;

public interface HelloMBean {
    void create() throws Exception;
    void start() throws Exception;
    void stop() throws Exception;
    void destroy() throws Exception;
}
