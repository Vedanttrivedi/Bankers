package com.bankmanagement.services;

import com.bankmanagement.dao.TransactionDAO;
import com.bankmanagement.dao.UserDAO;
import com.bankmanagement.models.Transaction;
import com.bankmanagement.exceptions.UserNotFoundException;
import com.bankmanagement.exceptions.InsufficientFundsException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class TransactionService {
    private final TransactionDAO transactionDAO;
    private final UserDAO userDAO;

    public TransactionService(TransactionDAO transactionDAO, UserDAO userDAO) {
        this.transactionDAO = transactionDAO;
        this.userDAO = userDAO;
    }

    public void addTransaction(Transaction transaction) {
        var userId = transaction.userId();
        if (!userDAO.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        var customer = userDAO.findById(userId);
        if (transaction.type() == Transaction.TransactionType.DEPOSIT) {
            customer.getAccount().deposit(transaction.amount());
        } else if (transaction.type() == Transaction.TransactionType.WITHDRAW) {
            if (customer.getAccount().getBalance() < transaction.amount()) {
                throw new InsufficientFundsException(transaction.amount(), customer.getAccount().getBalance());
            }
            customer.getAccount().withdraw(transaction.amount());
        }
        transactionDAO.addTransaction(transaction);
    }

    public void loadTransactionsFromFile(String filePath) throws IOException {
        var lines = Files.readAllLines(Paths.get(filePath));
        for (var line : lines) {
            var parts = line.split(",");
            if (parts.length != 4) continue;
            var userId = parts[0];
            var timestamp = LocalDateTime.parse(parts[1]);
            var amount = Double.parseDouble(parts[2]);
            var type = Transaction.TransactionType.valueOf(parts[3].toUpperCase());
            var transaction = new Transaction(userId, timestamp, amount, type);
            addTransaction(transaction);
        }
    }
}
