package com.bankmanagement.services;

import com.bankmanagement.dao.TransactionDAO;
import com.bankmanagement.dao.UserDAO;
import com.bankmanagement.models.Transaction;
import com.bankmanagement.exceptions.UserNotFoundException;
import com.bankmanagement.exceptions.InsufficientFundsException;


public class TransactionService
{
    private final TransactionDAO transactionDAO;
    private final UserDAO userDAO;

    public TransactionService(TransactionDAO transactionDAO, UserDAO userDAO)
    {
        this.transactionDAO = transactionDAO;
        this.userDAO = userDAO;
    }

    public boolean addTransaction(Transaction transaction) throws UserNotFoundException{
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
        return true;
    }

}
