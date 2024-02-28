package com.example.jakartaeeiths.service;

import com.example.jakartaeeiths.dto.CustomerDto;
import com.example.jakartaeeiths.dto.Customers;
import com.example.jakartaeeiths.entity.Customer;
import com.example.jakartaeeiths.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

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
            throw new NotFoundException("Invalid id " + id);
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
}
