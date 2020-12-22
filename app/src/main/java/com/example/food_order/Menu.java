package com.example.food_order;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu {
    private HashMap<String, ArrayList<Dish>> menu = null; // "CuisineName" : [ Dish ] such as "Sushi" : "California Roll"

    // This method gets the menu
    public HashMap<String, ArrayList<Dish>> dishesByCuisine() {
        // Lazy instantiation, have to call dishesByCuisine instead of code running on create
        if (menu == null) {
            populateMenu();
        }
        return menu;
    }
    // Only allow this class to use this method
    private void populateMenu() {
        menu = new HashMap<String, ArrayList<Dish>>();

        // Iterate through every possible Cuisine type
        for (Cuisine cuisine: Cuisine.values()) { // This gets every value in enum Cuisine
            ArrayList<Dish> dishesList = new ArrayList<Dish>();

            // Insert 6 dishes for each cuisine, perhaps can modify this to work with database
            for (int i = 0; i < 6; i ++) {
                Dish dish = new Dish("", "3", 1, 2);
                dishesList.add(dish);
            }
            // Add cuisine name with its dishes to menu
            menu.put(cuisine.toString(), dishesList);
        }
        System.out.println("Menu: " + menu);
    }

}
