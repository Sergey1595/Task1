package models;

import java.text.DecimalFormat;

public class Product {
    private double price;
    private double regularPrice;
    private double discount;
    String currency;


    public Product(double price, double regularPrice, double discount, String currency) {
        setPrice(price);
        setRegularPrice(regularPrice);
        setDiscount(discount);
        setCurrency(currency);
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
