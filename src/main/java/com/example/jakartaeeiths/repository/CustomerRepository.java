package com.example.jakartaeeiths.repository;

import com.example.jakartaeeiths.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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

    // query + @Transactional methods
}
