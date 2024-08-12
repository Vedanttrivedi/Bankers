package com.bankmanagement.models;

import java.time.LocalDateTime;

public record Transaction(String userId, LocalDateTime timestamp, double amount, TransactionType type) {
    @Override
    public String toString() {
        return "Transaction{" +
                "userId='" + userId + '\'' +
                ", timestamp=" + timestamp +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }

    public enum TransactionType {
        DEPOSIT, WITHDRAW
    }

}

