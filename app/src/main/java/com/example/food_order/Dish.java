package com.example.food_order;

import android.graphics.Bitmap;

public class Dish {
    public int mQuantity;
    public String mFoodName;
    public Bitmap mFoodImage;
    public String mPrice;
    public String mDetails;

    public Dish(String foodName, Bitmap foodImage, String price, String details) {
        mQuantity = 1;
        mFoodName = foodName;
        mFoodImage = foodImage;
        mPrice = price;
        mDetails = details;
    }

    @Override
    public String toString() {
        return "Dish{" +
                ", quantity='" + mQuantity + '\'' +
                "foodName='" + mFoodName + '\'' +
                ", foodImage=" + mFoodImage +
                ", price='" + mPrice + '\'' +
                ", details='" + mDetails + '\'' +
                '}';
    }

    public Dish(int quantity, String foodName, Bitmap foodImage, String price, String details) {
        mQuantity = quantity;
        mFoodName = foodName;
        mFoodImage = foodImage;
        mPrice = price;
        mDetails = details;
    }

}
