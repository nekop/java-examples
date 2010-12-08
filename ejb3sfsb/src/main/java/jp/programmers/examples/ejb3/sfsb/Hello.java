package jp.programmers.examples.ejb3.sfsb;

public interface Hello {
    public String hello();
    public String hello(String name);
    public String getLastMessage();
    public void exception();
    public void destroy();
}
