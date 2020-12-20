package com.example.food_order;

public class Restaurant {
    // Displayed information
    String name;
    int image;
    String address;
    String phoneNumber;
    String averageCost;
    String starRating;

    // Properties
    Menu menu = new Menu(); // Singleton? Each restaurant should only have one menu

}
