package com.example.jakartaeeiths.resource;

import com.example.jakartaeeiths.dto.CustomerDto;
import com.example.jakartaeeiths.dto.Customers;
import com.example.jakartaeeiths.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.LocalDateTime;

@Path("/customers")
public class CustomerResource {

    private CustomerService customerService;
    public CustomerResource() {
    }

    @Inject
    public CustomerResource(CustomerService customerRepository) {
        this.customerService = customerRepository;
    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response sayHello() {
//        String message = "Hello!";
//        return Response.ok(message).build();
//    }

    /**READ*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Customers getAll() {
        return customerService.all();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Customer getOneById(@PathParam("id") long id) {
        return customerRepository.findById(id);

    }

    // CREATE
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(CustomerDto customerDto) {
        var c = customerRepository.add(CustomerDto.map(customerDto));

        return Response.created(
                URI.create("http://localhost:8080/JakartaEEiths-1.0-SNAPSHOT/api/customers" + c.getId()))
                .build();
    }







    // UPDATE

    // DELETE
}