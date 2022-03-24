package com.example.bank_teller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 A GUI class to process the transactions (opening or closing an account, depositing
 into or withdrawing from an existing account, or printing account details) entered
 by the user through the graphical user interface and outputs the results to the
 text area present in the GUI.
 @author Maryam, Nabihah
 */


public class BankTellerController {
    AccountDatabase accountDatabase = new AccountDatabase();
    public final static double MIN_AMT_FOR_MM = 2500.00;
    public final static int ACC_ALREADY_OPEN = -1;
    public final static int NOT_FOUND = -1;
    private final static int NEW_BRUNSWICK = 0;
    private final static int NEWARK = 1;
    private final static int CAMDEN = 2;

    @FXML
    private RadioButton checking, checkingOC, collegeChecking, collegeCheckingOC,
            savings, savingsOC, moneyMarket, moneyMarketOC;

    @FXML
    private ToggleGroup accountType, campus;

    @FXML
    private TextField amount, amountOC;

    @FXML
    private DatePicker dob, dobOC;

    @FXML
    private TextField firstName, firstNameOC, lastName, lastNameOC;

    @FXML
    private CheckBox loyal;

    @FXML
    private RadioButton newBrunswickOC, newarkOC, camdenOC;

    @FXML
    private TextArea textFieldAD, textFieldDW, textFieldOC;

    /**
     * This method is called to initialize controller after root element has been
     * processed. All elements are disabled in initial state of application. 
     */
    @FXML
    void initialize() {
        newBrunswickOC.setDisable(true);
        newarkOC.setDisable(true);
        camdenOC.setDisable(true);
        loyal.setDisable(true);
        checking.setSelected(false);
    }

    /**
     * Event Handler for Savings radio button. Enables loyalty, disables campus buttons
     * @param event an Event representing some type of action
     */
    @FXML
    void savingsButton(ActionEvent event) {
        newBrunswickOC.setDisable(true); newarkOC.setDisable(true);
        camdenOC.setDisable(true);
        loyal.setDisable(false);
        loyal.setSelected(false);
    }

    /**
     * Event Handler for Checking radio button. Disables all elements (campus and loyalty)
     * @param event an Event representing some type of action
     */
    @FXML
    void checkingButton(ActionEvent event){
        newBrunswickOC.setDisable(true); newarkOC.setDisable(true);
        camdenOC.setDisable(true);
        loyal.setDisable(true);
    }

    /**
     * Event Handler for MoneyMarket radio button.
     * Disables campus and loyalty buttons. sets loyalty to be selected.
     * @param event an Event representing some type of action
     */
    @FXML
    void mmButton(ActionEvent event){
        newBrunswickOC.setDisable(true); newarkOC.setDisable(true);
        camdenOC.setDisable(true);
        loyal.setDisable(true);
        loyal.setSelected(true);
    }

    /**
     * Event Handler for College Checking radio button
     * Enables campus radio buttons and disables loyalty button
     * @param event an Event representing some type of action
     */
    @FXML
    void ccButton(ActionEvent event){
        newBrunswickOC.setDisable(false);
        newarkOC.setDisable(false);
        camdenOC.setDisable(false);
        loyal.setDisable(true);
    }

    /**
     * check whether account type has been selected
     * @param isOCTab (boolean) if user is on the open/close tab of the GUI or
     * deposit/withdraw tab
     * @return false if no account type had been selected, true otherwise
     */
    boolean accTypeIsSelected(boolean isOCTab) {
        if(isOCTab) {
            return checkingOC.isSelected() || savingsOC.isSelected() ||
                    collegeCheckingOC.isSelected() || moneyMarketOC.isSelected();
        } else {
            return checking.isSelected() || savings.isSelected() ||
                    moneyMarket.isSelected() || collegeChecking.isSelected();
        }
    }

    /**
     * Event Handler for Apply Interest and Fees button. Subtracts the fee and
     * monthly interest from every account in the database and then prints
     * updated balances of accounts with account information (by calling accountList
     * from AccountDatabase) to text area in Account Database tab in GUI.
     * @param event an Event representing some type of action
     */
    @FXML
    void applyInterestAndFees(ActionEvent event) {
        if(accountDatabase.getNumAcct() == 0) {
            textFieldAD.setText("Account Database is empty!");
            return;
        }
        for(int i = 0; i < accountDatabase.getNumAcct(); i++){
            Account [] accounts = accountDatabase.getAccounts();
            accounts[i].balance -= accounts[i].fee();
            accounts[i].balance += accounts[i].monthlyInterest();
        }
        textFieldAD.setText(accountDatabase.accountsList());
    }

