package com.example.food_order;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.food_order.categoryRecycler.Category;
import com.example.food_order.categoryRecycler.CategoryAdapter;

import java.util.ArrayList;

public class RestaurantView extends AppCompatActivity {

    // Properties
    DatabaseAccess db;
    ArrayList<Restaurant> restaurants;
    RestaurantAdapter restaurantAdapter;

    // XML Views
    RecyclerView restaurantsRecyclerView;
    ImageView restaurantImage;
    SearchView searchView;
    ImageButton cartButton;
    TextView resultsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = DatabaseAccess.getInstance(getApplicationContext());
        connectXMLViews();
        populateRestaurants();
        setUpGridLayout();
    }

    public void connectXMLViews() {
        restaurantsRecyclerView = findViewById(R.id.restaurants_recycle);
        restaurantImage = findViewById(R.id.image);
        searchView = findViewById(R.id.search_bar);
        cartButton = findViewById(R.id.checkout);
        resultsText = findViewById(R.id.results_text);

        setContentView(R.layout.activity_restaurant_view);
        db = DatabaseAccess.getInstance(getApplicationContext());
        Intent intent = getIntent();
        String categoryName = intent.getStringExtra("catname");
        restaurants = db.getRestaurants(categoryName);
        System.out.println("Restaurant view:" + restaurants);
    }

}