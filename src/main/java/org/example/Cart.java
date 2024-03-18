package org.example;

import lombok.Data;

@Data
public class Cart {
    private int id;
    private String name;
    private String category;
    private double price;
    private int discount;


    public Cart(int id, String name, String category, double price, int discount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.discount = discount;
    }

}



