package ui;

import service.CustomerService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;

class Main
{
    public static void main(String[] args)
    {
        System.out.println("Intellijent Bank");
        System.out.println("Welcome to bank");
        CustomerService service = CustomerService.getInstance();
        final String mainIntroMessage = "Main>>Press 1 for Customer ,2 For Admin , 3 to Quit:-> ";
        System.out.print(mainIntroMessage);
        Scanner scan = new Scanner(System.in);
        int choice;
        try
        {
            choice = scan.nextInt();
        }
        catch (InputMismatchException ie)
        {
            System.err.println(ie);
            choice = 1;
            System.out.println("Default Choice 1 is selected!(Customer)");
        }

        while(choice!=3)
        {
            if(choice==1)
            {
                System.out.println("User>> Hey User Welcome To Bank");
                final String introMessage = "User>> Press l For Login and r For Register q quit :-> ";
                System.out.print(introMessage);
                char userChoice = scan.next().toLowerCase().charAt(0);

                while (userChoice!='q')
                {
                    if (userChoice == 'l')
                        Auth.login(scan,service);
                    else if(userChoice=='r')
                        Auth.register(scan,service);
                    else if(userChoice=='q')
                        break;
                    else
                        System.out.println("invalid choice");
                    System.out.print(introMessage);
                    try
                    {
                        userChoice = scan.next().toLowerCase().charAt(0);
                    }
                    catch (InputMismatchException ie)
                    {
                        System.err.println(ie);
                    }
                }
            }
            else if(choice==2)
            {
                System.out.println("Welcome to bank Admin");
                System.out.println("Admin dashboard");
                AdminUI.fileRegister(scan,service);
            }
            System.out.println(mainIntroMessage);
            try
            {
                choice = scan.nextInt();
            }
            catch (InputMismatchException ie)
            {
                System.err.println("Invalid input");
            }
        }
        System.out.println("Program Completed!");
    }
}
