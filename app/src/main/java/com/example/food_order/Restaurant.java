package com.example.food_order;

public class Restaurant {
    // Displayed information
    public String name;
    public String address;
    public String phoneNumber;
    public String averageCost;
    public String starRating;
    public int image;

    // Properties
    Menu menu = new Menu(); // Singleton? Each restaurant should only have one menu
    public Restaurant() {
        setupProperties();
    }

    private void setupProperties() {
        // Roland uses this to randomly generate properties for Restaurant using lorem, we need a way to not use random values
    }
}
