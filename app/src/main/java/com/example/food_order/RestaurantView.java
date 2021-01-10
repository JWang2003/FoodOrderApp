package com.example.food_order;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.restaurantRecycler.RestaurantAdapter;
import com.example.food_order.restaurantRecycler.RestaurantViewHolder;

import java.util.ArrayList;

public class RestaurantView extends AppCompatActivity implements RestaurantViewHolder.OnNoteListener {

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
        setContentView(R.layout.activity_restaurant_view);
        db = DatabaseAccess.getInstance(getApplicationContext());
        Intent intent = getIntent();
        String categoryName = intent.getStringExtra("catname");
        restaurants = db.getRestaurants(categoryName);
        System.out.println("Restaurant view:" + restaurants);

        connectXMLViews();
        setUpGridLayout();
    }

    public void connectXMLViews() {
        restaurantsRecyclerView = findViewById(R.id.restaurants_recycle);
        restaurantImage = findViewById(R.id.image);
        searchView = findViewById(R.id.search_bar);
        cartButton = findViewById(R.id.checkout);
        resultsText = findViewById(R.id.results_text);
    }

    public void setUpGridLayout() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        restaurantsRecyclerView.setLayoutManager(gridLayoutManager);
        restaurantAdapter = new RestaurantAdapter(this, restaurants, this);
        restaurantsRecyclerView.setAdapter(restaurantAdapter);
        restaurantsRecyclerView.setHasFixedSize(true);

    }

    @Override
    public void onNoteClick(int position) {
        Restaurant currentRestaurant = restaurants.get(position);
        // This gets all the data of the restaurant selected
        String restaurantName = currentRestaurant.name;

        Intent intent = new Intent(this, MenuView.class);
        intent.putExtra("restaurantname", restaurantName);
        startActivity(intent);
    }
}