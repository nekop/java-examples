package com.github.nekop.examples;

import javax.ejb.Local;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.jws.WebService;

@Local
@Path("/hello")
@WebService
public interface HelloLocal {
    @GET
	@Path("{name}")
    String hello(@PathParam("name") String name);
}
