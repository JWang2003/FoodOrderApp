package com.example.food_order;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class Restaurant {

    Context context;

    // Displayed information
    public String name;
    public Bitmap image;
    public String address;
    public String priceRange;
    public String starRating;
    public String deliveryFee;
    public String yelpUrl;

    // Menu which gets populated when restaurant is selected
    public ArrayList<Dish> menu;

    public Restaurant(String name, Bitmap image, String address, String priceRange, String starRating, String deliveryFee, String yelpUrl) {
        this.name = name;
        this.image = image;
        this.address = address;
        this.priceRange = priceRange;
        this.starRating = starRating;
        this.deliveryFee = deliveryFee;
        this.yelpUrl = yelpUrl;
    }

    public void populateMenu(DatabaseAccess db) {
        menu = db.getDishes(name);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", image=" + image +
                ", address='" + address + '\'' +
                ", priceRange='" + priceRange + '\'' +
                ", starRating='" + starRating + '\'' +
                ", deliveryFee='" + deliveryFee + '\'' +
                ", yelpUrl='" + yelpUrl + '\'' +
                ", menu=" + menu +
                '}';
    }
}
