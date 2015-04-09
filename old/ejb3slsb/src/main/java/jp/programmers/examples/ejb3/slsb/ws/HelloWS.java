package jp.programmers.examples.ejb3.slsb.ws;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;

import jp.programmers.examples.ejb3.slsb.HelloSLSB;

@Remote
@Stateless
@WebService
@SOAPBinding
public class HelloWS extends HelloSLSB implements HelloEndpoint {

    @WebMethod
    public String hello() {
        return super.hello();
    }

}
