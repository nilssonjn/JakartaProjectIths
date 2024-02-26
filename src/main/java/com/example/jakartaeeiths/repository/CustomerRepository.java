package com.example.jakartaeeiths.repository;

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
    // query + @Transactional methods
}
