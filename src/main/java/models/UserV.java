package models;

interface UserV
{
    void signUp(String username,String password,String fullname,String email);
    boolean login(String username,String password);
}
