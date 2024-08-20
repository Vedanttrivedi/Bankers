package client.send;
import com.bankmanagement.services.TransactionService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SendTransactionData
{
    Socket socket;
    DataInputStream din;
    DataOutputStream dout;
    TransactionService transactionService;
    public SendTransactionData(Socket socket, DataInputStream din, DataOutputStream dout, TransactionService transactionService)
    {
        this.socket = socket;
        this.din = din;
        this.dout = dout;
        this.transactionService = transactionService;
    }

    public void encryptTransaction(String userId,double amount,String type)
    {

        //transactionService.addTransaction(new Transaction(userId, java.time.LocalDateTime.now(), amount, Transaction.TransactionType.DEPOSIT));
        String payLoad = type+"\t"+userId+"\t"+amount;
        try
        {
            System.out.println(type+" Data Sent to server !!!");
            dout.writeUTF(payLoad);
            dout.flush();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void showProfile(String userId)
    {
        String payLoad = "profile\t"+userId;
        try
        {
            System.out.println("Profile Requested!");
            dout.writeUTF(payLoad);
            dout.flush();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
