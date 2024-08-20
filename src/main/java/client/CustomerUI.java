package client;

import client.send.SendData;
import client.send.SendTransactionData;
import com.bankmanagement.dao.TransactionDAOImp;
import com.bankmanagement.dao.UserDAOImp;
import com.bankmanagement.models.Customer;
import com.bankmanagement.models.Transaction;
import com.bankmanagement.services.TransactionService;
import com.bankmanagement.services.UserService;

import javax.crypto.Cipher;
import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerUI
{
    Scanner scanner = new Scanner(System.in);
    UserService userService = new UserService(UserDAOImp.getInstance());
    TransactionService transactionService = new TransactionService(TransactionDAOImp.getInstance(), UserDAOImp.getInstance());
    Socket socket;
    DataInputStream din;
    DataOutputStream dout;
    Cipher cipher;
    SendData sender;
    SendTransactionData transactionSender;
    CustomerUI(Socket socket)
    {
        this.socket = socket;
        try
        {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            cipher = Cipher.getInstance("AES");
            sender = new SendData(socket);
            transactionSender= new SendTransactionData(socket,din,dout,transactionService);
        }
        catch (Exception ie)
        {
            System.out.println(ie.getMessage());
        }
        start();
    }
    public void start()
    {
        while (true)
        {
            System.out.println("===== Customer Menu =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");

            var option = scanner.next();
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

    private void register()
    {
        try
        {
            System.out.print("Enter Full Name: ");
            scanner.skip("\\R?");

            var name = scanner.nextLine();
            if(name.equals("\n") || name.charAt(0)==' ' || name.trim().length()==0)
            {
                System.err.println("Invalid Name value!");
                return;
            }
            System.out.print("Enter Password: ");
            var password = scanner.next();
            if(password.length() < 8)
            {
                System.err.println("Minimum length For password is 8! Try again");
                return;
            }
            System.out.print("Enter Aadhaar Number: ");
            var aadhaarNumber = scanner.next();
            try{
                if(aadhaarNumber.length() != 12){
                    System.err.println("Length required For aadhar card is 12");
                    return;
                }
                long k = Long.parseLong(aadhaarNumber);
            }catch (NumberFormatException nfe){
                System.err.println("Invalid Value entered Can Only Contain Number!");
                return;
            }
            System.out.print("Enter Account Type s or c (Savings/Current): ");
            var accountType = scanner.next();
            if(accountType.length() > 1)
            {
                System.err.println("Invalid Value !");
                return;
            }
            char d = accountType.toLowerCase().charAt(0);
            if(d!='s' && d!='c'){
                System.err.println("Invalid value ! Entered is "+d);
                System.out.println();
                return;
            }
            if(d=='s')
                System.out.println("Minimum balance for savings account is 500");
            System.out.print("Enter initial amount : ");
            var initialAmount = scanner.nextDouble();
            if(initialAmount < 0){
                System.out.println("Cannot enter negative amount");
                return;
            }
            if(initialAmount < 500){
                System.err.println("Savings Account Must have intial balance  : 500 ");
                return;
            }
            sender.encryptedRegister(name,password,aadhaarNumber,d,initialAmount);
            //var userId = userService.register(name, password, aadhaarNumber,d, initialAmount);
            System.out.println("Data Sent to socket!!!");
        }
        catch (InputMismatchException ie){
            System.out.println(ie.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error during registration " + e.getMessage());
        }
    }
 private void login()
 {
        try
        {
            System.out.print("Enter User ID: ");
            if(scanner.nextLine().equals("\n"))
                scanner.nextLine();
            scanner.skip("\\R?");
            var userId = scanner.nextLine();

            Console console = System.console();
            String password;

            if (console != null)
            {
                char[] passwordChars = console.readPassword("Enter Password: ");
                password = new String(passwordChars);
            }
            else
            {
                System.out.print("Enter Password: ");
                password = scanner.next();
            }
            sender.encryptedLogin(userId,password);
            boolean answer = din.readBoolean();
            System.out.println("Server Sent login "+answer);
            if (answer)
            {
                System.out.println("Login successful!");

                while (true)
                {
                    System.out.println("===== Account Menu =====");
                    System.out.println("1. Deposit");
                    System.out.println("2. Withdraw");
                    System.out.println("3. Profile");
                    System.out.println("4. Logout");

                    System.out.print("Choose an option: ");

                    var option = scanner.next();
                    switch (option)
                    {
                        case "1" -> deposit(userId);
                        case "2" -> withdraw(userId);
                        case "3" -> profile(userId);
                        case "4" ->
                        {
                            System.out.println("Logging out...");
                            return;
                        }
                        default -> System.out.println("Invalid option. Please try again.");
                    }
                }
            }
            else
            {
                System.out.println("Invalid User ID or Password.");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error during login: " + e.getMessage());
        }
    }
    private void profile(String userId)
    {
        System.out.println("I requested profile "+userId);
        sender.showProfile(userId);
        try
        {
            String profileMessage = din.readUTF();
            System.out.println(profileMessage);
        }
        catch (Exception e)
        {
            System.out.println("Exception while reading");
            System.out.println(e.getMessage());
        }
    }
    private void deposit(String userId)
    {
        try
        {
            System.out.print("Enter Deposit Amount: ");
            var amount = scanner.nextDouble();
            if(amount <= 0){
                System.out.println("invalid value");return;
            }
            //transactionService.addTransaction(new Transaction(userId, java.time.LocalDateTime.now(), amount, Transaction.TransactionType.DEPOSIT));
            System.out.println("Sending to server!!!!");
            transactionSender.encryptTransaction(userId,amount,"deposit");
            boolean response = din.readBoolean();
            if(response)
                System.out.println("Deposit successful!");
            else
                System.out.println("Transaction failed");
        }
        catch (InputMismatchException ee)
        {
            System.out.println(ee.getMessage());
        }
        catch (Exception e)
        {
            System.err.println("Error : " +e.getMessage());
        }
    }

    private void withdraw(String userId)
    {
        try
        {
            System.out.print("Enter Withdrawal Amount: ");
            var amount = scanner.nextDouble();
            if(amount < 0){
                System.out.println("Invalid value!");
                return;
            }
           // transactionService.addTransaction(new Transaction(userId, java.time.LocalDateTime.now(), amount, Transaction.TransactionType.WITHDRAW));
            transactionSender.encryptTransaction(userId,amount,"withdraw");
            boolean response = din.readBoolean();
            if(response)
                System.out.println("Withdraw successful!");
            else
                System.out.println("Transaction failed");
        }
        catch (InputMismatchException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception ef)
        {
            System.out.println("Error "+ef.getMessage());
        }
    }
}
