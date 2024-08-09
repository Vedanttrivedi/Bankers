package ui;
import service.AuthException;
import service.CustomerService;
import java.io.Console;
import java.util.Scanner;

class Auth
{
    private static Console cns = System.console();
    public static void register(Scanner scan,CustomerService service)
    {
        System.out.println("User>> Welcome to Registration page :");

        System.out.print("Enter fullname : ");
        String fullname = scan.nextLine();
        fullname = scan.nextLine();
        System.out.print("Enter password : ");
        char[]password;
        try
        {
            password =cns.readPassword();
        }
        catch (NullPointerException ne)
        {
            System.err.println("Console is not available try with scanner");
            password = scan.nextLine().toCharArray();
        }
        System.out.print("Enter Adharnumber : ");
        long adharNumber = scan.nextLong();

        System.out.print("Enter Email : ");
        String email = scan.next();
        System.out.print("Enter initial amount(optional) : ");
        long amount = scan.nextLong();
        if(amount < 0)
        {System.out.println("Invalid amount ! Try again.(1 Chance Remaining)");
            System.out.print("Enter initial amount(optional) : ");
            amount= scan.nextLong();
        }

        if(amount < 0)
        {
            System.out.println("Noob!");
            return;
        }
        try
        {
            service.createAccount(email,new String(password),fullname,adharNumber,amount,false);
            System.out.println("Registration Complete");
        }
        catch (AuthException e)
        {
            System.out.println(e.toString());
        }
    }

    public static void login(Scanner scan, CustomerService service)
    {
        System.out.println("User>>Welcome to Login page :");
        System.out.print("Enter Email: ");
        String email = scan.next();
        System.out.print("Enter password : ");
        char[]password;

        try
        {
            password = cns.readPassword();
        }
        catch (NullPointerException ne)
        {
            System.err.println("Console is not available! Try Scanner");
            password =scan.next().toCharArray();
        }
        try
        {
           if(service.verifyLogin(email,new String(password)))
           {
               DashBoard activity = new DashBoard(email,service);
               System.out.println("Thank You For the session!");
           }
           else
               System.out.println("Invalid details");
        }
        catch (AuthException e)
        {
            System.out.println(e.toString());
        }
    }

}
