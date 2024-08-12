package com.bankmanagement.models;

public abstract class Account {
    private final String accountType;
    private double balance;

    protected Account(String accountType, double initialAmount) {
        this.accountType = accountType;
        this.balance = initialAmount;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double b) {
        this.balance = b;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public abstract void withdraw(double amount);

    @Override
    public String toString() {
        return "Account{" +
                "accountType='" + accountType + '\'' +
                ", balance=" + balance +
                '}';
    }
}

