package com.example.bank_teller;

/**
 * Represents database all bank accounts using an array of Accounts.
 *
 * An account can be opened, reopened, closed, and searched for within the database.
 * Deposits and withdrawals can be made to and from accounts, respectively.
 * All accounts can be returned as a String as they appear in the database, or sorted by account type,
 * or returned as a String with their monthly interest and fee listed.
 * @author Nabihah, Maryam
 */
public class AccountDatabase {
    private Account[] accounts;
    private int numAcct;
    public static final int NOT_FOUND = -1;
    public final static int ACC_ALREADY_OPEN = -1;

    /**
     * Creates an instance of AccountDatabase
     */
    public AccountDatabase() {
        this.accounts = new Account[4];
        this.numAcct = 0;
    }

    /**
     * Gets the accounts array from AccountDatabase
     * @return An array object representing the accounts array in AccountDatabase
     */
    public Account[] getAccounts() {
        return this.accounts;
    }
    /**
     Gets the numAcct from Schedule
     @return An int representing the number of accounts currently
     in the AccountDatabase object
     */
    public int getNumAcct() {
        return this.numAcct;
    }
    /**
     Checks if given account exists in array of accounts
     @param account an instance of Account
     @return index of account in array if found, or NOT_FOUND otherwise
     */
    private int find(Account account) {
        for (int i = 0; i < this.numAcct; i++) {
            if(this.accounts[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Increases capacity of account array by 4
     */
    private void grow() {
        int newSize = this.accounts.length + 4;
        Account newAccountsArray[] = new Account[newSize];

        for(int i = 0; i < this.accounts.length; i++) {
            newAccountsArray[i] = this.accounts[i];
        }
        this.accounts = newAccountsArray;
    }

    /**
     * Opens an account by adding given account to accounts array
     * @param account an instance of Account
     * @return true if account successfully added into the array, false otherwise
     */
    public boolean open(Account account) {
        if (find(account) != NOT_FOUND){
            return false;
        }
        if(numAcct >= this.accounts.length){
            grow();
            this.accounts[numAcct] = account;
            this.numAcct++;
        }
        else {
            this.accounts[numAcct] = account;
            this.numAcct++;
        }
        return true;
    }

    /**
     * Reopens an existing account.
     * @param account an instance of Account
     * @return -1 if account is already open, else returns the index of the specified
     * (closed) account in AccountDatabase
     */
    public int reopen(Account account) {
        int index = find(account);
        if(accounts[index].closed == true){
            return index;
        }
        return ACC_ALREADY_OPEN;
    }

    /**
     * Closes the given account by setting balance to zero and closed to true
     * @param account an instance of Account
     * @return true if account closed successfully, false otherwise
     */
    public boolean close(Account account) {
        int accIndex = find(account);
        if(accIndex == NOT_FOUND)
            return false;
        if(accounts[accIndex].closed == true){
            return false;
        }
        accounts[accIndex].closed = true;
        accounts[accIndex].balance = 0.00;
        return true;
    }

    /**
     * Deposits into account specified by finding that account in accounts array.
     * The deposit amount is the balance in the passed in Account object.
     * @param account an instance of account
     */
    public void deposit(Account account) {
        Account acc = accounts[find(account)];
        acc.deposit(account.balance);
    }

    /**
     * Withdraws from account specified by finding that account in accounts array.
     * The withdraw amount is the balance in the passed in Account object.
     * @param account an instance of account
     * @return false if insufficient funds, true is withdraw was successful
     */
    public boolean withdraw(Account account) {
        Account acc = accounts[find(account)];
        if(account.balance >= acc.balance) {
            return false;
        }
        acc.withdraw(account.balance);
        return true;
    }

    /**
     * Returns all of the accounts in accounts array
     * @return String consisting of all accounts in the database
     */
    public String accountsList() {
        String output = "";
        for(int i = 0; i < this.numAcct; i++){
            output = output.concat(this.accounts[i].toString() + "\n");
        }
        return output;
    }

    /**
     * Returns all accounts ordered by Account Type
     * @return String consisting of all accounts ordered by Account Type
     */
    public String accountsByAccountType() {
        for (int i = 1; i < numAcct; i++) {
            Account acc = accounts[i];
            int j = i - 1;
            while (j >= 0 && accounts[j].getType()
                    .compareTo(acc.getType()) > 0) {
                accounts[j + 1] = accounts[j];
                j--;
            }
            accounts[j + 1] = acc;
        }
        return accountsList();
    }

    /**
     * Returns all the accounts and their monthly fees and monthly interest
     * @return String consisting of accounts in database, each accounts respective
     * fee and monthly interest
     */
    public String accountsWithFeeAndInterest() {
        String output = "";
        for(int i = 0; i < this.numAcct; i++){
            output = output.concat(this.accounts[i].toString() + "::fee $" +
                    Account.df.format(this.accounts[i].fee()) + "::monthly interest $"
                    + Account.df.format(this.accounts[i].monthlyInterest()) + "\n");
        }
        return output;
    }

    /**
     * Calls (private) find method
     * @param account an instance of account
     * @return index of account if found, -1 if not found
     */
    public int callFind(Account account){
        return find(account);
    }

}
