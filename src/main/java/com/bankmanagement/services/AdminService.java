package com.bankmanagement.services;

import com.bankmanagement.dao.TransactionDAOImp;
import com.bankmanagement.dao.UserDAO;
import com.bankmanagement.dao.TransactionDAO;
import com.bankmanagement.models.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

public class AdminService {
    private final UserDAO userDAO;
    private final TransactionDAO transactionDAO;
    private final String path = "/home/vedant/Documents/Bankers/src/assets/";

    public AdminService(UserDAO userDAO, TransactionDAO transactionDAO) {
        this.userDAO = userDAO;
        this.transactionDAO = transactionDAO;
    }

    public void loadUsersFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(path+filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String userId = parts[0];
                    String fullName = parts[1];
                    String password = parts[2];
                    String aadharNumber = parts[3];
                    String accountType = parts[4];
                    double initialAmount = Double.parseDouble(parts[5]);

                    Account act;
                    if(accountType.equals("S"))
                        act = new SavingsAccount(initialAmount);
                    else
                        act = new CurrentAccount(initialAmount);
                    Customer user = new Customer(userId, fullName, password, aadharNumber, act);
                    userDAO.save(user);
                } else {
                    System.out.println("Invalid user data format: " + line);
                }
            }
            System.out.println("Users loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        }
    }

    public void loadTransactionsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(path+filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String userId = parts[0];
                    LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
                    double amount = Double.parseDouble(parts[2]);
                    String transactionType = parts[3];
                    if(userDAO.existsById(userId)){
                        Transaction transaction = new Transaction(userId, timestamp, amount, transactionType=="Deposit"? Transaction.TransactionType.DEPOSIT: Transaction.TransactionType.WITHDRAW);
                        transactionDAO.addTransaction(transaction);
                    }
                    System.err.println("User With Id Does Exists!");
                } else {
                    System.out.println("Invalid transaction data format: " + line);
                }
            }
            System.out.println("Transactions loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error reading transaction file: " + e.getMessage());
        }
    }
    public void getLastNTransaction(String userId,int n){
        transactionDAO.lastNTransactions(userId,n).forEach(e-> System.out.println(e));
    }
}

