package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AccountingLedger {

    static Scanner theScanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        menu();
    }

    public static void menu() throws IOException {
        boolean isRunning = true;  //Setting up an " on/off "
        System.out.println("-----Accounting Ledger-----");
        while(isRunning){
            System.out.println("Welcome!");
            System.out.println("Select from the following options:"); //user will select from options to pull up a specific screen
            System.out.println("""
                    1.Add a Deposit
                    2.Make a Payment
                    3.Display Ledger
                    4.Exit Application
                    """);
            int userInput = theScanner.nextInt(); //caching the users input
            switch(userInput){
                case 1:
                    addDeposit();
                    break;
                case 2:
                    makePayment();
                    break;
                case 3:
                   // displayLedger();
                    break;
                case 4:
                    isRunning = false;

            }

        }

    }

    public static void addDeposit() throws IOException {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss"); //getting time of transaction added
            String formattedTime = now.format(fmt);
            FileWriter fileWriter = new FileWriter("src/main/resources/transactions.csv", true); //whatever user writes will route to that csv file
            BufferedWriter buffWriter = new BufferedWriter(fileWriter);
            theScanner.nextLine();
            System.out.print("What is the description for the transaction? : ");
            String userTransaction = theScanner.nextLine();
            System.out.print("Who is the vendor? : ");
            String userVendor = theScanner.nextLine();
            System.out.print("What is the total amount of the transaction? ");
            double userAmount = theScanner.nextDouble();

            buffWriter.write(formattedTime + "|" + userTransaction + "|" + userVendor + "|" + userAmount + "\n"); // writing the input to the csv file
            buffWriter.close();
        } catch (Exception e) {
            System.out.println("The transaction was not added to ledger");
        }
    }
    public static void makePayment(){
        try{
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss"); //getting time of transaction added
            String formattedTime = now.format(fmt);
            FileWriter fileWriter = new FileWriter("src/main/resources/transactions.csv", true); //whatever user writes will route to that csv file
            BufferedWriter buffWriter = new BufferedWriter(fileWriter);
            theScanner.nextLine();
            System.out.print("What is the description for the transaction? : ");
            String userTransaction = theScanner.nextLine();
            System.out.print("Who is the vendor? : ");
            String userVendor = theScanner.nextLine();
            System.out.print("What is the total amount of the transaction? ");
            double userAmount = theScanner.nextDouble();
            String completeLine = String.format(formattedTime + "|" + userTransaction + "|" + userVendor + "|" + -Math.abs(userAmount) + "\n");
            buffWriter.write(completeLine); // writing the input to the csv file and making the transaction amount always come back as negative
            buffWriter.close();

        }catch(Exception e){
            System.out.println("Could not add transaction to ledger");
        }
    }
}
