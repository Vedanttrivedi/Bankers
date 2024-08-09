package models;

public class Customer
{
    private String fullname;
    private transient String password;

    public long getAdharnumber() {
        return adharnumber;
    }

    public void setAdharnumber(long adharnumber) {
        this.adharnumber = adharnumber;
    }

    private long adharnumber;
    private long userId,amount;

    public boolean isPasswordByToken() {
        return passwordByToken;
    }

    public void setPasswordByToken(boolean passwordByToken) {
        this.passwordByToken = passwordByToken;
    }

    private boolean passwordByToken;
    public Customer(String password,String fullname)
    {
        this(password,fullname,0l);
    }
    public Customer(String password,String fullname,long adharnumber)
    {
        this(password,fullname,adharnumber,0L,false);
    }
    public Customer(String password,String fullname,long adharnumber,long amount,boolean passwordByToken)
    {
        this.password = password;
        this.fullname = fullname;
        this.amount = amount;
        this.userId  = 0L;
        this.adharnumber = adharnumber;
        this.passwordByToken=passwordByToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullname;
    }

    public void setFullName(String fullname) {
        this.fullname = fullname;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "fullname='" + fullname + '\'' +
                ",amount=" + amount +
                '}';
    }
}
