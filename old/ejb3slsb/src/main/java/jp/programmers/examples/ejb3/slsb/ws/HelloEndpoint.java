package jp.programmers.examples.ejb3.slsb.ws;

import javax.jws.WebService;

@WebService
public interface HelloEndpoint {
    public String hello();
}