    /**
     * Event Handler for Calculate Interest and Fees button. Calls
     * accountsWithFeeAndInterest method in Account Database which returns String
     * of all accounts with their fees and monthly interest and prints it to text
     * area in Account Database tab in GUI.
     * @param event an Event representing some type of action
     */
    @FXML
    void calculateInterestandFees(ActionEvent event) {
        if(accountDatabase.getNumAcct() == 0) {
            textFieldAD.setText("Account Database is empty!");
            return;
        }
        textFieldAD.setText(accountDatabase.accountsWithFeeAndInterest());
    }

    /**
     * Event Handler for Print All Accounts button. Calls
     * accountsList method in AccountDatabase to get list of all accounts
     * as a String and prints it to text area in Account Database tab in GUI.
     * @param event an Event representing some type of action
     */
    @FXML
    void printAccounts(ActionEvent event) {
        if(accountDatabase.getNumAcct() == 0) {
            textFieldAD.setText("Account Database is empty!");
            return;
        }
        textFieldAD.setText(accountDatabase.accountsList());
    }

    /**
     * Event Handler for Print All Accounts Sorted by Type button. Calls
     * accountsByAccountType method in AccountDatabase to get list of all accounts,
     * sorted by type as a String and prints it to text area in Account Database
     * tab in GUI.
     * @param event an Event representing some type of action
     */
    @FXML
    void printByAccountType(ActionEvent event) {
        if(accountDatabase.getNumAcct() == 0) {
            textFieldAD.setText("Account Database is empty!");
            return;
        }
        textFieldAD.setText(accountDatabase.accountsByAccountType());
    }

    /**
     * Event handler for open button. This method calls the openHelper method to
     * open an account.
     * @param event an Event representing some type of action
     */
    @FXML
    void open(ActionEvent event) {
        if (firstNameOC.getText().isBlank() || lastNameOC.getText().isBlank() ||
                dobOC.getValue() == null || amountOC.getText().isBlank()
                || !accTypeIsSelected(true)) {
            textFieldOC.setText("Missing information for opening account.");
            clearText();
            return;
        }

        Date todayDate = new Date();
        Date date = new Date(dobOC.getValue().toString());
        if(date.compareTo(todayDate) == 1){
            textFieldOC.setText("Invalid Date of Birth!");
            clearText();
            return;
        }

        Profile profile = new Profile(firstNameOC.getText().replaceAll("\\s","") + " "
                + lastNameOC.getText().replaceAll("\\s","") + " " + dobOC.getValue().toString());

        double balance;
        try {
            balance = Double.parseDouble(amountOC.getText());
        } catch (NumberFormatException e) {
            textFieldOC.setText("Not a valid amount");
            clearText();
            return;
        }

        Account account = createAccount("O", profile, balance);
        openHelperMethod(account);
    }

    /**
     * opens an account if account holder doesn't have an account of the same type
     * in the database and all valid information is passed. this method can
     * also reopen an existing closed account
     * @param account (Account) containing information about account holder and
     * initial balance
     */
    void openHelperMethod(Account account) {
        boolean accountOpened;
        if(account == null) {
            clearText();
            return;
        }
        if(account.balance <= 0.0) {
            textFieldOC.setText("Initial deposit cannot be 0 or negative.");
            clearText();
            return;
        }

        if(account.getType().equals("Money Market")) {
            if(account.balance < MIN_AMT_FOR_MM) {
                textFieldOC.setText("Minimum of $2500 to open a MoneyMarket account.");
                clearText();
                return;
            }
        }
        int ccInd  = accountDatabase.callFind(new CollegeChecking(account.holder, false, 0, 0));
        int cInd = accountDatabase.callFind(new Checking(account.holder, false, 0));
        if((account.getType().equals("Checking") && ccInd != NOT_FOUND) ||
                (account.getType().equals("College Checking") && cInd != NOT_FOUND)) {
            textFieldOC.setText(account.holder.toString() + " same account(type) " +
                    "is in the database.");
            clearText();
            return;
        }

        accountOpened = accountDatabase.open(account);
        if(!accountOpened) {
            int accIndex = accountDatabase.reopen(account);
            if(accIndex == ACC_ALREADY_OPEN) {
                textFieldOC.setText(account.holder.toString() + " same account(type) " +
                        "is in the database.");
                clearText();
            }
            else {
                accountDatabase.getAccounts()[accIndex].closed = false;
                accountDatabase.getAccounts()[accIndex].balance = account.balance;
                if(accountDatabase.getAccounts()[accIndex].getType().equals("College Checking"))
                    accountDatabase.getAccounts()[accIndex] = account;
                textFieldOC.setText("Account reopened.");
            }
        }
        else
            textFieldOC.setText("Account opened.");
        clearText();
    }

