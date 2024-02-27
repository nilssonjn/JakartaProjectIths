package com.example.jakartaeeiths.dto;

import com.example.jakartaeeiths.entity.Customer;

public record  CustomerDto (String firstName, String surname, int age) {


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
