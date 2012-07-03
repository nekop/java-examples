package jp.programmers.as7.examples;

import javax.ejb.EJB;

public class HelloClient {

    @EJB
    static Hello hello;

    public static void main(String[] args) throws Exception {
        hello.hello();
    }

}
