package com.example.jakartaeeiths.dto;

import com.example.jakartaeeiths.entity.Customer;
import com.example.jakartaeeiths.validate.Age;
import jakarta.validation.constraints.NotEmpty;

public record  CustomerDto (@NotEmpty String firstName,
                            @NotEmpty String surname,
                            @Age(message = "Age must be between 0 and 150") int age) {

    public static CustomerDto map(Customer customer) {
        return new CustomerDto(customer.getCustomerFirstName(),
                customer.getCustomerSurname(),
                customer.getCustomerAge());
    }


    public static Customer map(CustomerDto customerDto){
        var customer = new Customer();
        customer.setCustomerFirstName(customerDto.firstName);
        customer.setCustomerSurname(customerDto.surname);
        customer.setCustomerAge(customerDto.age);
        return customer;
    }
}
