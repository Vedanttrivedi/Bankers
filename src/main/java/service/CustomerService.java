package service;
import models.BankDAO;

public class CustomerService
{
    private BankDAO dao;
    private static CustomerService service;
    private CustomerService()
    {
        dao = new BankDAO();
    }
    public static CustomerService getInstance()
    {
        if(service==null)
        {
            synchronized (BankDAO.class){
                if(service==null)
                {
                    service = new CustomerService();
                    return service;
                }
            }
        }
        return service;
    }
    public void createAccount(String email,String password,String fullname,long adharNumber,long amount,boolean passByToken) throws AuthException
    {
        if(adharNumber < (long)1e11)
            throw new AuthException("Underflow! 12 digists required");
        else if(adharNumber > (long)1e12)
            throw new AuthException("Overflow! 12 digit required!");

        dao.saveUser(email,password,fullname,adharNumber,amount,passByToken);
    }
    public boolean verifyLogin(String email,String password) throws AuthException
    {
       return dao.login(email,password);
    }
    public void deposite(String email,long amount) throws TransactionException
    {
        if(amount < 0)
            throw new TransactionException("Invalid Deposit(Negative Amount)!");
        dao.deposite(email,amount);
    }
    public boolean checkForUpdatePassword(String email)
    {
        return dao.passwordByToken(email);
    }
    public boolean updatePassword(String email,String password)
    {
        return dao.passwordByToken(email);
    }

    public boolean withDraw(String email,long amount) throws TransactionException
    {
        if(amount < 0)
            throw new TransactionException("Invalid Withdraw(Negative Amount)!");
        return dao.withDraw(email,amount);
    }
    public void takeLoan(String email,long amount) throws TransactionException
    {
        if(amount < 0)
            throw new TransactionException("Invalid Amount Loan(Negative Amount)!");
        dao.takeLoan(email,amount);
    }
    public void profile(String email)
    {
        dao.profile(email);
    }
}
