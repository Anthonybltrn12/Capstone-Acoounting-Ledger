package com.pluralsight;
//Time to clean up and design!

import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AccountingLedger {

    static Scanner theScanner = new Scanner(System.in);
    static final String GREEN = "\u001B[32m";  //creating global color variables to change the text color depending on the type of transaction
    static final String RESET = "\u001B[0m";
    static final String RED = "\u001B[31m";
    static final String CYAN = "\u001B[36m";

    public static void main(String[] args) throws IOException, InterruptedException {
        menu();
    }

    public static void menu() throws IOException, InterruptedException {
        boolean isRunning = true;  //Setting up an " on/off "
        System.out.println("What is your name? :");
        String userName = theScanner.nextLine();
        while (isRunning) {
            System.out.printf("\n-----%s's Accounting Ledger-----", userName);  //users name will concat to personalize the ledger
            System.out.println("\n\t\t   Welcome!");
            System.out.println("Select from the following options:"); //user will select from options to pull up a specific screen
            System.out.println("""
                    1.Add a Deposit
                    2.Make a Payment
                    3.Display Ledger
                    4.Exit Application
                    """);
            System.out.print(CYAN + "Option Here:" + RESET);
            int userInput = theScanner.nextInt(); //caching the users input
            switch (userInput) {
                case 1:
                    addDeposit();
                    Thread.sleep(1000);
                    break;
                case 2:
                    makePayment();
                    Thread.sleep(1000);
                    break;
                case 3:
                    ledgerMenu();
                    break;
                case 4:
                    exitDots();  //loading scren before exiting application
                    isRunning = false;
                    break;
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
            System.out.println("Your deposit was successfully added!");
        } catch (Exception e) {
            System.out.println("The transaction was not added to ledger");
        }
    }

    public static void makePayment() {
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

            String completeLine = String.format(formattedTime + "|" + userTransaction + "|" + userVendor + "|" + -Math.abs(userAmount) + "\n");
            buffWriter.write(completeLine); // writing the input to the csv file and making the transaction amount always come back as negative
            buffWriter.close();
            System.out.println("Your payment was successfully added to ledger!");
        } catch (Exception e) {
            System.out.println("Could not add transaction to ledger");
        }
    }

    public static ArrayList<Transaction> getLedger() throws IOException {
        ArrayList<Transaction> transactionList = new ArrayList<>(); //creating hte array list to add transactions
        FileReader fileReader = new FileReader("src/main/resources/transactions.csv");
        BufferedReader lineReader = new BufferedReader(fileReader);
        String transLine;

        while ((transLine = lineReader.readLine()) != null) {  //looping through the csv file and stopping once a line is empty
            String[] transArray = transLine.split("\\|");
            transactionList.add(new Transaction(transArray[0], transArray[1], transArray[2], transArray[3], Double.parseDouble(transArray[4]))); //adding each split up part into the object
        }

        return transactionList;

    }

    public static void ledgerMenu() throws IOException, InterruptedException {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("""      
                    \n1.All Transactions
                    2.Deposits Only
                    3.Payments Only
                    4.Reports
                    0.Back to Main Menu
                    """);   //getting input from user on how to display transactions
            System.out.print(CYAN + "Option Here:" + RESET);
            int userLedgerOption = theScanner.nextInt();
            switch (userLedgerOption) {
                case 1:
                    displayLedger();//displays all the transactions
                    Thread.sleep(1500);  //gives user time to look at output before screen changes
                    break;
                case 2:
                    displayDeposits();    //displays the deposits only
                    Thread.sleep(1500);
                    break;
                case 3:
                    displayPayments();    //displays the payments only
                    Thread.sleep(1500);
                    break;
                case 4:
                    reports();
                    Thread.sleep(1500);
                    break;
                case 0:
                    loadingDots(); //loading screen for moving back to main menu
                    return;
                //user can decide to go back to home screen
            }
        }

    }

    public static void displayLedger() throws IOException {
        ArrayList<Transaction> transactionList = getLedger();
        System.out.println("+------------------------------------------------------+");
        System.out.println("| DATE   | TIME    | DESCRIPTION    | VENDOR     |PRICE  |"); //adding header for the user to easiily read the transactions
        for (int i = 0; i < transactionList.size(); i++) {
            Transaction transaction = transactionList.get(i);//get each variable from the object
            if (transaction.getPrice() < 0) {  //checking to see if the transaction was a payment or deposit
                System.out.printf(RED + "%s|%s|%s|%s|%.2f \n" + RESET, transaction.getDate(), transaction.getTime(), transaction.getName(), transaction.getType(), transaction.getPrice());
            } else {
                System.out.printf(GREEN + "%s|%s|%s|%s|%.2f \n" + RESET, transaction.getDate(), transaction.getTime(), transaction.getName(), transaction.getType(), transaction.getPrice());
            }
        }
        System.out.println("+------------------------------------------------------+");

    }

    public static void displayDeposits() throws IOException {
        ArrayList<Transaction> transactionList = getLedger();
        System.out.println("+------------------------------------------------------+");
        System.out.println("| DATE   | TIME    | DESCRIPTION    | VENDOR     |PRICE  |"); //adding header for the user to easiily read the transactions
        for (Transaction trans : transactionList) {
            if (trans.getPrice() > 0) {            //decides if the transaction was a deposit
                System.out.printf(GREEN + "%s|%s|%s|%s|%.2f\n" + RESET, trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
            }
        }
        System.out.println("+------------------------------------------------------+");
    }

    public static void displayPayments() throws IOException {
        ArrayList<Transaction> transactionList = getLedger();
        System.out.println("+------------------------------------------------------+");
        System.out.println("| DATE   | TIME    | DESCRIPTION    | VENDOR     |PRICE  |"); //adding header for the user to easiily read the transactions
        for (Transaction trans : transactionList) {
            if (trans.getPrice() < 0) {           //decides if the transaction is a payment
                System.out.printf(RED + "%s|%s|%s|%s|%.2f \n" + RESET, trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
            }
        }
        System.out.println("+------------------------------------------------------+");
    }

    public static void reports() throws IOException, InterruptedException {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("""  
                    Choose your report option
                    1. Month to Date
                    2. Previous Month
                    3. Year to Date
                    4. Previous Year
                    5. Search by Vendor
                    6. Back 
                    """);     //user can choose the report they want
            System.out.print(CYAN + "Option Here:" + RESET);
            int userInput = theScanner.nextInt();

            switch (userInput) {
                case 1:
                    monthToDate();
                    Thread.sleep(1500);  //gives user time to look at output before screen changes
                    break;
                case 2:
                    previousMonth();
                    Thread.sleep(1500);
                    break;
                case 3:
                    yearToDate();
                    Thread.sleep(1500);
                    break;
                case 4:
                    previousYear();
                    Thread.sleep(1500);
                    break;
                case 5:
                    vendorSearch();
                    Thread.sleep(1500);
                    break;
                case 6:
                    loadingDots();
                    return; //takes user back to ledger menu

            }
        }
    }

    public static void monthToDate() throws IOException {
        ArrayList<Transaction> transList = getLedger();
        LocalDate date = LocalDate.now();

        String monthString = date.toString();
        String month = monthString.substring(5, 7); //grabbing the month out of the entire date

        System.out.println("+------------------------------------------------------+");
        System.out.println("| DATE   | TIME    | DESCRIPTION    | VENDOR     |PRICE  |"); //adding header for the user to easiily read the transactions
        for (Transaction trans : transList) { //seeing if the current date matches with transaction date

            if (month.equalsIgnoreCase(trans.getDate().substring(5, 7))) {

                if (trans.getPrice() < 0) {  //checking to see if the price is negative to determine if the transaction is a payment or depsoit for the color scheme

                    System.out.printf(RED + "%s|%s|%s|%s|%.2f \n" + RESET, trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
                } else {
                    System.out.printf(GREEN + "%s|%s|%s|%s|%.2f \n" + RESET, trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
                }
            }
        }
        System.out.println("+------------------------------------------------------+");
    }

    public static void yearToDate() throws IOException {
        ArrayList<Transaction> transList = getLedger();
        LocalDate date = LocalDate.now();   //finding current date
        DateTimeFormatter fmd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("+------------------------------------------------------+");
        System.out.println("| DATE   | TIME    | DESCRIPTION    | VENDOR     |PRICE  |"); //adding header for the user to easiily read the transactions
        for (Transaction trans : transList) {
            String[] transYear = trans.getDate().split("-");   //splitting the date to get the year
            if (date.getYear() == Integer.parseInt(transYear[0])) {  //seeing if year in each transaction macthes the current year
                if (trans.getPrice() < 0) {  //checking to see if the price is negative to determine if the transaction is a payment or depsoit for the color scheme
                    System.out.printf(RED + "%s|%s|%s|%s|%.2f \n" + RESET, trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
                } else {
                    System.out.printf(GREEN + "%s|%s|%s|%s|%.2f \n" + RESET, trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
                }
            }
        }
        System.out.println("+------------------------------------------------------+");
    }


    public static void previousMonth() throws IOException {
        ArrayList<Transaction> transList = getLedger();
        LocalDate date = LocalDate.now();   //finding current date
        DateTimeFormatter fmd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate previousMonth = date.minusMonths(1); //grabbing the previous month
        String monthString = previousMonth.toString();
        String month = monthString.substring(5, 7); //grabbing the month out if the formatted date
        System.out.println("+------------------------------------------------------+");
        System.out.println("| DATE   | TIME    | DESCRIPTION    | VENDOR     |PRICE  |"); //adding header for the user to easiily read the transactions
        for (Transaction trans : transList) {
            LocalDate year = LocalDate.parse(trans.getDate());
            int transYear = year.getYear();   //getting the year from the transaction
            if (month.equalsIgnoreCase(trans.getDate().substring(5, 7)) && date.getYear() == transYear) {  //seeing if month in each transaction macthes the formatted month
                //and if the year matches so we dont get past years
                if (trans.getPrice() < 0) {  //checking to see if the price is negative to determine if the transaction is a payment or depsoit for the color scheme
                    System.out.printf(RED + "%s|%s|%s|%s|%.2f \n" + RESET, trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
                } else {
                    System.out.printf(GREEN + "%s|%s|%s|%s|%.2f \n" + RESET, trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
                }
            }
        }
        System.out.println("+------------------------------------------------------+");
    }

    public static void previousYear() throws IOException {
        ArrayList<Transaction> transList = getLedger();
        LocalDate date = LocalDate.now();
        LocalDate previousYear = date.minusYears(1); //function to knock year back by 1

        String dateString = previousYear.toString();
        String year = dateString.substring(0, 4); //grab only the year out of the date
        System.out.println("+------------------------------------------------------+");
        System.out.println("| DATE   | TIME    | DESCRIPTION    | VENDOR     |PRICE  |"); //adding header for the user to easiily read the transactions
        for (Transaction trans : transList) {
            if (year.equalsIgnoreCase(trans.getDate().substring(0, 4))) {  //checking for the same years in the transactions
                if (trans.getPrice() < 0) {  //checking to see if the price is negative to determine if the transaction is a payment or depsoit for the color scheme
                    System.out.printf(RED + "%s|%s|%s|%s|%.2f \n" + RESET, trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
                } else {
                    System.out.printf(GREEN + "%s|%s|%s|%s|%.2f \n" + RESET, trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
                }
            }
        }
        System.out.println("+------------------------------------------------------+");
    }

    public static void vendorSearch() throws IOException, InterruptedException {
        ArrayList<Transaction> transList = getLedger();
        theScanner.nextLine();  //eating line bc last input was an int
        boolean isRunning = true;

        System.out.println("What vendor are you looking for? :");
        String userVendor = theScanner.nextLine(); //saving users vendor that they want to search
        System.out.println("+------------------------------------------------------+");
        System.out.println("| DATE   | TIME    | DESCRIPTION    | VENDOR     |PRICE  |"); //adding header for the user to easiily read the transactions
        for (Transaction trans : transList) {

            if (userVendor.equalsIgnoreCase(trans.getType())) {  // if the input equals the vendor from a transaction it will print the entire transaction
                if (trans.getPrice() < 0) {  //checking to see if the price is negative to determine if the transaction is a payment or depsoit for the color scheme
                    System.out.printf(RED + "%s|%s|%s|%s|%.2f \n" + RESET, trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
                } else {
                    System.out.printf(GREEN + "%s|%s|%s|%s|%.2f \n" + RESET, trans.getDate(), trans.getTime(), trans.getName(), trans.getType(), trans.getPrice());
                }

            }
        }
        System.out.println("+------------------------------------------------------+");

    }

    public static void loadingDots() throws InterruptedException {
        System.out.print("Loading");
        for (int i = 0; i < 4; i++) {
            System.out.print(".");  //takes a break between each dot to give animation
            Thread.sleep(400);
        }
    }

    public static void exitDots() throws InterruptedException {
        System.out.print("Exiting Application");
        for (int i = 0; i < 4; i++) {
            System.out.print(".");
            Thread.sleep(400);
        }
    }

}
