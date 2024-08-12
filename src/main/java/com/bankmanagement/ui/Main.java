package com.bankmanagement.ui;

import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("===== Bank Management System =====");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            var option = scanner.nextLine();
            switch (option) {
                case "1" -> new CustomerUI().start();
                case "2" -> new AdminUI().start();
                case "3" -> {
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
