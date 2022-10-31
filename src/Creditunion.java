//Written by: Ilja Cekinovs
//Student ID: B00139696
//Date: 20.4.2022
//Program function: Credit Union account, creating a file and storing account details to it, also read the account details.
//Importing java classes

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.DecimalFormat;

//main class that extends JFrame and implements an action listener
public class Creditunion extends JFrame implements ActionListener {
    // declaring variables for the GUI part
    private JTextField accountField, firstNameField, lastNameField, balanceField,
            logementField, withdrawalField, overdraftField, newOverdraftField;
    private JButton enter, done, closeAccount, loadAccount, makeLodement, makeWithdrawal, setNewOverdraft;
    private JLabel accountLabel, firstNameLabel, lastNameLabel, balanceLabel, logementLabel, withdrawlLabel, overDraftLabel, newOver;
    private RandomAccessFile output, input;    //file for input and output
    private CredingunionAccount data;
    private JFrame frame;//Frame declaration
    private JPanel buttonPane, fieldPanel, exitButton;

    //constructor
    public Creditunion() {

        initializeFile();//method call for creating a file

        //open file
        data = new CredingunionAccount();

        try {
            output = new RandomAccessFile("creditUnion.dat", "rw");//creating red write file
            input = new RandomAccessFile("creditUnion.dat", "rw");

            System.out.println("In first try block");
        } catch (IOException e) {
            System.err.println(e.toString());
            System.exit(1);
        }
        frame = new JFrame("Credit union Account Manager");//creating a frame to add components to it

        buttonPane = new JPanel();//button pane
        fieldPanel = new JPanel();//text fields pane
        exitButton = new JPanel();//exit button pane

        //creating fiels,buttons and adding action listener
        accountField = new JTextField();
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        balanceField = new JTextField();
        overdraftField = new JTextField();
        enter = new JButton("Add Account");
        enter.addActionListener(this);
        done = new JButton("Save and Exit");
        done.addActionListener(this);
        closeAccount = new JButton("Close Account");
        closeAccount.addActionListener(this);
        loadAccount = new JButton("Load Account");
        loadAccount.addActionListener(this);
        logementField = new JTextField();
        makeLodement = new JButton("Make lodgement");
        makeLodement.addActionListener(this);
        withdrawalField = new JTextField();
        makeWithdrawal = new JButton("Withdraw");
        makeWithdrawal.addActionListener(this);
        newOverdraftField = new JTextField();
        setNewOverdraft = new JButton("Set new overdraft limit");
        setNewOverdraft.addActionListener(this);
        //creating JLabels
        accountLabel = new JLabel("Account number");
        firstNameLabel = new JLabel("First name");
        lastNameLabel = new JLabel("Last name");
        balanceLabel = new JLabel("Account Balance");
        logementLabel = new JLabel("Logement Amount");
        withdrawlLabel = new JLabel("Withdraw amount");
        overDraftLabel = new JLabel("Overdraft amount");
        newOver = new JLabel("New overdraft amount");

        //I have created 3 layouts for exit button, text fields and buttons
        exitButton.setLayout(new GridLayout(1, 1));
        fieldPanel.setLayout(new GridLayout(8, 1));
        buttonPane.setLayout(new GridLayout(4, 3));
        //adding components to the frame
        fieldPanel.add(accountLabel);
        fieldPanel.add(accountField);
        fieldPanel.add(firstNameLabel);
        fieldPanel.add(firstNameField);
        fieldPanel.add(lastNameLabel);
        fieldPanel.add(lastNameField);
        fieldPanel.add(balanceLabel);
        fieldPanel.add(balanceField);
        fieldPanel.add(overDraftLabel);
        fieldPanel.add(overdraftField);
        fieldPanel.add(logementLabel);
        fieldPanel.add(logementField);
        fieldPanel.add(withdrawlLabel);
        fieldPanel.add(withdrawalField);
        fieldPanel.add(newOver);
        fieldPanel.add(newOverdraftField);
        buttonPane.add(enter);
        buttonPane.add(loadAccount);

        componentsStyle();//method call for decoration


        buttonPane.add(makeLodement);
        buttonPane.add(makeWithdrawal);
        buttonPane.add(closeAccount);
        buttonPane.add(setNewOverdraft);
        exitButton.add(done);
        logementField.setEditable(false);
        withdrawalField.setEditable(false);
        newOverdraftField.setEditable(false);
        logementField.setText("load account first");
        withdrawalField.setText("load account first");
        newOverdraftField.setText("load account first");
        frame.add(fieldPanel, BorderLayout.PAGE_START);
        frame.add(buttonPane, BorderLayout.PAGE_END);
        frame.add(exitButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    //method containing the button styles and frame decorations
    public void componentsStyle() {
        //I have the button borders commented out because i did not like how they looked,i couldnt not get them to pop out like the default buttons
//        done.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 1, 1, 1, new java.awt.Color(109, 114, 122)));
//        enter.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 1, 1, 1, new java.awt.Color(109, 114, 122)));
//        loadAccount.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(109, 114, 122)));
//        closeAccount.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 1, 1, 1, new java.awt.Color(109, 114, 122)));
//        makeLodement.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 1, 1, 1, new java.awt.Color(109, 114, 122)));
//        makeWithdrawal.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 1, 1, 1, new java.awt.Color(109, 114, 122)));
//        setNewOverdraft.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 1, 1, 1, new java.awt.Color(109, 114, 122)));
        frame.setBackground(Color.lightGray);
        fieldPanel.setBackground(Color.lightGray);
        buttonPane.setBackground(Color.lightGray);
        exitButton.setBackground(Color.lightGray);


    }


    //create method for adding records to file
    public void addRecord() {
        int accountNumber = 0;
        Double balance;
        Double overdraftLimit;

        if (!accountField.getText().equals("")) ;
        {
            try {
                accountNumber = Integer.parseInt(accountField.getText());
                balance = Double.parseDouble(balanceField.getText());
                overdraftLimit = Double.parseDouble(overdraftField.getText());
                System.out.println("In second try block");

                if (accountNumber < 1 || accountNumber > 100)  //validate account number is in range
                {
                    JOptionPane.showMessageDialog(this, "Account number must be between 1 & 100");
                } else if (accountNumber > 0 && accountNumber <= 100) {

                    //read file to check if account number already exists.
                    output.seek((long) (accountNumber - 1) * CredingunionAccount.size());
                    data.read(output);

                    if (data.getAccount() == accountNumber)  //if a/c exists,display dialog box to user
                    {
                        JOptionPane.showMessageDialog(this, "Account already exists! Please try a different account number");
                        accountField.setText("");   // clear account textfield
                    } else   //once conditions are met, data is written to file.
                    {
                        data.setAccount(accountNumber);
                        data.setFirstName(firstNameField.getText());
                        data.setLastName(lastNameField.getText());
                        data.setBalance(balance.doubleValue());
                        data.setOverdraftLimit(overdraftLimit.doubleValue());
                        output.seek((long) (accountNumber - 1) * CredingunionAccount.size());
                        data.write(output);
                        accountField.setText("");
                        firstNameField.setText("");
                        lastNameField.setText("");
                        balanceField.setText("");
                        overdraftField.setText("");
                        JOptionPane.showMessageDialog(this, "Account Details Saved");
                    }
                }


            }//end try statement
            catch (NumberFormatException nfe) {
                System.err.println("You must enter an integer account number");
            } catch (IOException io) {
                System.err.println("error during write to file\n" + io.toString());
            }

        }//end initial if statement
    } //end addRecord method

    //action performed method looking for user interaction
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enter)
            addRecord();//method call IF user preses add account
        if (e.getSource() == closeAccount) {
            deleteAccount();//method call if user presses delete account
        }
        if (e.getSource() == loadAccount) {

            Boolean accountLoaded = loadAccount();
            if (accountLoaded) {
                logementField.setText("");
                withdrawalField.setText("");
                newOverdraftField.setText("");
                logementField.setEditable(true);
                withdrawalField.setEditable(true);
                newOverdraftField.setEditable(true);
            }
        }
        if (e.getSource() == makeLodement) {
            makeLogement();
        }
        if (e.getSource() == makeWithdrawal) {
            makeWithdrawal();
        }
        if (e.getSource() == setNewOverdraft) {
            setNewOverdraft();
        }

        if (e.getSource() == done) {
            try {
                output.close();
            } catch (IOException io) {
                System.err.println("File not closed properly\n" + io.toString());
                System.exit(1);
            }
            closeFile();
        }
    }

