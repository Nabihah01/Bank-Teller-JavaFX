package com.example.bank_teller;

/**
 * Represents a specific type of account: a regular checking account.
 *
 * A checking account has a $25 fee which is waived if balance is >= $1000.
 * It also has an annual interest rate of 0.1%
 * @author Nabihah, Maryam
 */
public class Checking extends Account {
    public final static int NUM_OF_MONTHS = 12;
    public final static double FEE = 25.00;
    public final static double FEE_WAIVED_AMOUNT = 1000.00;
    public final static double ANNUAL_INTEREST = 0.001;

    /**
     * Creates an instance of a checking account with given parameters by calling
     * the constructor from its parent class: Account.
     * @param holder A profile object containing first name, last name, and
     *        date of birth of account holder
     * @param closed A boolean that is true if account is closed and false if its open
     * @param balance A double that hold the account's balance
     */
    public Checking(Profile holder, boolean closed, double balance) {
        super(holder, closed, balance);
    }

    /**
     * Override monthlyInterest method from superclass, Account.
     * Calculates monthlyInterest by dividing annual rate (0.1 %) by 12 and multiplying it
     * with the current balance in the account.
     * @return double specifying the monthly interest earned.
     */
    @Override
    public double monthlyInterest() {
        double monthlyInterestRate = ANNUAL_INTEREST / NUM_OF_MONTHS;
        return Double.parseDouble(df.format(monthlyInterestRate * super.balance));
    }

    /**
     * Overrides fee method in superclass, Account. returns fee for checking account
     * @return 0 if balance is greater than  or equal to $1000, $25 if it is less than.
     */
    @Override
    public double fee() {
        if (super.balance >= FEE_WAIVED_AMOUNT)
            return 0.00;
        else
            return FEE;
    }

    /**
     * Override equals method. Two Checking accounts are equal if their profiles
     * are equal
     * @param obj an object
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Checking) {
            Checking check = (Checking) obj;
            return super.equals(obj) && this.getType().equals(check.getType());
        }
        return false;
    }

    /**
     * Override toString.
     * @return toString implementation in parent class, Account.
     */
    @Override
    public String toString(){
        return super.toString();
    }

    /**
     * Override getType from parent class
     * @return a string specifying what account this is: Checking
     */
    @Override
    public String getType() {
        return "Checking";
    }

}
