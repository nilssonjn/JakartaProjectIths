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

@Path("/customers")
public class CustomerResource {
    CustomerRepository customerRepository;
    public CustomerResource() {
    }

    @Inject
    public CustomerResource(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

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

        return Response.created(URI.create("http://localhost:8080/JakartaEEiths-1.0-SNAPSHOT/api/customers" + c.getId()))
                .build();
    }



    // DELETE
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") long id) {
        customerRepository.deleteById(id);
        return Response.noContent().build();
    }


    // UPDATE
    @PATCH
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Customer update(@PathParam("id") long id, CustomerDto customerDto)  {
        return customerRepository.update(id, customerDto);
    }

    //https://jakarta.ee/learn/starter-guides/how-to-store-and-retrieve-data-using-jakarta-persistence/
}