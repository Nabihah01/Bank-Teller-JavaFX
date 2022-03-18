package com.example.bank_teller;

/**
 * Represents a specific type of checking account: regular savings account.
 *
 * Monthly fee is $6, and waived if balance is greater than 300
 * A loyal customer’s account gets additional interest rate 0.15%;
 * that is, annual interest rate will be 0.45% for a loyal customer account.
 * @author Nabihah, Maryam
 */
public class Savings extends Account {
    protected int loyalCustomer;
    public final static int NUM_OF_MONTHS = 12;
    public final static double FEE = 6.00;
    public final static double FEE_WAIVED_AMOUNT = 300.00;
    public final static int LOYAL = 1;
    public final static double ANNUAL_INTEREST_LOYAL = 0.0045;
    public final static double ANNUAL_INTEREST_NOT_LOYAL = 0.003;

    /**
     * Creates an instance of a savings account with given parameters by calling
     * the constructor from its parent class: Account.
     * @param holder A profile object containing first name, last name, and
     *        date of birth of account holder
     * @param closed A boolean that is true if account is closed and false if its open
     * @param balance A double that holds the account's balance
     * @param loyalCustomer An int that is 1 if loyalCustomer, 0 otherwise
     */
    public Savings(Profile holder, boolean closed, double balance, int loyalCustomer) {
        super(holder, closed, balance);
        this.loyalCustomer = loyalCustomer;
    }

    /**
     * Overrides fee method in superclass, Account. returns fee for savings account
     * @return 0 if balance is greater than or equal to $300, $6 if it is less than.
     */
    @Override
    public double fee() {
        if(super.balance >= FEE_WAIVED_AMOUNT) {
            return 0;
        }
        return FEE;
    }
    /**
     * Overrides monthlyInterest method from superclass, Account.
     * Calculates monthlyInterest by dividing annual rate 0.45 % (if loyal)
     * or 0.3% (if not loyal) by 12 and multiplying it
     * with the current balance in the account.
     * @return double specifying the monthly interest earned.
     */
    @Override
    public double monthlyInterest() {
        double monthlyInterestRate;
        if (this.loyalCustomer == LOYAL) {
            monthlyInterestRate = ANNUAL_INTEREST_LOYAL / NUM_OF_MONTHS;
        } else {
            monthlyInterestRate = ANNUAL_INTEREST_NOT_LOYAL / NUM_OF_MONTHS;
        }
        return monthlyInterestRate * super.balance;
    }

    /**
     * Overrides equals method. Two Savings Accounts are equal if their holders
     * are equal.
     * @param obj an object
     * @return true if both objects of type Savings are equal. false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Savings) {
            Savings save = (Savings) obj;
            return super.equals(obj) && this.getType().equals(save.getType());
        }
        return false;
    }

    /**
     * Override toString.
     * @return toString implementation in parent class, Account and ::loyal (if loyal)
     */
    @Override
    public String toString(){
        if(this.closed){
            return super.toString();
        }
        if (loyalCustomer == LOYAL){
            return super.toString() + "::Loyal";
        }
        else {
            return super.toString();
        }

    }

    /**
     * Overrides getType from parent class.
     * @return a string specifying that this is a "Savings" account
     */
    @Override
    public String getType() {
        return "Savings";
    }

    /**
     * Overrides withdraw method from Account class
     * Withdraws amount from account
     * @param amount a double specifying amount to withdraw from account
     */
    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
    }
}
