package com.example.food_order;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;

public class FoodView extends AppCompatActivity {
    Dish dish;
    DatabaseAccess db;
    Bitmap foodImage;

    //XML
    TextView dishName;
    ImageView dishImage;
    TextView dishQuantity;
    TextView dishPrice;

    //Buttons
    Button increment;
    Button decrement;
    Button addToCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_view);

        getIntents();
        connectXMLViews();

        setButtonListeners();
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

    public void connectXMLViews() {
        dishName = findViewById(R.id.food_name);
        dishName.setText(dish.mFoodName);
        dishImage = findViewById(R.id.food_image);
        dishImage.setImageBitmap(foodImage);
        dishPrice = findViewById(R.id.food_price);
        dishPrice.setText(dish.mPrice);
        dishQuantity = findViewById(R.id.food_quantity);
        dishQuantity.setText(dish.mQuantity);

        increment = findViewById(R.id.food_increment);
        decrement = findViewById(R.id.food_decrement);
        addToCart = findViewById(R.id.add_to_cart);
    }

    public void setButtonListeners() {

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dish.mQuantity -= 1;
            }
        });
        decrement.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dish.mQuantity += 1;
            }
        }));
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.addFoodToCart(dish, dish.mQuantity);
            }
        });
    }
}