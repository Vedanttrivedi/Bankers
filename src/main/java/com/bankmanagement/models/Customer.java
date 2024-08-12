package com.bankmanagement.models;
import java.util.UUID;

public class Customer {
    private final String userId;
    private final String fullName;
    private final String password;
    private final String aadhaarNumber;
    private final Account account;

    public Customer(String userId, String fullName, String password, String aadhaarNumber, Account account) {
        this.userId = userId;
        this.fullName = fullName;
        this.password = password;
        this.aadhaarNumber = aadhaarNumber;
        this.account = account;

    }

    public String getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", aadhaarNumber='" + aadhaarNumber + '\'' +
                ", account=" + account +
                '}';
    }
}
