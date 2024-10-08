package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class Main
{
    Scanner scanner = new Scanner(System.in);

    public void start()
    {
        int port=6666;
        String ip="localhost";
        boolean isRunning=true;
        try
        {
            Socket socket = new Socket(ip,port);
            System.out.println("Connected To "+socket.getRemoteSocketAddress());
            while (isRunning)
            {

                System.out.println("===== Bank Management System =====");
                System.out.println("1. Customer");
                System.out.println("2. Admin");

                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                scanner.skip("\\R?");
                var option = scanner.nextLine();
                switch (option) {
                    case "1" -> new CustomerUI(socket);
                    case "2" -> new AdminUI();
                    case "3" -> {
                        isRunning=false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        }
        catch (Exception ie)
        {
            System.out.println(ie.getMessage());
        }
        finally
        {
            System.out.println("Thank you for visiting bank!");
        }
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
