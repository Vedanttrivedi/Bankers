package com.bankmanagement.dao;
import com.bankmanagement.models.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TransactionDAOImp implements TransactionDAO
{

    private static TransactionDAOImp instance;
    private volatile Map<String, List<Transaction>> transactions;

    private TransactionDAOImp() {
        transactions= new ConcurrentHashMap<>();
    }

    public static TransactionDAOImp getInstance() {
        if (instance == null) {
            instance = new TransactionDAOImp();
        }
        return instance;
    }

    @Override
    public List<Transaction> lastNTransactions(String userId, int n) {
        List<Transaction> userTransactions = transactions.getOrDefault(userId, List.of());
        int size = userTransactions.size();
        return userTransactions.stream()
                .skip(Math.max(0, size - n))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getTransactionsByDateTime(String userId, LocalDate date) {
        return transactions.getOrDefault(userId, List.of()).stream()
                .filter(transaction -> transaction.timestamp().toLocalDate().isEqual(date))
                .collect(Collectors.toList());
    }
    @Override
    public List<Transaction> getTransactionsByDateTime(String userId, LocalDateTime datetime) {
        return transactions.getOrDefault(userId, List.of()).stream()
                .filter(transaction -> transaction.timestamp().toLocalDate().isEqual(ChronoLocalDate.from(datetime)))
                .collect(Collectors.toList());
    }


    @Override
    public synchronized void addTransaction(Transaction transaction) {
        transactions.computeIfAbsent(transaction.userId(), k -> new ArrayList<>()).add(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByUserId(String userId) {
        return transactions.getOrDefault(userId, new ArrayList<>());
    }
}
