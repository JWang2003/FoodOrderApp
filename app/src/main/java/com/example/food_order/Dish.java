package com.example.food_order;

public class Dish {
    private String name;
    private int price;
    private int photo;
    private String description;

    Dish(String name, int price, int photo, String description){
        this.name = name;
        this.price = price;
        this.photo = photo;
        this.description = description;
    }
    public int getPrice(){
        return price;
    }
}
