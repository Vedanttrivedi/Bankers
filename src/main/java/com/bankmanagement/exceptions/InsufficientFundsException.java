package com.bankmanagement.exceptions;

public class InsufficientFundsException extends BankException {
    public InsufficientFundsException(double attemptedAmount, double balance) {
        super("Insufficient funds. Attempted: " + attemptedAmount + ", Available: " + balance);
    }
}
