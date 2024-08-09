package ui;

import service.CustomerService;
import service.TransactionException;

import java.util.InputMismatchException;
import java.util.Scanner;

class DashBoard
{
    final String currentUser;
    final CustomerService service;
    final Scanner scan;
    DashBoard(String email, CustomerService service)
    {
        currentUser = email;
        this.service = service;
        scan = new Scanner(System.in);
        active();
    }

    void active()
    {
       // updatePassword();
        System.out.println("DashBoard>>Press 1 For Deposit , 2 For Withdraw , 3 For takeLoan, 4 for Account Info, 5 to Quit");
        System.out.print("Enter choice :");
        int choice = scan.nextInt();
        while(choice!=5)
        {
            try
            {
                switch (choice)
                {
                    case 1 -> deposit();
                    case 2 -> withdraw();
                    case 3 -> takeLoan();
                    case 4 -> profile();
                    default -> System.out.println("Invalid Choice");
                }
                System.out.println("DashBoard>>Press 1 For Deposit , 2 For Withdraw , 3 For takeLoan, 4 for Account Info, 5 to Quit");
                System.out.print("Enter choice :");
                choice = scan.nextInt();
            }
            catch (Exception e)
            {
                System.err.println(e);
            }
        }
    }

    private void deposit() throws TransactionException
    {
        System.out.println("Hey WelCome to Deposit Section");
        System.out.print("Enter the amount you would like to deposit :");
        long amount =scan.nextLong();
        service.deposite(currentUser,amount);
        System.out.println("Deposit done!");
    }
    private void updatePassword()
    {
        if(service.checkForUpdatePassword(currentUser))
        {
            System.out.println("Please Update your password first :");
            System.out.println("Enter new password : ");
            String password = new Scanner(System.in).nextLine();
            service.updatePassword(currentUser,password);
        }
    }
    private void withdraw() throws TransactionException
    {
        System.out.println("Hey WelCome to Withdraw Section");
        System.out.print("Enter the amount you would like to withdraw :");
        long amount;
        amount =scan.nextLong();
        service.withDraw(currentUser,amount);
        System.out.println("Withdraw done!");
    }
    private void profile()
    {
        service.profile(currentUser);
    }
    private void takeLoan() throws TransactionException
    {
        System.out.println("Enter the amount for which you want to apply load : ");
        long loanAmount = scan.nextLong();
        service.takeLoan(currentUser,loanAmount);
    }
}
