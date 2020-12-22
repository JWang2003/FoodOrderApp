package com.example.food_order;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Restaurant restaurant1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpRestaurants();
        System.out.println(restaurant1.menu);
    }
    void setUpRestaurants(){
        Restaurant restaurant1 = new Restaurant();
//        restaurant1.menu.dishes = new ArrayList<>();
//
//        Dish dish = new Dish("Food", 1200, 12, "Hello");
//        restaurant1.menu.dishes.add(dish);
//
//        dish = new Dish("A", 1, 2, "B");
//        restaurant1.menu.dishes.add(dish);
//
//        dish = new Dish("C", 2, 4, "Dfsf");
//        restaurant1.menu.dishes.add(dish);
    }
}