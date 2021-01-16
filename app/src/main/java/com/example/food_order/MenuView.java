package com.example.food_order;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.FileInputStream;
import java.util.ArrayList;

public class MenuView extends AppCompatActivity {

    DatabaseAccess db;
    ArrayList<Dish> dishes;

    Restaurant restaurant;
    yelpFragment fragment;
    Bitmap restaurantImage = null;
    FrameLayout layout;


    // XML
    ImageView restImage;
    TextView restName;
    TextView restAddress;
    TextView restPrice;
    TextView restStars;
    TextView restDeliveryFee;
    ImageButton yelpLink;

    ConstraintLayout constraintLayout;


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


        connectXMLViews();
    }

    public void connectXMLViews() {
        constraintLayout = findViewById(R.id.page);
        // If they click the constraint layout, close the yelp page
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragment != null)
                    layout.setVisibility(layout.INVISIBLE);
//                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.web_container, null).commit();
            }
        });
        layout = findViewById(R.id.web_container);
        restImage = findViewById(R.id.restaurant_background);
        restImage.setImageBitmap(restaurantImage);
        restName = findViewById(R.id.name);
        restName.setText(restaurant.name);
        restAddress = findViewById(R.id.address);
        restAddress.setText(restaurant.address);
        restPrice = findViewById(R.id.priceSymbol);
        restPrice.setText(restaurant.priceRange);
        // TODO: Make stars actually be stars
        restStars = findViewById(R.id.stars);
        restStars.setText(restaurant.starRating);
        restDeliveryFee = findViewById(R.id.delivery);
        restDeliveryFee.setText(restaurant.deliveryFee);
        // TODO: Connect button
        yelpLink = findViewById(R.id.yelp_link);
        yelpLink.setOnClickListener(v -> getWebFragment());

    }

    public void getWebFragment(){
        layout.setVisibility(layout.VISIBLE);
        fragment = yelpFragment.newInstance(restaurant.yelpUrl);
        getSupportFragmentManager().beginTransaction().replace(R.id.web_container, fragment).commit();
    }
}