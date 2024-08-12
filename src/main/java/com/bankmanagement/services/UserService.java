package com.bankmanagement.services;

import com.bankmanagement.dao.UserDAO;
import com.bankmanagement.exceptions.AuthenticationException;
import com.bankmanagement.models.Account;
import com.bankmanagement.models.Customer;
import com.bankmanagement.models.SavingsAccount;
import com.bankmanagement.models.CurrentAccount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void showProfile(String userId){
        System.out.println(userDAO.findById(userId));
    }
    public String register(String fullName, String password, String aadhaarNumber, String accountType, double initialAmount) {
        var userId = UUID.randomUUID().toString();
        var account = switch (accountType.toLowerCase()) {
            case "savings" -> new SavingsAccount(initialAmount);
            case "current" -> new CurrentAccount(initialAmount);
            default -> throw new IllegalArgumentException("Invalid account type: " + accountType);
        };
        var customer = new Customer(userId, fullName, password, aadhaarNumber, account);
        userDAO.save(customer);
        return userId;
    }

    public boolean login(String userId, String password) throws AuthenticationException {
        Customer customer = userDAO.findById(userId);
        if (customer != null && customer.getPassword().equals(password)) {
            return true;
        } else {
            throw new AuthenticationException("Invalid User ID or Password.");
        }
    }

}
