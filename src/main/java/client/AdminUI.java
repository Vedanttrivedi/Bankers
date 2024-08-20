package client;

import com.bankmanagement.dao.TransactionDAOImp;
import com.bankmanagement.dao.UserDAOImp;
import com.bankmanagement.services.AdminService;

import java.util.Scanner;


public class AdminUI {
    private final AdminService adminService;
    private final Scanner scanner;

    public AdminUI()
    {
        adminService = new AdminService(UserDAOImp.getInstance(), TransactionDAOImp.getInstance());
        scanner = new Scanner(System.in);
        start();
    }

    public void start()
    {
        while (true)
        {
            System.out.println("===== Admin Menu =====");
            System.out.println("1. Load Users from File");
            System.out.println("2. Load Transactions from File");
            System.out.println("3. Last N Transactions Of User");
            System.out.println("4. Quit");
            System.out.print("Choose an option: ");
            scanner.skip("\\R?");
            var option = scanner.nextLine();
            switch (option) {
                case "1" -> loadUsersFromFile();
                case "2" -> loadTransactionsFromFile();
                case "3" -> lastNTransactions();
                case "4" -> {
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void loadUsersFromFile() {
        System.out.println("Your userdata file must be present in  /home/vedant/Documents/Bankers/src/assets/");
        System.out.print("Enter the file path for users: ");

        scanner.skip("\\R?");
        String filePath = scanner.next();
        adminService.loadUsersFromFile(filePath);
    }

    private void loadTransactionsFromFile() {
        System.out.print("Enter the file path for transactions: ");
        scanner.skip("\\R?");
        String filePath = scanner.next();
        adminService.loadTransactionsFromFile(filePath);
    }
    private void lastNTransactions()
    {
        System.out.println("Enter userid : ");
        var userId = scanner.next();
        System.out.println("Enter the n : ");
        var n = scanner.nextInt();
        adminService.getLastNTransaction(userId,n);
    }
    private void dayWiseTransation()
    {

    }
}
