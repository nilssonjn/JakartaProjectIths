package com.example.jakartaeeiths.resource;

import com.example.jakartaeeiths.dto.CustomerDto;
import com.example.jakartaeeiths.dto.Customers;
import com.example.jakartaeeiths.entity.Customer;
import com.example.jakartaeeiths.repository.CustomerRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Path("/customers")
public class CustomerResource {
    CustomerRepository customerRepository;
    public CustomerResource() {
    }

    @Inject
    public CustomerResource(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

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

    /**READ*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Customers getAll() {
        return new Customers(
                customerRepository.getAll().stream().map(CustomerDto::map).toList(),
                LocalDateTime.now());
    }

    // CREATE
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(CustomerDto customerDto) {
        var c = customerRepository.add(CustomerDto.map(customerDto));

        return Response.created(URI.create("http://localhost:8080/JakartaEEiths-1.0-SNAPSHOT/api/customers" + c.getId()))
                .build();
    }







    // UPDATE

    // DELETE
}