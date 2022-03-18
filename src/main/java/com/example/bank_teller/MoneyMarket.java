package com.example.bank_teller;

/**
 * Represents a specific type of checking account: money market savings account.
 *
 * Monthly fee is $10, and waived if balance is greater than 2500.
 * By default, it is a loyal customer account, but if the balance falls below $2500,
 * then it is not a loyal customer account anymore. A loyal customer account gets additional interest rate 0.15%.
 * Fee cannot be waived if the number of withdrawals exceeds 3 times
 * @author Nabihah, Maryam
 */

public class MoneyMarket extends Savings {
    private int numOfWithdrawals;
    public final static int NUM_OF_MONTHS = 12;
    public final static double FEE = 10.00;
    public final static double FEE_WAIVED_AMOUNT = 2500.00;
    public final static int NOT_LOYAL = 0;
    public final static int LOYAL = 1;
    public final static double ANNUAL_INTEREST_LOYAL = 0.0095;
    public final static double ANNUAL_INTEREST_NOT_LOYAL = 0.008;

    /**
     * Creates instance of MoneyMarket account with specified parameters.
     * Calls constructor from parent class, Savings, and passes in 1 as loyalCustomer parameter
     * @param holder A profile object containing first name, last name, and
     *               date of birth of account holder
     * @param closed A boolean that is true if account is closed and false if its open
     * @param balance A double that hold the account's balance
     */

    public MoneyMarket(Profile holder, boolean closed, double balance) {
        super(holder, closed, balance, LOYAL);
        this.numOfWithdrawals = 0;
    }

    /**
     * Overrides fee method in superclass, Savings. returns fee for money
     * market account
     * @return 0 if balance is greater than 2500 and number of withdrawls is
     * greater than 3, 10 otherwise.
     */
    @Override
    public double fee() {
        if(this.balance >= FEE_WAIVED_AMOUNT && this.numOfWithdrawals <= 3) {
            return 0.00;
        }
        return FEE;
    }

    /**
     * Override monthlyInterest method from superclass, Savings.
     * Calculates monthlyInterest by dividing annual rate (0.95%) by 12 and
     * multiplying it with the current balance in the account.
     * @return double specifying the monthly interest earned.
     */
    @Override
    public double monthlyInterest() {
        double monthlyInterestRate;
        if (this.loyalCustomer == LOYAL) {
            monthlyInterestRate = ANNUAL_INTEREST_LOYAL / NUM_OF_MONTHS;
        }
        else {
            monthlyInterestRate = ANNUAL_INTEREST_NOT_LOYAL / NUM_OF_MONTHS;
        }
        return monthlyInterestRate * super.balance;
    }

    /**
     * Overrides deposit method from Account class
     * Deposits amount from account and makes account loyal if balance >= 2500
     * @param amount a double specifying amount to deposit into account
     */
    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        if(super.balance >= FEE_WAIVED_AMOUNT)
            super.loyalCustomer = LOYAL;
    }

    /**
     * Overrides withdraw method from Account class
     * Withdraws amount from account and makes account not loyal if balance falls under 2500
     * Updates number of withdrawals
     * @param amount a double specifying amount to withdraw from account
     */
    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
        if(super.balance < FEE_WAIVED_AMOUNT)
            super.loyalCustomer = NOT_LOYAL;
        this.numOfWithdrawals++;
    }

    /**
     * Overrides toString.
     * @return String implementation from parent class and ::numofWithdrawals
     */
    @Override
    public String toString(){
        if(this.closed){
            numOfWithdrawals = 0;
        }
        return super.toString() + "::withdrawl: " + this.numOfWithdrawals;
    }

    /**
     * Overrides equals method. Two MM accounts their holders are equal.
     * @param obj an object
     * @return true if both objects of type MoneyMarket are equal. false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MoneyMarket) {
            MoneyMarket moneyMarket = (MoneyMarket) obj;
            return super.equals(obj) && this.getType().equals(moneyMarket.getType());
        }
        return false;
    }

    /**
     * Overrides getType from parent class.
     * @return a string specifying that this is a "Money Market Savings" account
     */
    @Override
    public String getType() {
        return "Money Market";
    }
}
