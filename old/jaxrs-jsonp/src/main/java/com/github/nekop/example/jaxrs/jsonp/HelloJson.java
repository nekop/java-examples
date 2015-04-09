package com.github.nekop.example.jaxrs.jsonp;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloJson {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Object hello() {
        System.out.println("HelloJson#hello()");
        return new RestMessage("hello");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object echo(RestMessage message) {
        System.out.println("HelloJson#echo()");
        return message;
    }

}