    private void closeFile() {
        try {
            output.close();
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Error closing file \n" + e.toString());
        }
    }

    private void deleteAccount() {
        try {

            int accountNumber = Integer.parseInt(accountField.getText());

            data.setAccount(0);
            data.setFirstName(null);
            data.setLastName(null);
            data.setBalance(0);
            data.setOverdraftLimit(0);

            output.seek((long) (accountNumber - 1) * CredingunionAccount.size());
            data.write(output);

            JOptionPane.showMessageDialog(this, "Account has been deleted");
            accountField.setText("");
            firstNameField.setText("");
            lastNameField.setText("");
            balanceField.setText("");
            overdraftField.setText("");
        }//end try
        catch (NumberFormatException nfe) {
            System.err.println("You must enter an integer account number");
        } catch (IOException io) {
            System.err.println("error during write to file\n" + io.toString());
        }
    }

    //loading account method
    private Boolean loadAccount() {
        DecimalFormat twoDigits = new DecimalFormat("0.00");
        try {
            int accountNumber = Integer.parseInt(accountField.getText());

            if (accountNumber < 1 || accountNumber > 100) {
                JOptionPane.showMessageDialog(this, "Account does not exist");
                return false;
            } else {
                input.seek((accountNumber - 1) * CredingunionAccount.size());
                data.read(input);

                accountField.setText(String.valueOf(data.getAccount()));
                firstNameField.setText(data.getFirstName());
                lastNameField.setText(data.getLastName());
                balanceField.setText(String.valueOf(
                        twoDigits.format(data.getBalance())));
                overdraftField.setText(String.valueOf(
                        twoDigits.format(data.getOverdraftLimit())));
            }
            if (data.getAccount() == 0) {
                JOptionPane.showMessageDialog(this, "Account does not exist");
                accountField.setText("");
                return false;
            }
        }//end try statement
        catch (EOFException eof) {
            closeFile();
        } catch (IOException e) {
            System.err.println("Error during read from file\n " + e.toString());
            System.exit(1);
        }
        return true;
    }

