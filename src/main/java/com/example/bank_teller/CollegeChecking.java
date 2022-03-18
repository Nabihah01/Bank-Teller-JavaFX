package com.example.bank_teller;

/**
 * Represents a specific type of checking account: college checking account.
 *
 * This type of account is provided to Rutgers' students only and they must provide
 * a valid campus code to qualify (0 for New Brunswick, 1 for Newark, and 2 for Camden).
 * There is no fee and annual interest is 0.25%.
 * @author Nabihah, Maryam
 */
public class CollegeChecking extends Checking {
    private int campusCode;
    private final static int NO_FEE = 0;
    private final static int NEW_BRUNSWICK = 0;
    private final static int NEWARK = 1;
    private final static int NUM_OF_MONTHS = 12;
    public final static double ANNUAL_INTEREST = 0.0025;

    /**
     * Creates instance of CollegeChecking account with specified parameters.
     * Calls constructor from parent class, Checking and sets campusCode variable
     * since that isn't part of the parent class
     * @param holder A profile object containing first name, last name, and
     *               date of birth of account holder
     * @param closed A boolean that is true if account is closed and false if its open
     * @param balance A double that hold the account's balance
     * @param campusCode An int specifying the campus (New Brunswick, Newark,
     *                   or Camden)
     */
    public CollegeChecking(Profile holder, boolean closed, double balance, int campusCode) {
        super(holder, closed, balance);
        this.campusCode = campusCode;
    }
    /**
     * Gets campus name from campus code
     * @return A string specifying the campus.
     */
    public String campusName(){
        if (campusCode == NEW_BRUNSWICK) {
            return "NEW_BRUNSWICK";
        }
        else if (campusCode == NEWARK) {
            return "NEWARK";
        }
        else {
            return "CAMDEN";
        }
    }

    /**
     * Overrides fee method in superclass, Checking. returns fee for college
     * checking account
     * @return 0. There is no fee for the College Checking account.
     */
    @Override
    public double fee() {
        return NO_FEE;
    }

    /**
     * Override monthlyInterest method from superclass, Checking.
     * Calculates monthlyInterest by dividing annual rate (0.25%) by 12 and
     * multiplying it with the current balance in the account.
     * @return double specifying the monthly interest earned.
     */
    @Override
    public double monthlyInterest() {
        double monthlyInterestRate = ANNUAL_INTEREST / NUM_OF_MONTHS;
        return monthlyInterestRate * super.balance;
    }
    /**
     * Overrides equals method. Two CCC accounts are equal if their holders sre equal
     * @param obj an object
     * @return true if both objects of type Account are equal. false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CollegeChecking) {
            CollegeChecking collegeChecking = (CollegeChecking) obj;
            return super.equals(obj) && this.getType().equals(collegeChecking.getType());
        }
        return false;
    }
    /**
     * Overrides toString.
     * @return String implementation from parent class and ::campusName
     */
    @Override
    public String toString(){
        return super.toString() + "::" + campusName();
    }

    /**
     * Overrides getType from parent class.
     * @return a string specifying that this is a "College Checking" account
     */
    @Override
    public String getType() {
        return "College Checking";
    }

}
