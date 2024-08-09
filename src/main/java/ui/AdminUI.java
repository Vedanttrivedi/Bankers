package ui;

import service.AuthException;
import service.CustomerService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

class AdminUI
{
    public static void fileRegister(Scanner scan, CustomerService service)
    {
        try
        {
            System.out.println("All data files must be present in /home/Vedant/Documents/Bankers/src/assets/");
            System.out.print("Enter the path of csv file : ");
            String line;
            line = scan.next();
            Path filePath = Paths.get(line);
            BufferedReader bfr = new BufferedReader(new FileReader("/home/vedant/Documents/Bankers/src/assets/"+line));
            while((line= bfr.readLine())!=null) {
                String[] userFields = line.split(",");
                System.out.println("Current line " + Arrays.toString(userFields));
                try {
                    service.createAccount(userFields[0], userFields[1], userFields[3], Long.parseLong(userFields[4]), Long.parseLong(userFields[5]), true);
                    System.out.println("Account created For "+userFields[3]);
                } catch (AuthException ae) {
                    System.err.println(ae);
                } catch (Exception e) {
                    System.err.println(e);
                }

            }
        }
        catch (IOException ie)
        {
            System.err.println(ie+" you may try after some time!");
        }
    }
}
