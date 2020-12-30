package com.example.food_order.categoryRecycler;

import com.example.food_order.Restaurant;

import java.util.ArrayList;

public class Category {
    public String categoryName;
    public ArrayList<Restaurant> restaurants;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public void populateRestaurants() {
        // Make it pass the category name into database, getting all the restaurants
    }
}
