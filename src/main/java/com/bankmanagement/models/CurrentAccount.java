package com.bankmanagement.models;

public class CurrentAccount extends Account {
    public CurrentAccount(double initialAmount) {
        super("Current", initialAmount);
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds.");
        }
        super.deposit(-amount);
    }
}

