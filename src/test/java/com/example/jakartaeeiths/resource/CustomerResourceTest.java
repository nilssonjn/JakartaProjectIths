package com.example.jakartaeeiths.resource;

import com.example.jakartaeeiths.entity.Customer;
import com.example.jakartaeeiths.service.CustomerService;
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

    


}