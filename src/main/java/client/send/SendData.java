package client.send;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SendData
{
    Socket socket;
    DataInputStream din;
    DataOutputStream dout;

    public SendData(Socket socket)
    {
        try
        {
            this.socket = socket;
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void  encryptedRegister(String fullName, String password, String aadhaarNumber, char accountType, double initialAmount)
    {
        try
        {
            String payLoad = "reg\t"+fullName+"\t"+password+"\t"+aadhaarNumber+"\t"+accountType+"\t"+initialAmount;

            System.out.println("Payload "+payLoad);
            dout.writeUTF(payLoad);
            String serverResponse = din.readUTF();
            System.out.println("Server message : "+serverResponse);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void  encryptedLogin(String userId, String password)
    {
        try
        {
            String payLoad = "login\t"+userId+"\t"+password;
            System.out.println("Payload "+payLoad);
            dout.writeUTF(payLoad);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void  showProfile(String userId)
    {
        try
        {
            String payLoad = "profile\t"+userId;
            System.out.println("Payload "+payLoad);
            dout.writeUTF(payLoad);
            dout.flush();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
