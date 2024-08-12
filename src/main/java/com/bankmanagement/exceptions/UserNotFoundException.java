package com.bankmanagement.exceptions;

public class UserNotFoundException extends BankException {
    public UserNotFoundException(String userId) {
        super("User not found with ID: " + userId);
    }
}
