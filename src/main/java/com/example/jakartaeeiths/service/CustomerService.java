package com.example.jakartaeeiths.service;

import com.example.jakartaeeiths.dto.CustomerDto;
import com.example.jakartaeeiths.dto.Customers;
import com.example.jakartaeeiths.entity.Customer;
import com.example.jakartaeeiths.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
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
        if (customer==null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);

        if (customer.getCustomerAge() == null || customer.getCustomerSurname() == null || customer.getCustomerFirstName() == null)
            throw new WebApplicationException(Response.Status.FORBIDDEN);

        return CustomerDto.map(customer);
    }
    public Customers all() {
        return new Customers(
                customerRepository.getAll().stream().map(CustomerDto::map).toList(),
                LocalDateTime.now()
        );
    }
    public Customer add(CustomerDto customerDto){
        var p = customerRepository.add(CustomerDto.map(customerDto));
        return p;
    }

    public Customer update(long id, CustomerDto customerDto) {
        var p = customerRepository.update(id, customerDto);
        return p;
    }

    public void deleteById(long id) {
      customerRepository.deleteById(id);
    }
}
