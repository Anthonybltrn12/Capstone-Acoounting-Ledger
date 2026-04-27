package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
                    ledgerMenu();
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

    public static ArrayList<Transaction> getLedger() throws IOException {
        ArrayList<Transaction> transactionList = new ArrayList<>(); //creating hte array list to add transactions
        FileReader fileReader = new FileReader("src/main/resources/transactions.csv");
        BufferedReader lineReader = new BufferedReader(fileReader);
        String transLine;

        while((transLine = lineReader.readLine()) != null){  //looping through the csv file and stopping once a line is empty
            String[] transArray = transLine.split("\\|");
            transactionList.add(new Transaction(transArray[0],transArray[1],transArray[2],transArray[3],Double.parseDouble(transArray[4]))); //adding each split up part into the object
        }

        return transactionList;

    }
    public static void ledgerMenu() throws IOException {

            System.out.println("""      
                    1.All Transactions
                    2.Deposits Only
                    3.Payments Only
                    4.Reports
                    """);   //getting input from user on how to display transactions
            int userLedgerOption = theScanner.nextInt();
            switch (userLedgerOption) {
                case 1:
                    displayLedger();   //displays all the transactions
                    break;
                case 2:
                    displayDeposits();    //displays the deposits only
                    break;
                case 3:
                    displayPayments();    //displays the payments only
                    break;
                case 4:
                    reports();
                    break;
            }

    }
    public static void displayLedger() throws IOException {
        ArrayList<Transaction> transactionList = getLedger();

        for(int i = 0; i < transactionList.size(); i++){
            Transaction transaction = transactionList.get(i); //get each variable from the object
            System.out.printf("%s|%s|%s|%s|%.2f \n", transaction.getDate(), transaction.getTime(), transaction.getName(), transaction.getType(), transaction.getPrice());
        }
    }

    public static void displayDeposits() throws IOException {
        ArrayList<Transaction> transactionList = getLedger();
        for(Transaction trans : transactionList){
            if (trans.getPrice() > 0){            //decides if the transaction was a deposit
                System.out.printf("%s|%s|%s|%s|%.2f \n", trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
            }
        }
    }
    public static void displayPayments() throws IOException {
        ArrayList<Transaction> transactionList = getLedger();
        for(Transaction trans : transactionList){
            if (trans.getPrice() < 0){           //decides if the transaction is a payment
                System.out.printf("%s|%s|%s|%s|%.2f \n", trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
            }
        }
    }
    public static void reports() throws IOException {
        System.out.println("""  
                Choose your report option
                1. Month to Date
                2. Previous Month
                3. Year to Date
                4. Previous Year
                5. Search by Vendor
                6. Back 
                """);     //user can choose the report they want
        int userInput = theScanner.nextInt();

        switch (userInput){
            case 1:
                monthToDate();
                break;
            case 3:
                yearToDate();
                break;
            case 6:
                ledgerMenu();    //takes user back to ledger menu

        }
    }

    public static void monthToDate() throws IOException {
        ArrayList<Transaction> transList = getLedger();
        LocalDate date = LocalDate.now();
        DateTimeFormatter fmd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = fmd.format(date.getMonth());
        for(Transaction trans : transList){                      //seeing if the current date matches with transaction date
            if(formattedDate.equalsIgnoreCase(trans.getDate()) ){
                System.out.printf("%s|%s|%s|%s|%.2f \n", trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
            }
        }
    }
    public static void yearToDate() throws IOException {
        ArrayList<Transaction> transList = getLedger();
        LocalDate date = LocalDate.now();   //finding current date
        DateTimeFormatter fmd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for(Transaction trans : transList){
            String[] transYear = trans.getDate().split("-");   //splitting the date to get the year
            if(date.getYear() == Integer.parseInt(transYear[0]) ){  //seeing if year in each transaction macthes the current year
                System.out.printf("%s|%s|%s|%s|%.2f \n", trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
            }
        }

    }

}
