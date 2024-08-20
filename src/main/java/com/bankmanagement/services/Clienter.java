package com.bankmanagement.services;


import com.bankmanagement.exceptions.AuthenticationException;
import com.bankmanagement.models.Transaction;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Clienter  implements Runnable
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
            StringBuilder clientMessage = new StringBuilder();
            clientMessage.append((String)din.readUTF());
            StringBuilder serviceAsked = new StringBuilder();
            System.out.println("Server side "+clientMessage.toString());
            while(!clientMessage.toString().equals("quit"))
            {
                String[] payLoad=clientMessage.toString().split("\t");
                System.out.println("In the method "+ Arrays.toString(payLoad));
                serviceAsked.append(payLoad[0]);
                //registration service
                if(serviceAsked.toString().equals("reg"))
                {
                    System.out.println("Client Called register on Server Side");
                    var userId = userService.register(payLoad[1],payLoad[2],payLoad[3],payLoad[4].charAt(0),Double.parseDouble(payLoad[5]));
                    dout.writeUTF("User Registered With this userid : "+userId);
                    dout.flush();
                }
                //login service
                else if(serviceAsked.toString().equals("login"))
                {
                    System.out.println("Client Called Login on server side");

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
                else if(serviceAsked.toString().equals("deposit") || serviceAsked.toString().equals("withdraw"))
                {
                    System.out.println("Client called "+serviceAsked+ " on server side ");

                    try
                    {
                        System.out.println("Trying to deposit...");
                        System.out.println(Arrays.toString(payLoad));
                        boolean value = transactionService.addTransaction(new Transaction(payLoad[1], LocalDateTime.now(),Double.parseDouble(payLoad[2]),
                                serviceAsked.toString().equals("deposit") ?Transaction.TransactionType.DEPOSIT:Transaction.TransactionType.WITHDRAW));
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
                        dout.writeBoolean(false);
                        dout.flush();
                        System.out.println(e.getMessage());
                    }
                }
                else if(serviceAsked.toString().equals("profile"))
                {
                    System.out.println("Profile View Request...");
                    dout.writeUTF(userService.showProfileSocket(payLoad[1]).toString());
                    dout.flush();
                }
                clientMessage.setLength(0);
                serviceAsked.setLength(0);
                clientMessage.append(din.readUTF());
            }

        }
        catch (IOException e)
        {
            System.out.println("Client Disconnected "+e.getMessage());
        }
    }
}

