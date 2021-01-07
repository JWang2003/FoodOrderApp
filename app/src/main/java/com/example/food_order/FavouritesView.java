package com.example.food_order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class FavouritesView extends AppCompatActivity {


    DatabaseAccess db;
    ArrayList<Dish> dishes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_view);

        db = DatabaseAccess.getInstance(getApplicationContext());
        Intent intent = getIntent();
        String restaurantName = intent.getStringExtra("restaurantname");
        dishes = db.getDishes(restaurantName);
        System.out.println("Restaurant view:" + dishes);
    }
}