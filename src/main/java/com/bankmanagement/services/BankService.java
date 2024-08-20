package com.bankmanagement.services;

import com.bankmanagement.dao.TransactionDAOImp;
import com.bankmanagement.dao.UserDAOImp;
import com.bankmanagement.exceptions.AuthenticationException;
import com.bankmanagement.models.Transaction;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class BankService
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService(UserDAOImp.getInstance());
        TransactionService transactionService = new TransactionService(TransactionDAOImp.getInstance(), UserDAOImp.getInstance());

        int port=6666;
        try
        {
            ServerSocket sct = new ServerSocket(port);
            System.out.println("BankServer Initialized!");

            while(true)
            {
                Socket srt = sct.accept();
                Thread th = new Thread(new Clienter(srt,userService,transactionService));
                th.start();
                System.out.println(srt.getInetAddress() +" Connected in "+Thread.currentThread().getName());

            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}

class Clienter  implements Runnable
{
    //for each client which is connected to socket
    DataInputStream din;
    DataOutputStream dout;
    BufferedReader breader;
    Socket socket;
    UserService userService;
    TransactionService transactionService;
    Clienter(Socket socket,UserService userService,TransactionService transactionService) throws Exception
    {
        this.socket = socket;
        din = new DataInputStream(socket.getInputStream());
        dout = new DataOutputStream(socket.getOutputStream());
        breader = new BufferedReader(new InputStreamReader(System.in));
        this.transactionService = transactionService;
        this.userService = userService;
    }
    public void run()
    {
        try
        {
            String clientMessage;
            clientMessage = (String)din.readUTF();
            while(clientMessage!="quit")
            {
                String[] payLoad=clientMessage.split("\t");
                System.out.println("In the method "+ Arrays.toString(payLoad));
                String serviceAsked = payLoad[0];
                //registration service
                if(serviceAsked.equals("reg"))
                {
                    System.out.println("Client Called register on Server Side");
                    serviceAsked="";
                    var userId = userService.register(payLoad[1],payLoad[2],payLoad[3],payLoad[4].charAt(0),Double.parseDouble(payLoad[5]));
                    dout.writeUTF("User Registered With this userid : "+userId);
                    dout.flush();
                }
                //login service
                else if(serviceAsked.equals("login"))
                {
                    System.out.println("Client Called Login on server side");
                    serviceAsked="";
                    try
                    {
                        boolean result = userService.login(payLoad[1],payLoad[2]);

                        if(result==true){
                            System.out.println("valid login on server");
                            dout.writeBoolean(true);
                            dout.flush();
                        }
                    }

                    catch (AuthenticationException ae)
                    {
                        System.out.println(ae.getMessage());
                        System.out.println("Messsage written here....");
                        dout.writeBoolean(false);
                        dout.flush();

                    }
                }
                else if(serviceAsked.equals("deposit") || serviceAsked.equals("withdraw"))
                {
                    System.out.println("Client called "+serviceAsked+ " on server side ");

                    try
                    {
                        System.out.println("Trying to deposit...");
                        System.out.println(Arrays.toString(payLoad));
                        boolean value = transactionService.addTransaction(new Transaction(payLoad[1],LocalDateTime.now(),Double.parseDouble(payLoad[2]),
                                serviceAsked.equals("deposit") ?Transaction.TransactionType.DEPOSIT:Transaction.TransactionType.WITHDRAW));
                        System.out.println("Value");
                        if(value)
                        {
                            System.out.println(serviceAsked+" successful on server side!!!");
                            dout.writeBoolean(true);
                            dout.flush();
                        }
                        else
                        {
                            System.out.println(serviceAsked +" not successful on server side!");
                            dout.writeBoolean(false);
                            dout.flush();
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
                else if(serviceAsked.equals("profile"))
                {
                    System.out.println("Profile View Request...");
                    dout.writeUTF(userService.showProfileSocket(payLoad[1]).toString());
                    dout.flush();
                }
                clientMessage = din.readUTF();
            }

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
