package models;

import service.TransactionException;

interface Bank
{
    String name = "Motabank";
    void deposite(String email,long amount);
    boolean withDraw(String email,long amount) throws TransactionException;
    boolean takeLoan(String email,long amount);
}