package jp.programmers.examples;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// http://localhost:8080/example-rest/rest/hello

@Path("/hello")
public class HelloRest {

    @GET
    @Produces(MediaType.APPLICATION_XML + "; charset=UTF-8")
    public Object hello() {
        System.out.println("HelloSLSB#hello()");
        return "<xml>はろー</xml>";
    }

}
