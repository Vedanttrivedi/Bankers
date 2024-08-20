package com.bankmanagement.services;

import com.bankmanagement.dao.TransactionDAOImp;
import com.bankmanagement.dao.UserDAOImp;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankService
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService(UserDAOImp.getInstance());
        TransactionService transactionService = new TransactionService(TransactionDAOImp.getInstance(), UserDAOImp.getInstance());

        final int port=6666;
        try
        {
            ServerSocket sct = new ServerSocket(port);
            System.out.println("BankServer Initialized!");
            int processors = Runtime.getRuntime().availableProcessors();
            System.out.println("Available processors are "+processors);
            ExecutorService executor = Executors.newFixedThreadPool(processors);

            while(true)
            {
                Socket srt = sct.accept();
                executor.execute(new Clienter(srt,userService,transactionService));
                System.out.println(srt.getInetAddress() +" Connected in "+Thread.currentThread().getName());
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
