package com.pluralsight;

public class Transaction {
    private String date;
    private String time;
    private String type;
    private String name;
    private double price;

    public Transaction(String date, String time, String type, String name, double price) {
        this.date = date;
        this.time = time;
        this.type = type;
        this.name = name;
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
