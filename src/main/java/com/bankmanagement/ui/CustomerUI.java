package com.bankmanagement.ui;

import com.bankmanagement.exceptions.BankException;
import com.bankmanagement.services.UserService;
import com.bankmanagement.services.TransactionService;
import com.bankmanagement.models.Transaction;
import com.bankmanagement.dao.UserDAOImp;
import com.bankmanagement.dao.TransactionDAOImp;

import java.io.Console;
import java.util.Scanner;

public class CustomerUI
{
    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService = new UserService(UserDAOImp.getInstance());
    private final TransactionService transactionService = new TransactionService(TransactionDAOImp.getInstance(), UserDAOImp.getInstance());

    public void start()
    {
        while (true) {
            System.out.println("===== Customer Menu =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");

            var option = scanner.nextLine();
            switch (option) {
                case "1" -> register();
                case "2" -> login();
                case "3" -> {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void register() {
        try {
            System.out.print("Enter Full Name: ");
            var fullName = scanner.nextLine();
            System.out.print("Enter Password: ");
            var password = scanner.nextLine();
            System.out.print("Enter Aadhaar Number: ");
            var aadhaarNumber = scanner.nextLine();
            System.out.print("Enter Account Type (Savings/Current): ");
            var accountType = scanner.nextLine();
            System.out.print("Enter Initial Amount: ");
            var initialAmount = Double.parseDouble(scanner.nextLine());

            var userId = userService.register(fullName, password, aadhaarNumber, accountType, initialAmount);
            System.out.println("Registration successful! Your User ID: " + userId);
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }
 private void login() {
        try {
            System.out.print("Enter User ID: ");
            var userId = scanner.nextLine();

            Console console = System.console();
            String password;

            if (console != null) {
                char[] passwordChars = console.readPassword("Enter Password: ");
                password = new String(passwordChars);
            } else {
                System.out.print("Enter Password: ");
                password = scanner.nextLine();
            }

            if (userService.login(userId, password)) {
                System.out.println("Login successful!");

                while (true) {
                    System.out.println("===== Account Menu =====");
                    System.out.println("1. Deposit");
                    System.out.println("2. Withdraw");
                    System.out.println("3. Profile");
                    System.out.println("4. Logout");

                    System.out.print("Choose an option: ");

                    var option = scanner.nextLine();
                    switch (option) {
                        case "1" -> deposit(userId);
                        case "2" -> withdraw(userId);
                        case "3" -> profile(userId);
                        case "4" -> {
                            System.out.println("Logging out...");
                            return;
                        }
                        default -> System.out.println("Invalid option. Please try again.");
                    }
                }
            } else {
                System.out.println("Invalid User ID or Password.");
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }
    private void profile(String userId){
        userService.showProfile(userId);
    }
    private void deposit(String userId) {
        try {
            System.out.print("Enter Deposit Amount: ");
            var amount = Double.parseDouble(scanner.nextLine());
            if(amount <= 0)
                throw new BankException("Invalid Deposit Amount!");
            transactionService.addTransaction(new Transaction(userId, java.time.LocalDateTime.now(), amount, Transaction.TransactionType.DEPOSIT));
            System.out.println("Deposit successful!");
        } catch (Exception e) {
            System.out.println("Error during deposit: " + e.getMessage());
        }
    }

    private void withdraw(String userId) {
        try {
            System.out.print("Enter Withdrawal Amount: ");
            var amount = Double.parseDouble(scanner.nextLine());
            transactionService.addTransaction(new Transaction(userId, java.time.LocalDateTime.now(), amount, Transaction.TransactionType.WITHDRAW));
            System.out.println("Withdrawal successful!");
        } catch (Exception e) {
            System.out.println("Error during withdrawal: " + e.getMessage());
        }
    }
}
