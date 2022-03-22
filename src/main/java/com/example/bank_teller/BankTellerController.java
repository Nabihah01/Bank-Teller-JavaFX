package com.example.bank_teller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class BankTellerController {
    AccountDatabase accountDatabase = new AccountDatabase();
    public final static double MIN_AMT_FOR_MM = 2500.00;
    public final static int ACC_ALREADY_OPEN = -1;
    public final static int NOT_FOUND = -1;
    private final static int NEW_BRUNSWICK = 0;
    private final static int NEWARK = 1;
    private final static int CAMDEN = 2;

    @FXML
    private Tab accountDatabaseTab, openCloseTab, depositWithdrawTab;

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

    @FXML
    void initialize() {
        newBrunswickOC.setDisable(true);
        newarkOC.setDisable(true);
        camdenOC.setDisable(true);
        loyal.setDisable(true);
    }

    //event handling when user clicks on savings,checking,mm,or cc(disabling/enabling elements)
    @FXML
    void savingsButton(ActionEvent event) {
        newBrunswickOC.setDisable(true); newarkOC.setDisable(true);
        camdenOC.setDisable(true);
        loyal.setDisable(false);
        loyal.setSelected(false);
    }
    @FXML
    void checkingButton(ActionEvent event){
        newBrunswickOC.setDisable(true); newarkOC.setDisable(true);
        camdenOC.setDisable(true);
        loyal.setDisable(true);
    }
    @FXML
    void mmButton(ActionEvent event){
        newBrunswickOC.setDisable(true); newarkOC.setDisable(true);
        camdenOC.setDisable(true);
//        loyal.setDisable(false);
        loyal.setSelected(true);
    }
    @FXML
    void ccButton(ActionEvent event){
        newBrunswickOC.setDisable(false);
        newarkOC.setDisable(false);
        camdenOC.setDisable(false);
        loyal.setDisable(true);
    }

    boolean accTypeIsSelected(boolean isOCTab) {
        if(isOCTab) {
            return checkingOC.isSelected() || savingsOC.isSelected() ||
                    collegeCheckingOC.isSelected() || moneyMarketOC.isSelected();
        } else {
            return checking.isSelected() || savings.isSelected() ||
                    moneyMarket.isSelected() || collegeChecking.isSelected();
        }
    }

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
        textFieldAD.setText(accountDatabase.print());
    }

    @FXML
    void calculateInterestandFees(ActionEvent event) {
        if(accountDatabase.getNumAcct() == 0) {
            textFieldAD.setText("Account Database is empty!");
            return;
        }
        textFieldAD.setText(accountDatabase.printFeeAndInterest());
    }

    @FXML
    void printAccounts(ActionEvent event) {
        if(accountDatabase.getNumAcct() == 0) {
            textFieldAD.setText("Account Database is empty!");
            return;
        }
        textFieldAD.setText(accountDatabase.print());
    }

    @FXML
    void printByAccountType(ActionEvent event) {
        if(accountDatabase.getNumAcct() == 0) {
            textFieldAD.setText("Account Database is empty!");
            return;
        }
        textFieldAD.setText(accountDatabase.printByAccountType());
    }

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

        Profile profile = new Profile(firstNameOC.getText() + " " + lastNameOC.getText() + " " + dobOC.getValue().toString());

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

    @FXML
    void close(ActionEvent event) {
        if (firstNameOC.getText().isBlank() || lastNameOC.getText().isBlank() ||
                dobOC.getValue() == null || !accTypeIsSelected(true)) {

            textFieldOC.setText("Missing information for closing account.");
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

        Profile profile = new Profile(firstNameOC.getText() + " " + lastNameOC.getText()
                + " " + dobOC.getValue().toString());

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

    @FXML
    void deposit(ActionEvent event) {
        if (firstName.getText().isBlank() || lastName.getText().isBlank() ||
                dob.getValue() == null || amount.getText().isBlank()
                || !accTypeIsSelected(false)) {
            textFieldDW.setText("Missing information for depositing.");
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

        Profile profile = new Profile(firstName.getText() + " " + lastName.getText() + " "
                + dob.getValue().toString());

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
        accountDatabase.deposit(account);
        textFieldDW.setText("Deposit - balance updated.");
        clearText();
    }

    @FXML
    void withdraw(ActionEvent event) {
        if (firstName.getText().isBlank() || lastName.getText().isBlank() ||
                dob.getValue() == null || amount.getText().isBlank() ||
                !accTypeIsSelected(false)) {
            textFieldDW.setText("Missing information for withdrawing.");
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
        Profile profile = new Profile(firstName.getText() + " " + lastName.getText()
                + " " + dob.getValue().toString());

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

        boolean withdrawSuccessful;
        withdrawSuccessful = accountDatabase.withdraw(account);
        if(!withdrawSuccessful)
            textFieldDW.setText("Withdraw - insufficient fund.");
        else
            textFieldDW.setText("Withdraw - balance updated.");
        clearText();
    }

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
