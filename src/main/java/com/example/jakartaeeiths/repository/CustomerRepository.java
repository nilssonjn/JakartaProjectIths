package com.example.jakartaeeiths.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;

@ApplicationScoped
public class CustomerRepository implements Serializable {

    @PersistenceContext(unitName = "mysql")
    EntityManager entityManager;

    // query + @Transactional methods
}
