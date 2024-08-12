package com.bankmanagement.models;

public class SavingsAccount extends Account {
    private static final double MIN_BALANCE = 500.0;

    public SavingsAccount(double initialAmount) {
        super("Savings", initialAmount);
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() - amount < MIN_BALANCE) {
            throw new IllegalArgumentException("Insufficient balance. Minimum balance required: " + MIN_BALANCE);
        }
        super.deposit(-amount);
    }
}
