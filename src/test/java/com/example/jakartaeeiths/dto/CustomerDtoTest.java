package com.example.jakartaeeiths.dto;

import com.example.jakartaeeiths.entity.Customer;
import jakarta.validation.Validator;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerDtoTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("Check that there is a valid customer")
    void checkThatThereIsAValidCustomer() {
        CustomerDto customer = new CustomerDto("John", "Doe", 27);

        var violations = validator.validate(customer);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Check that there is no valid customer")
    void checkThatThereIsNoValidCustomer() {
        CustomerDto customer = new CustomerDto("", "doe", 15);

        var violations = validator.validate(customer);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Cannot exceed max age")
    void cannotExceedMaxAge() {
        CustomerDto customer = new CustomerDto("john", "doe", 151);

        var violations = validator.validate(customer);
        String violationMessage = violations.iterator().next().getMessage();

        assertThat(violationMessage).contains("Age must be between 0 and 150");
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Age cannot be negative")
    void ageCannotBeNegative() {
        CustomerDto customer = new CustomerDto("john", "doe", -5);

        var violations = validator.validate(customer);
        String violationMessage = violations.iterator().next().getMessage();

        assertThat(violationMessage).contains("Age must be between 0 and 150");
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Customer recieves a name")
    void customerRecievesAName() {

        CustomerDto customerDto = new CustomerDto("test", "person", 15);

        Assertions.assertEquals("test", customerDto.firstName());
    }

    @Test
    @DisplayName("Mapping CustomerDto to Customer")
    void mappingCustomerDtoToCustomer() {
        CustomerDto customerDto = new CustomerDto("test", "person", 15);

        Customer customer = CustomerDto.map(customerDto);

        assertThat(customer.getCustomerFirstName()).isEqualTo("test");
        assertThat(customer.getCustomerSurname()).isEqualTo("person");
        assertThat(customer.getCustomerAge()).isEqualTo(15);
    }

    @Test
    @DisplayName("Mapping Customer to CustomerDto")
    void testMapCustomerToDto() {
        // Create a Customer instance
        Customer customer = new Customer();
        customer.setCustomerFirstName("John");
        customer.setCustomerSurname("Doe");
        customer.setCustomerAge(30);

        // Map Customer to CustomerDto

        CustomerDto customerDto = CustomerDto.map(customer);

        // Check if the mapping is correct
        assertThat(customerDto.firstName()).isEqualTo("John");
        assertThat(customerDto.surname()).isEqualTo("Doe");
        assertThat(customerDto.age()).isEqualTo(30);
    }
}