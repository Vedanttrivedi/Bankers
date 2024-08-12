package com.bankmanagement.dao;

import com.bankmanagement.models.Customer;

import java.util.List;

public interface UserDAO {
    void save(Customer customer);
    Customer findById(String userId);
    boolean existsById(String userId);
    List<Customer> findAll();
}
