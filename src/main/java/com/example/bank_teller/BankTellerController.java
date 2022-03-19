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
    private Tab accountDatabaseTab;

    @FXML
    private ToggleGroup accountType;

    @FXML
    private TextField amount;

    @FXML
    private RadioButton camdenOC;

    @FXML
    private ToggleGroup campus;

    @FXML
    private RadioButton checking;

    @FXML
    private RadioButton checkingOC;

    @FXML
    private RadioButton collegeChecking;

    @FXML
    private RadioButton collegeCheckingOC;

    @FXML
    private Tab depositWithdrawTab;

    @FXML
    private DatePicker dob;

    @FXML
    private DatePicker dobOC;

    @FXML
    private TextField firstName;

    @FXML
    private TextField firstNameOC;

    @FXML
    private TextField lastName;

    @FXML
    private TextField lastNameOC;

    @FXML
    private CheckBox loyal;

    @FXML
    private RadioButton moneyMarket;

    @FXML
    private RadioButton moneyMarketOC;

    @FXML
    private RadioButton newBrunswickOC;

    @FXML
    private RadioButton newarkOC;

    @FXML
    private Tab openCloseTab;

    @FXML
    private Button printAccounts;

    @FXML
    private Button printByAccountType;

    @FXML
    private RadioButton savings;

    @FXML
    private RadioButton savingsOC;

    @FXML
    private TextArea textFieldAD;

    @FXML
    private TextArea textFieldDW;

    @FXML
    private TextArea textFieldOC;

    @FXML
    void applyInterestAndFees(ActionEvent event) {
        //add print statements to textfield
        if(accountDatabase.getNumAcct() == 0) {
            textFieldAD.setText("Account Database is empty!");
            return;
        }
        for(int i = 0; i < accountDatabase.getNumAcct(); i++){
            Account [] accounts = accountDatabase.getAccounts();
            accounts[i].balance -= accounts[i].fee();
            accounts[i].balance += accounts[i].monthlyInterest();
        }
//        textFieldAD.setText(accountDatabase.print());

    }

    @FXML
    void calculateInterestandFees(ActionEvent event) {

    }

    @FXML
    void close(ActionEvent event) {
        Profile profile = new Profile(firstNameOC.getText() + lastNameOC.getText() + dobOC.toString());
        Date todayDate = new Date();
        Date date = new Date(dobOC.toString());
        if(!date.isValid() || date.compareTo(todayDate) == 1){
            textFieldOC.setText("Invalid Date of Birth!");
            return;
        }
        Account account = createAccount("C", profile);
        if(account == null){
            return;
        }
        if (!accountDatabase.close(account)){
            textFieldOC.setText("Account is closed already.");
        }
        else {
            textFieldOC.setText("Account closed.");
        }

    }

    @FXML
    void deposit(ActionEvent event) {
        Profile profile = new Profile(firstName.getText() + lastName.getText() + dob.toString());
        Date todayDate = new Date();
        Date date = new Date(dobOC.toString());
        if(!date.isValid() || date.compareTo(todayDate) == 1){
            textFieldOC.setText("Invalid Date of Birth!");
            return;
        }
        Account account = createAccount("D", profile);
        if(account == null){
            return;
        }
        if(account.balance <= 0.0) {
            textFieldDW.setText("Deposit - amount cannot be 0 or negative.");
            return;
        }
        if(accountDatabase.callFind(account) == NOT_FOUND) {
            textFieldDW.setText(account.holder.toString() + " " + account.getType() + " is not in the database.");
            return;
        }
        accountDatabase.deposit(account);
        textFieldDW.setText("Deposit - balance updated.");

    }

    @FXML
    void open(ActionEvent event) {
        Profile profile = new Profile(firstNameOC.getText() + lastNameOC.getText() + dobOC.toString());
        Date todayDate = new Date();
        Date date = new Date(dobOC.toString());
        if(!date.isValid() || date.compareTo(todayDate) == 1){
            textFieldOC.setText("Invalid Date of Birth!");
            return;
        }
        Account account = createAccount("O", profile);
        boolean accountOpened;
        if(account == null)
            return;
        if(account.balance <= 0.0) {
            textFieldOC.setText("Initial deposit cannot be 0 or negative.");
            return;
        }

        if(account.getType().equals("Money Market")) {
            if(account.balance < MIN_AMT_FOR_MM) {
                textFieldOC.setText("Minimum of $2500 to open a MoneyMarket account.");
                return;
            }
        }
        int ccInd  = accountDatabase.callFind(new CollegeChecking(account.holder, false, 0, 0));
        int cInd = accountDatabase.callFind(new Checking(account.holder, false, 0));
        if((account.getType().equals("Checking") && ccInd != NOT_FOUND) ||
                (account.getType().equals("College Checking") && cInd != NOT_FOUND)) {
            textFieldOC.setText(account.holder.toString() + " same account(type) " +
                    "is in the database.");
            return;
        }

        accountOpened = accountDatabase.open(account);
        if(!accountOpened) {
            int accIndex = accountDatabase.reopen(account);
            if(accIndex == ACC_ALREADY_OPEN)
                textFieldOC.setText(account.holder.toString() + " same account(type) " +
                        "is in the database.");
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

    }

    @FXML
    void printAccounts(ActionEvent event) {
        //add print statements to textfield
        if(accountDatabase.getNumAcct() == 0) {
            textFieldAD.setText("Account Database is empty!");
            return;
        }
        System.out.println("*list of accounts with fee and monthly interest*");
        accountDatabase.printFeeAndInterest();
        System.out.println("*end of list*");

    }

    @FXML
    void withdraw(ActionEvent event) {

    }

    public Account createAccount(String command, Profile profile) {
        //possibly put balance in respective open, withdraw, deposit methods
        double balance = 0;

        if(command.equals("O") || command.equals("W") || command.equals("D")){
            balance = Double.parseDouble(amount.getText());
        }

        if(((RadioButton) accountType.getSelectedToggle()).getText().equals("Checking")){
            return new Checking(profile, false, balance);
        }
        else if(((RadioButton) accountType.getSelectedToggle()).getText().equals("College Checking")) {
            int campusCode;
            if(command.equals("O")) {
                campusCode= Integer.parseInt(((RadioButton) campus.getSelectedToggle()).getText());
                if(campusCode != NEW_BRUNSWICK && campusCode != NEWARK && campusCode != CAMDEN){
                    textFieldOC.setText("Invalid campus code!");
                    return null;
                }
            }
            else
                campusCode = 0; //DUMMY for W, D, or C

            return new CollegeChecking(profile, false, balance, campusCode);
        }
        else if(((RadioButton) accountType.getSelectedToggle()).getText().equals("Savings")) {
            if(command.equals("O")) {
                if(loyal.isSelected()){
                    return new Savings(profile, false,balance, 1);
                }
            }
            return new Savings(profile, false,balance, 0);
        }
        else if(((RadioButton) accountType.getSelectedToggle()).getText().equals("Money Market")) {
            return new MoneyMarket(profile, false, balance);
        }
        return null;
    }

}
