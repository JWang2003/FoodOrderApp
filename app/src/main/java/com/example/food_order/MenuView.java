package com.example.food_order;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.util.ArrayList;

public class MenuView extends AppCompatActivity {

    DatabaseAccess db;
    ArrayList<Dish> dishes;

    Restaurant restaurant;
    // INSTEAD OF restaurant.image USE restaurantImage
    Bitmap restaurantImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_view);

        // https://stackoverflow.com/questions/11010386/passing-android-bitmap-data-within-activity-using-intent-in-android
        String filename = getIntent().getStringExtra("image");
        try {
            FileInputStream is = this.openFileInput(filename);
            restaurantImage = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db = DatabaseAccess.getInstance(getApplicationContext());
        restaurant = getIntent().getParcelableExtra("restaurant");
        dishes = db.getDishes(restaurant.name);
        System.out.println("Menu view has added: " + dishes);
        System.out.println("Restaurant added: " + restaurant);
    }
}