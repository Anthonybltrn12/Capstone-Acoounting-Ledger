package com.pluralsight;

public class Transaction {
    private String date;
    private String time;
    private String name;
    private String type;
    private double price;

    public Transaction(String date, String time, String name, String type, double price) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.type = type;

        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
