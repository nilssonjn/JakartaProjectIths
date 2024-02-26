package com.example.jakartaeeiths.dto;

import com.example.jakartaeeiths.entity.Customer;

import java.time.LocalDateTime;
import java.util.List;


public record Customers (List<Customer> customers, LocalDateTime updated) {}

