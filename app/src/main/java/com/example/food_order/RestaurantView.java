package com.example.food_order;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RestaurantView extends AppCompatActivity {

    ArrayList<Restaurant> restaurants;
    DatabaseAccess db;
    // IMAGE IS A BITMAP, NOT AN INT

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_view);
        db = DatabaseAccess.getInstance(getApplicationContext());
        Intent intent = getIntent();
        String categoryName = intent.getStringExtra("catname");
        restaurants = db.getRestaurants(categoryName);
        System.out.println("Restaurant view:" + restaurants);
    }
}