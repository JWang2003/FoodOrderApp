package com.example.food_order;

import android.graphics.Bitmap;

public class Dish {
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

    @Override
    public String toString() {
        return "Dish{" +
                "foodName='" + foodName + '\'' +
                ", foodImage=" + foodImage +
                ", price='" + price + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
