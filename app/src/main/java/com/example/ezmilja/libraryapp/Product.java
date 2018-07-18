package com.example.ezmilja.libraryapp;

public class Product {
    private int id;
    private String name;
    private double price;

    public Product() {
        super();
    }

    public Product(int id, String name, double price) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return this.id + ". " + this.name + " [$" + this.price + "]";
    }
}
