package com.example.bank_teller;
import java.text.DecimalFormat;

/**
 Abstract class that represents a generic account with three fields: a profile
 object containing the full name and dob of the account holder, a boolean specifying
 if the account is closed or not, and the balance in the account.
 @author Nabihah, Maryam
 */
public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;
    protected static final DecimalFormat df = new DecimalFormat("###,##0.00");

    /**
     * Creates an instance of Account with given parameters.
     * @param holder A profile object containing first name, last name, and
     *               date of birth of account holder
     * @param closed A boolean that is true if account is closed and false if its open
     * @param balance A double that hold the accounts' balance
     */
    public Account(Profile holder, boolean closed, double balance) {
        this.holder = holder;
        this.closed = closed;
        this.balance = balance;
    }

    /**
     * Overrides equals method. Two Accounts are equal if their holders are equal.
     * @param obj an object
     * @return true if both objects of type Account are equal. false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Account) {
            Account account = (Account) obj;
            return this.holder.equals(account.holder);
        }
        return false;
    }

    /**
     * Withdraws amount from account
     * @param amount a double specifying amount to withdraw from account
     */
    public void withdraw(double amount) {
        this.balance -= amount;
    }

    /**
     * Deposits amount into account
     * @param amount a double specifying amount to deposit into account
     */
    public void deposit(double amount) {
        this.balance += amount;
    }

    /**
     * Overrides toSting method.
     * @return Account as a string in "type::firstname lastname dob
     * ::Balance$balance::CLOSED" format if the account is closed.
     * If account is open, then ::CLOSED isn't added
     */
    @Override
    public String toString(){
        String string = "";
        if(this.getType().equals("Money Market"))
            string = this.getType() + " Savings::" + this.holder.toString() + "::Balance $"
                    + df.format(this.balance);
        else
            string = this.getType() + "::" + this.holder.toString() + "::Balance $"
                + df.format(this.balance);
        if(this.closed) {
            string = string + "::CLOSED";
        }
        return string;
    }

    /**
     * returns the monthlyInterest
     * @return a double specifying the monthly interest
     */
    public abstract double monthlyInterest();
    /**
     * returns the monthly fee
     * @return a double specifying the monthly fee
     */
    public abstract double fee();
    /**
     * returns the account type
     * @return a String that is the account type
     */
    public abstract String getType();
}