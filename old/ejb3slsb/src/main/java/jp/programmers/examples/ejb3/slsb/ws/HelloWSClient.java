package jp.programmers.examples.ejb3.slsb.ws;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class HelloWSClient {

    public static void main(String... args) throws Exception {
        URL wsdlLocation = new URL("http://127.0.0.1:8080/example-ejb3slsb/HelloWS?wsdl");
        QName serviceName = new QName("http://ws.slsb.ejb3.examples.programmers.jp/", "HelloWSService");
        Service service = Service.create(wsdlLocation, serviceName);
        QName portName = new QName("http://ws.slsb.ejb3.examples.programmers.jp/", "HelloWSPort");
        HelloEndpoint hello = service.getPort(portName, HelloEndpoint.class);
        hello.hello();
    }

}
