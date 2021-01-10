package com.example.food_order;

import android.graphics.Bitmap;

public class Dish {
    public int quantity;
    public String foodName;
    public Bitmap foodImage;
    public String price;
    public String details;

    public Dish(String foodName, Bitmap foodImage, String price, String details) {
        this.foodName = foodName;
        this.foodImage = foodImage;
        this.price = price;
        this.details = details;
    }

    public Dish(int quantity, String foodName, Bitmap foodImage, String price, String details) {
        this.quantity = quantity;
        this.foodName = foodName;
        this.foodImage = foodImage;
        this.price = price;
        this.details = details;
    }

    @Override
    public String toString() {
        return "Dish{" +
                ", quantity='" + quantity + '\'' +
                "foodName='" + foodName + '\'' +
                ", foodImage=" + foodImage +
                ", price='" + price + '\'' +
                ", details='" + details + '\'' +
                '}';
    }


}
