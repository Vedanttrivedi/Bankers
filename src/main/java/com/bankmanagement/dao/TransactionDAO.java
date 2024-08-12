package com.bankmanagement.dao;

import com.bankmanagement.models.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionDAO {
    void addTransaction(Transaction transaction);
    List<Transaction> getTransactionsByUserId(String userId);
    List<Transaction> lastNTransactions(String userId,int n);
    List<Transaction>getTransactionsByDateTime(String userId, LocalDateTime datetime);
    List<Transaction>getTransactionsByDateTime(String userId, LocalDate date);

}