    /**
     * Event Handler for close button. This method calls close from AccountDatabase
     * to close an account (if exists and hasn't been closed already).
     * @param event an Event representing some type of action
     */
    @FXML
    void close(ActionEvent event) {
        if (firstNameOC.getText().isBlank() || lastNameOC.getText().isBlank() ||
                dobOC.getValue() == null || !accTypeIsSelected(true)) {

            textFieldOC.setText("Missing information for closing account.");
            clearText();
            return;
        }

        if(!firstNameOC.getText().matches("[a-zA-Z]+") || !lastNameOC.getText().matches("[a-zA-Z]+")){
            textFieldOC.setText("Invalid first or last name.");
            clearText();
            return;
        }
        Date todayDate = new Date();
        Date date = new Date(dobOC.getValue().toString());

        if(date.compareTo(todayDate) == 1){
            textFieldOC.setText("Invalid Date of Birth!");
            clearText();
            return;
        }

        Profile profile = new Profile(firstNameOC.getText().replaceAll("\\s","") + " "
                + lastNameOC.getText().replaceAll("\\s","") + " " + dobOC.getValue().toString());

        Account account = createAccount("C", profile, 0);
        if(account == null) {
            clearText();
            return;
        }
        if (!accountDatabase.close(account)){
            textFieldOC.setText("Account is closed already.");
        }
        else {
            textFieldOC.setText("Account closed.");
        }
        clearText();
    }

    /**
     * Event Handler for deposit method. This method calls deposit in AccountDatabase
     * and deposits money into an (open) existing account in the databse, if the
     * amount is valid.
     * @param event an Event representing some type of action
     */
    @FXML
    void deposit(ActionEvent event) {
        if (firstName.getText().isBlank() || lastName.getText().isBlank() ||
                dob.getValue() == null || amount.getText().isBlank()
                || !accTypeIsSelected(false)) {
            textFieldDW.setText("Missing information for depositing.");
            clearText();
            return;
        }
        if(!firstName.getText().matches("[a-zA-Z]+") || !lastName.getText().matches("[a-zA-Z]+")){
            textFieldDW.setText("Invalid first or last name.");
            clearText();
            return;
        }
        Date date = new Date(dob.getValue().toString());
        Date todayDate = new Date();
        if(date.compareTo(todayDate) == 1){
            textFieldDW.setText("Invalid Date of Birth!");
            clearText();
            return;
        }

        Profile profile = new Profile(firstName.getText().replaceAll("\\s","") + " "
                + lastName.getText().replaceAll("\\s","") + " " + dob.getValue().toString());

        double balance;
        try {
            balance = Double.parseDouble(amount.getText());
        } catch (NumberFormatException e) {
            textFieldDW.setText("Not a valid amount");
            clearText();
            return;
        }
        Account account = createAccount("D", profile, balance);

        if(account == null) {
            clearText();
            return;
        }
        if(account.balance <= 0.0) {
            textFieldDW.setText("Deposit - amount cannot be 0 or negative.");
            clearText();
            return;
        }
        if(accountDatabase.callFind(account) == NOT_FOUND) {
            textFieldDW.setText(account.holder.toString() + " " + account.getType()
                    + " is not in the database.");
            clearText();
            return;
        }
        int idx  = accountDatabase.callFind(account);
        Account [] accounts = accountDatabase.getAccounts();
        if(accounts[idx].closed){
            textFieldDW.setText("Cannot deposit in closed account.");
            clearText();
            return;
        }
        accountDatabase.deposit(account);
        textFieldDW.setText("Deposit - balance updated.");
        clearText();
    }

