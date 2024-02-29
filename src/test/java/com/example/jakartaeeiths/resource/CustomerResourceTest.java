package com.example.jakartaeeiths.resource;

import com.example.jakartaeeiths.dto.CustomerDto;
import com.example.jakartaeeiths.dto.Customers;
import com.example.jakartaeeiths.entity.Customer;
import com.example.jakartaeeiths.service.CustomerService;
import jakarta.ejb.Local;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.resteasy.spi.Dispatcher;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class CustomerResourceTest {

    @Mock
    CustomerService customerService;

    ObjectMapper objectMapper;

    Dispatcher dispatcher;


    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        dispatcher = MockDispatcherFactory.createDispatcher();
        var resource = new CustomerResource(customerService);
        dispatcher.getRegistry().addSingletonResource(resource);

        // dispatcher.getProviderFactory().registerProvider(ConstraintViolationExceptionMapper.class);
    }
    
    @Test
    @DisplayName("create new customer with POST returns 201")
    void createReturnsStatus201() throws URISyntaxException, UnsupportedEncodingException {
        when(customerService.add(Mockito.any())).thenReturn(new Customer());
        // Create a mock request and response
        MockHttpRequest request = MockHttpRequest.post("/customers");
        request.contentType(MediaType.APPLICATION_JSON);
        // firstName surname and age must be the same as the variables in the dto
        request.content("{\"firstName\":\"Test\",\"surname\":\"LastName\",\"age\":100}".getBytes());
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        // Assert the response status code and content
        assertEquals(201, response.getStatus());
    }

    @Test
    @DisplayName("Delete customer with DELETE returns 204")
    void deleteCustomerWithDeleteReturns204() throws URISyntaxException {
        // Create a mock customer
        Customer mockCustomer = new Customer();
        when(customerService.add(Mockito.any())).thenReturn(mockCustomer);

        // Add the mock customer
        MockHttpRequest postRequest = MockHttpRequest.post("/customers");
        postRequest.contentType(MediaType.APPLICATION_JSON);
        postRequest.content("{\"firstName\":\"Test\",\"surname\":\"LastName\",\"age\":100}".getBytes());
        MockHttpResponse postResponse = new MockHttpResponse();
        dispatcher.invoke(postRequest, postResponse);

        // Get the created customer's ID (assuming it is returned in the response or can be retrieved from the service)
        long customerId = 1L;

        // Mock the deletion of the customer
        doNothing().when(customerService).deleteById(customerId);

        // Delete the customer
        MockHttpRequest deleteRequest = MockHttpRequest.delete("/customers/" + customerId);
        MockHttpResponse deleteResponse = new MockHttpResponse();
        dispatcher.invoke(deleteRequest, deleteResponse);

        // Assert the response status code
        assertEquals(204, deleteResponse.getStatus());
    }


    @Test
    @DisplayName("get customers returns status 200")
    void getCustomersReturnsStatus200() throws Exception {
        when(customerService.all()).thenReturn(new Customers(List.of(), LocalDateTime.now()));

        // Create a mock request and response
        MockHttpRequest request = MockHttpRequest.get("/customers");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        // Assert the response status code and content
        assertEquals(200, response.getStatus());
        assertEquals("{\"customerDtos\":[],\"updated\"}", response.getContentAsString());
    }

    @Test
    @DisplayName("Server failure will cause 500 v2")
    void serverFailureWillCause500V2 () throws Exception {
        when(customerService.all()).thenThrow(new RuntimeException("Server failure"));

        // Create a mock request and response
        MockHttpRequest request = MockHttpRequest.get("/customers");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        // Assert the response status code is 500 (Internal Server Error)
        assertEquals(500, response.getStatus());
    }

}