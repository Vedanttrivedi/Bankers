package models;

import models.Bank;
import service.AuthException;

import service.TransactionException;

import java.util.HashMap;
import java.util.function.Predicate;
//long as key for map

public class BankDAO implements Bank
{
    private HashMap<String, Customer> users;
    public BankDAO()
    {
        users = new HashMap<>();
    }
    private final  Predicate<String> existsByEmail = email -> users.containsKey(email);

    public void saveUser(String email, String password, String fullname, long adharNumber, long amount,boolean passByToken) throws AuthException
    {
        if(existsByEmail.test(email))
            throw new AuthException("User with this email already exists!");

        users.put(email,new Customer(password,fullname,adharNumber,amount,false));

    }
    public boolean login(String email,String password) throws AuthException
    {
        if(existsByEmail.negate().test(email))
            throw new AuthException("User With this email does not exists!");
        return users.get(email).getPassword().equals(password);
    }

    synchronized public void deposite(String email,long amount)
    {
        var userEmail = users.get(email);
        long prevAmount = userEmail.getAmount();
        userEmail.setAmount(prevAmount+amount);
    }
    synchronized public boolean withDraw(String email,long amount) throws TransactionException
    {
        var prevAmount = users.get(email).getAmount();
        if(prevAmount < amount)
            throw new TransactionException("Cannot Withdraw insuffiecient balance");
        users.get(email).setAmount(prevAmount-amount);
        return true;
    }
    public boolean takeLoan(String email,long amount)
    {
        return (amount <= (long)users.get(email).getAmount() % 50);
    }
    public void profile(String email)
    {
        System.out.println(users.get(email));
    }

    public boolean passwordByToken(String email)
    {
        return users.get(email).isPasswordByToken();
    }

}
