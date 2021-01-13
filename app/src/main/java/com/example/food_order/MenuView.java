package com.example.food_order;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MenuView extends AppCompatActivity {

    DatabaseAccess db;
    ArrayList<Dish> dishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_view);

        db = DatabaseAccess.getInstance(getApplicationContext());
        Intent intent = getIntent();
        String restaurantName = intent.getStringExtra("restaurantname");
        dishes = db.getDishes(restaurantName);
        System.out.println("Menu view has added: " + dishes);
    }
}