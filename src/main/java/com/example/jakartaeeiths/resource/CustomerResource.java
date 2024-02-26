package com.example.jakartaeeiths.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/hello")
public class CustomerResource {

//    @GET
//    @Produces("text/plain")
//    public String hello() {
//        return "Hello, World!";
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sayHello() {
        String message = "Hello!";
        return Response.ok(message).build();
    }
}