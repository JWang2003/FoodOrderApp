package com.example.food_order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.restaurantRecycler.RestaurantAdapter;
import com.example.food_order.restaurantRecycler.RestaurantViewHolder;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class RestaurantView extends AppCompatActivity implements RestaurantViewHolder.OnNoteListener {

    // Properties
    DatabaseAccess db;
    Bitmap bmp;
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
        cartButton = findViewById(R.id.checkout);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!db.getCartDishes().isEmpty()) {
                    Intent intent = new Intent(RestaurantView.this, CheckoutView.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RestaurantView.this, "You have nothing in your cart!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        connectXMLViews();
        setUpGridLayout();
    }

    public void connectXMLViews() {
        restaurantsRecyclerView = findViewById(R.id.restaurants_recycle);
        restaurantImage = findViewById(R.id.image);
        searchView = findViewById(R.id.search_bar);
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
//        String restaurantName = currentRestaurant.name;
//        Bitmap bmp = currentRestaurant.image;
        bmp = currentRestaurant.image;
        try { // https://stackoverflow.com/questions/11010386/passing-android-bitmap-data-within-activity-using-intent-in-android
            //Write file
            String filename = "bitmap.png";
            FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            //Cleanup
            stream.close();
//            if (bmp != null && !bmp.isRecycled()) {
//                bmp.recycle();
//                bmp = null;
//            }
//            bmp.recycle();

            //Pop intent
            Intent intent = new Intent(this, MenuView.class);
            // All this extra code is because restaurant.image is too big, so pass it separately
            intent.putExtra("restaurant", currentRestaurant);
            intent.putExtra("image", filename);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}