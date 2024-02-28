package com.example.jakartaeeiths.resource;

import com.example.jakartaeeiths.dto.CustomerDto;
import com.example.jakartaeeiths.dto.Customers;
import com.example.jakartaeeiths.entity.Customer;
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
    public CustomerDto getOneById(@PathParam("id") long id) {
        return customerService.one(id);

    }

    // CREATE
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid CustomerDto customerDto) {
        var c = customerService.add(customerDto);

        return Response.created(
                URI.create("http://localhost:8080/JakartaEEiths-1.0-SNAPSHOT/api/customers" + c.getId()))
                .build();
    }



    // DELETE
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") long id) {
        customerService.deleteById(id);
        return Response.noContent().build();
    }


    // UPDATE
    @PATCH
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Customer update(@PathParam("id") long id, CustomerDto customerDto)  {
        return customerService.update(id, customerDto);
    }

    //https://jakarta.ee/learn/starter-guides/how-to-store-and-retrieve-data-using-jakarta-persistence/
}