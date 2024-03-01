package com.example.jakartaeeiths.resource;

import com.example.jakartaeeiths.dto.CustomerDto;
import com.example.jakartaeeiths.dto.Customers;
import com.example.jakartaeeiths.entity.Customer;
import com.example.jakartaeeiths.repository.CustomerRepository;
import com.example.jakartaeeiths.service.CustomerService;
import jakarta.ejb.Local;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


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
    @DisplayName("Server failure will cause 500")
    void serverFailureWillCause500() throws Exception {
        when(customerService.all()).thenThrow(new RuntimeException("Server failure"));

        // Create a mock request and response
        MockHttpRequest request = MockHttpRequest.get("/customers");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        // Assert the response status code is 500 (Internal Server Error)
        assertEquals(500, response.getStatus());
    }

    @Test
    @DisplayName("if customer is null then return 204")
    void ifCustomerIsNullThenReturn404() throws Exception {

        when(customerService.one(1L)).thenReturn(null);
        // use "one" method in customerService to test
        MockHttpRequest request = MockHttpRequest.get("/customers/1");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        CustomerDto customer = customerService.one(1L);
        assertNull(customer);

        assertEquals(204, response.getStatus());
    }
    @Test
    @DisplayName("update existing customer with PATCH returns 200")
    void updateReturnsStatus200() throws URISyntaxException, UnsupportedEncodingException {

        long customerId = 1L;
        CustomerDto updatedCustomerDto = new CustomerDto("UpdatedName", "UpdatedSurname", 50);
        when(customerService.update(customerId, updatedCustomerDto)).thenReturn(new Customer());


        MockHttpRequest request = MockHttpRequest.patch("/customers/" + customerId);
        request.contentType(MediaType.APPLICATION_JSON);
        request.content("{\"firstName\":\"UpdatedName\",\"surname\":\"UpdatedSurname\",\"age\":50}".getBytes());
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);


        assertEquals(200, response.getStatus());
    }
    //
    @Test
    @DisplayName("get one customer by id returns status 200")
    void getOneById_ReturnsStatus200() throws Exception {
        long customerId = 1L;
        CustomerDto customerDto = new CustomerDto("John", "Doe", 30);

        when(customerService.one(customerId)).thenReturn(customerDto);
        MockHttpRequest request = MockHttpRequest.get("/customers/" + customerId);
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        assertEquals(200, response.getStatus());

        assertEquals("{\"firstName\":\"John\",\"surname\":\"Doe\",\"age\":30}", response.getContentAsString());
    }
    @Test
    @DisplayName("get one customer by non-existing ID returns 404")
    void getOneByNonExistingId_ReturnsStatus404() throws URISyntaxException {
        long nonExistingId = 999L;
        when(customerService.one(nonExistingId)).thenThrow(new NotFoundException());

        MockHttpRequest request = MockHttpRequest.get("/customers/" + nonExistingId);
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(404, response.getStatus());
    }

}
