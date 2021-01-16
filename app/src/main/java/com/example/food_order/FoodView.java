package com.example.food_order;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;

public class FoodView extends AppCompatActivity {
    Dish dish;
    DatabaseAccess db;
    Bitmap foodImage;

    //XML
    





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_view);

        getIntents();
        connectXMLViews();
    }

    public void getIntents() {
        String filename = getIntent().getStringExtra("image");
        try {
            FileInputStream is = this.openFileInput(filename);
            foodImage = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db = DatabaseAccess.getInstance(getApplicationContext());
        dish = getIntent().getParcelableExtra("dish");


        System.out.println("Dish added: " + dish);
    }

    public void connectXMLViews()  {

    }
}