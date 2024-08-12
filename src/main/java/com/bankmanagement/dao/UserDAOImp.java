package com.bankmanagement.dao;

import com.bankmanagement.models.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDAOImp implements UserDAO {
    private static UserDAOImp instance;
    private final Map<String, Customer> users = new ConcurrentHashMap<>();

    private UserDAOImp() {}

    public static UserDAOImp getInstance() {
        if (instance == null) {
            instance = new UserDAOImp();
        }
        return instance;
    }

    @Override
    public void save(Customer customer) {
        users.put(customer.getUserId(), customer);
    }

    @Override
    public Customer findById(String userId) {
        return users.get(userId);
    }

    @Override
    public boolean existsById(String userId) {
        return users.containsKey(userId);
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(users.values());
    }
}
