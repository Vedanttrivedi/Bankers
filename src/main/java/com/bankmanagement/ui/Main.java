package com.bankmanagement.ui;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
//implement extra features
public class Main {
    Scanner scanner = new Scanner(System.in);
    
    public void start() {

        while (true) {

            System.out.println("===== Bank Management System =====");
            System.out.println("1. Customer");
            System.out.println("2. Admin");

            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            scanner.skip("\\R?");
            var option = scanner.nextLine();
            switch (option) {
                case "1" -> new CustomerUI().start();
                case "2" -> new AdminUI().start();
                case "3" -> {
                    System.out.println("Exiting the system. Goodbye!");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void main(String[] args)
    {
        try
        {
            int port = 6666;
            String ip = "localhost";
            System.out.println("Trying to connect with "+ip+"\t on port "+port);
            Socket socket = new Socket(ip,port);
            new Main().start();
        }
        catch (IOException ie)
        {
            System.out.println(ie.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
