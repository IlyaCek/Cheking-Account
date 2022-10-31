//Written by: Ilja Cekinovs
//Student ID: B00139696
//Date: 20.4.2022
//Program function: Account class containing the account details
import java.io.*;
import javax.swing.*;

public class CredingunionAccount {
    //declaring variables
    private int account;
    private String lastName;
    private String firstName;
    private double balance;

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    private double overdraftLimit;
//read method to random file
    public void read(RandomAccessFile file) throws IOException {
        account = file.readInt();

        char first[] = new char[15];//creating an array

        for (int i = 0; i < first.length; i++)//reading through a for loop
            first[i] = file.readChar();

        firstName = new String(first);

        char last[] = new char[15];

        for (int i = 0; i < last.length; i++)
            last[i] = file.readChar();

        lastName = new String(last);

        balance = file.readDouble();
        overdraftLimit = file.readDouble();
    }
//write method to random file
    public void write(RandomAccessFile file) throws IOException {
        StringBuffer buf;

        file.writeInt(account);

        if (firstName != null)
            buf = new StringBuffer(firstName);

        else
            buf = new StringBuffer(15);

        buf.setLength(15);

        file.writeChars(buf.toString());

        if (lastName != null)
            buf = new StringBuffer(lastName);
        else
            buf = new StringBuffer(15);

        buf.setLength(15);

        file.writeChars(buf.toString());

        file.writeDouble(balance);
        file.writeDouble(overdraftLimit);
    }

    public void setAccount(int a) {
        account = a;
    }

    public int getAccount() {
        return account;
    }

    public void setFirstName(String f) {
        firstName = f;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String l) {
        lastName = l;
    }

    public String getLastName() {
        return lastName;
    }

    public void setBalance(double b) {
        balance = b;
    }

    public double getBalance() {
        return balance;
    }

    //determines size (bytes) of each file
    public static int size() {
        return 80;
    }
}


