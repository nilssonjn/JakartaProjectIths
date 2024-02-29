package com.example.jakartaeeiths.service;
import com.example.jakartaeeiths.dto.CustomerDto;
import com.example.jakartaeeiths.dto.Customers;
import com.example.jakartaeeiths.entity.Customer;
import com.example.jakartaeeiths.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;

@ApplicationScoped
public class CustomerService {


    CustomerRepository customerRepository;

    public CustomerService() {

    }

    @Inject
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public CustomerDto one(long id) {
        var customer = customerRepository.findById(id);
        if (customer == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);

        if (customer.getCustomerAge() == null || customer.getCustomerSurname() == null || customer.getCustomerFirstName() == null)
            throw new WebApplicationException(Response.Status.FORBIDDEN);

        return CustomerDto.map(customer);
    }

    public Customers all() {
        try {
            return new Customers(
                    customerRepository.getAll().stream().map(CustomerDto::map).toList(),
                    LocalDateTime.now()
            );
        } catch (Exception e) {
            throw new WebApplicationException("Internal server error", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Customer add(CustomerDto customerDto) {
        var c = customerRepository.add(CustomerDto.map(customerDto));
        return c;
    }

    public Customer update(long id, CustomerDto customerDto) {
        var c = customerRepository.update(id, customerDto);
        return c;
    }

    public void deleteById(long id) {
        customerRepository.deleteById(id);
        // todo add http exception if you try to delete user that dont exist, now it will return 204 regardless
    }
}