    //method for logement
    private void makeLogement() {
        try {
            int accountNumber = Integer.parseInt(accountField.getText());
            Double lodgementAmount = Double.parseDouble(logementField.getText());

            data.setBalance(data.getBalance() + lodgementAmount);

            output.seek((long) (accountNumber - 1) * CredingunionAccount.size());
            data.write(output);

            JOptionPane.showMessageDialog(this, "Money has been lodged");
            balanceField.setText(String.valueOf(data.getBalance()));
            logementField.setText("");
        }//end try
        catch (NumberFormatException nfe) {
            System.err.println("You must enter an integer account number");
        } catch (IOException io) {
            System.err.println("error during write to file\n" + io.toString());
        }
    }

    //make withdrawal method
    private void makeWithdrawal() {
        try {
            int accountNumber = Integer.parseInt(accountField.getText());
            Double withdrawalAmount = Double.parseDouble(withdrawalField.getText());

            if (withdrawalAmount > data.getBalance() + data.getOverdraftLimit()) {
                JOptionPane.showMessageDialog(this, "Insufficient funds");
                return;
            }
            data.setBalance(data.getBalance() - withdrawalAmount);

            output.seek((long) (accountNumber - 1) * CredingunionAccount.size());
            data.write(output);

            JOptionPane.showMessageDialog(this, "Money has been withdrawn");
            balanceField.setText(String.valueOf(data.getBalance()));
            withdrawalField.setText("");
        }//end try
        catch (NumberFormatException nfe) {
            System.err.println("You must enter an integer account number");
        } catch (IOException io) {
            System.err.println("error during write to file\n" + io.toString());
        }
    }

    //setting overdraft method
    private void setNewOverdraft() {
        try {
            int accountNumber = Integer.parseInt(accountField.getText());
            Double overdraftAmount = Double.parseDouble(newOverdraftField.getText());

            data.setOverdraftLimit(overdraftAmount);

            output.seek((long) (accountNumber - 1) * CredingunionAccount.size());
            data.write(output);

            JOptionPane.showMessageDialog(this, "New overdraft limit has been set");
            overdraftField.setText(String.valueOf(data.getOverdraftLimit()));
            newOverdraftField.setText("");
        }//end try
        catch (NumberFormatException nfe) {
            System.err.println("You must enter an integer account number");
        } catch (IOException io) {
            System.err.println("error during write to file\n" + io.toString());
        }
    }

    //Method for creating a blank file if the file does not exist
    private void initializeFile() {
        File file = new File("creditUnion.dat");
        if (!file.exists()) {
            CredingunionAccount blank = new CredingunionAccount();
            try {
                input = new RandomAccessFile("creditUnion.dat", "rw");
                for (int i = 0; i < 100; i++) {
                    blank.write(input);
                }
                System.out.println("File created\n");
            } catch (IOException e) {
                System.err.println("File not opened properly\n" + e.toString());
                System.exit(1);
            }
        } else {
            System.out.println("File exists, skipping initilization\n");
        }
    }


    public static void main(String[] args) {

        new Creditunion();

    }
}