    /**
     * Event Handler for withdraw button. This method calls withdraw in AccountDatabase
     * and withdraws money from an (open) existing account in database if amount is
     * valid and enough money exists with the account.
     * @param event an Event representing some type of action
     */
    @FXML
    void withdraw(ActionEvent event) {
        if (firstName.getText().isBlank() || lastName.getText().isBlank() ||
                dob.getValue() == null || amount.getText().isBlank() ||
                !accTypeIsSelected(false)) {
            textFieldDW.setText("Missing information for withdrawing.");
            clearText();
            return;
        }
        if(!firstName.getText().matches("[a-zA-Z]+") || !lastName.getText().matches("[a-zA-Z]+")){
            textFieldDW.setText("Invalid first or last name.");
            clearText();
            return;
        }
        Date date = new Date(dob.getValue().toString());
        Date todayDate = new Date();

        if(date.compareTo(todayDate) == 1){
            textFieldDW.setText("Invalid Date of Birth!");
            clearText();
            return;
        }
        Profile profile = new Profile(firstName.getText().replaceAll("\\s","") + " " +
                lastName.getText().replaceAll("\\s","") + " " + dob.getValue().toString());

        double balance;
        try {
            balance = Double.parseDouble(amount.getText());
        } catch (NumberFormatException e) {
            textFieldDW.setText("Not a valid amount");
            clearText();
            return;
        }
        Account account = createAccount("W", profile, balance);

        if(account == null) {
            clearText();
            return;
        }

        if (account.balance <= 0.0) {
            textFieldDW.setText("Withdraw - amount cannot be 0 or negative.");
            clearText();
            return;
        }

        if(accountDatabase.callFind(account) == NOT_FOUND) {
            textFieldDW.setText(account.holder.toString() + " " + account.getType()
                    + " is not in the database.");
            clearText();
            return;
        }
        int idx  = accountDatabase.callFind(account);
        Account [] accounts = accountDatabase.getAccounts();
        if(accounts[idx].closed){
            textFieldDW.setText("Cannot withdraw from closed account.");
            clearText();
            return;
        }
        boolean withdrawSuccessful;
        withdrawSuccessful = accountDatabase.withdraw(account);
        if(!withdrawSuccessful)
            textFieldDW.setText("Withdraw - insufficient fund.");
        else
            textFieldDW.setText("Withdraw - balance updated.");
        clearText();
    }

    /**
     * clears all the components in open/close and deposit/withdraw tabs in GUI
     * once an event occurs/button is pressed.
     */
    private void clearText(){
        amount.clear();
        amountOC.clear();
        lastName.clear();
        lastNameOC.clear();
        firstName.clear();
        firstNameOC.clear();
        dob.setValue(null);
        dobOC.setValue(null);
        if(campus.getSelectedToggle()!= null) {
            campus.getSelectedToggle().setSelected(false);
        }
        if(accountType.getSelectedToggle() != null) {
            accountType.getSelectedToggle().setSelected(false);
        }
        loyal.setSelected(false);

        newBrunswickOC.setDisable(true);
        newarkOC.setDisable(true);
        camdenOC.setDisable(true);
        loyal.setDisable(true);
    }

    /**
     * converts a string specifying campus to equivalent integer (campus code)
     * @param code (String) campus name
     * @return (int) campus code; 1 for New Brunswick, 2 for Newark, 3
     * for Camden
     */
    private int toIntCampusCode(String code){
        if(code.equals("New Brunswick")) {
            return NEW_BRUNSWICK;
        }
        else if(code.equals("Newark")) {
            return NEWARK;
        }
        else {
            return CAMDEN;
        }
    }

    /**
     * creates an instance of Account (checking, savings, money market,
     * or college checking) depending on what command user inputs
     * @param command (String) can be O/C/D/W for open/close/deposit/withdraw respectively
     * @param profile (Profile) specifying details of account holder
     * @param balance (double) specifying initial balance in account being created
     * @return an instance of Account
     */
    public Account createAccount(String command, Profile profile, double balance) {
        if(((RadioButton) accountType.getSelectedToggle()).getText().equals("Checking")){
            return new Checking(profile, false, balance);
        }
        else if(((RadioButton) accountType.getSelectedToggle()).getText()
                .equals("College Checking")) {
            int campusCode = 0;
            if(command.equals("O")) {
                if(campus.getSelectedToggle() == null){
                    textFieldOC.setText("Please select campus");
                    clearText();
                    return null;
                }
                campusCode = toIntCampusCode(((RadioButton)campus.getSelectedToggle())
                        .getText());
            }
            return new CollegeChecking(profile, false, balance, campusCode);
        }
        else if(((RadioButton) accountType.getSelectedToggle()).getText()
                .equals("Savings")) {
            if(command.equals("O")) {
                if(loyal.isSelected()) {
                    return new Savings(profile, false, balance, 1);
                }
                else {
                    return new Savings(profile, false, balance, 0);
                }
            }
            return new Savings(profile, false,balance, 0);
        }
        else if(((RadioButton) accountType.getSelectedToggle()).getText()
                .equals("Money Market")) {
            return new MoneyMarket(profile, false, balance);
        }
        return null;
    }
}
