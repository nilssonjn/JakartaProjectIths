package com.example.jakartaeeiths.repository;

import com.example.jakartaeeiths.dto.CustomerDto;
import com.example.jakartaeeiths.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class CustomerRepository implements Serializable {

    @PersistenceContext(unitName = "mysql")
    EntityManager entityManager;

    public List<Customer> getAll() {
        return entityManager
                .createQuery("select c from Customer c", Customer.class)
                .getResultList();
    }

    @Transactional
    public Customer add(Customer customer) {
        entityManager.persist(customer);
        return customer;
    }
    
    public Customer findById(long id) {
        return entityManager.find(Customer.class, id);
    }

    /**deletes customer*/
    @Transactional
    public void deleteById(long id) {
        // Retrieve the customer object using the ID
        Customer customer = entityManager.find(Customer.class, id);

        // Check if customer exists before deletion
        if (customer != null) {
            // Remove the customer
            entityManager.remove(customer);
        } else {
            System.out.println("error");
        }
    }

    @Transactional
    public Customer update(long id, CustomerDto customerDto) {
        Customer customer = entityManager.find(Customer.class, id);
        customer.setCustomerFirstName(customerDto.firstName());
        entityManager.flush();
        return customer;
    }

}
