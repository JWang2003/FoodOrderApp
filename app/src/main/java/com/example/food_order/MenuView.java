package com.example.food_order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.menuRecycler.MenuAdapter;
import com.example.food_order.menuRecycler.MenuViewHolder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MenuView extends AppCompatActivity implements MenuViewHolder.OnNoteListener {

    DatabaseAccess db;
    ArrayList<Dish> dishes;
    Restaurant restaurant;
    yelpFragment fragment;
    Bitmap restaurantImage;
    FrameLayout layout;


    // XML
    ImageView restImage;
    TextView restName;
    TextView restAddress;
    TextView restPrice;
    RatingBar restStars;
    TextView restDeliveryFee;
    ImageButton yelpLink;
    ImageButton toCart;

    // RecyclerView
    RecyclerView mRecyclerView;
    MenuAdapter mAdapter;

    //Nested ScrollView
    NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_view);

        //Main Display
        initViews();
        getIntents();
        setUpButtons();

        refreshAdapters();

    }

    public void getIntents() {
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

    public void setUpButtons() {
        toCart = findViewById(R.id.cartButton);
        toCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!db.getCartDishes().isEmpty()) {
                    openCart();
                } else {
                    Toast.makeText(MenuView.this, "You have nothing in your cart!", Toast.LENGTH_SHORT).show();
                }
        }
        });
    }

    private void openCart() {
        Intent openCart = new Intent(this, CheckoutView.class);
        startActivity(openCart);
    }

    public void initViews() {
        nestedScrollView = findViewById(R.id.nestedScrollView);
        mRecyclerView = findViewById(R.id.menu_recycler_view);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(LayoutManager);

    }

    public void refreshAdapters() {
        mAdapter = new MenuAdapter(this, dishes, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    public void connectXMLViews() {
        nestedScrollView.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLicked");
                if(fragment != null) {
                    System.out.println("CLicked");
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
        });
        int Stars = 0;
        String [] starArray = restaurant.starRating.split("");
        for (String s : starArray) {
            if (s.equals("*")) {
                Stars++;
            }
        }
        restImage = findViewById(R.id.restaurant_background);
        restImage.setImageBitmap(restaurantImage);
        restName = findViewById(R.id.name);
        restName.setText(restaurant.name);
        restAddress = findViewById(R.id.address);
        restAddress.setText(restaurant.address);
        restPrice = findViewById(R.id.priceSymbol);
        restPrice.setText(restaurant.priceRange);
        restStars = findViewById(R.id.stars);
        restStars.setRating(Stars);
        restDeliveryFee = findViewById(R.id.delivery);
        restDeliveryFee.setText(restaurant.deliveryFee);
        layout = findViewById(R.id.web_container);
        yelpLink = findViewById(R.id.yelp_link);
        yelpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWebFragment();
            }
        });
    }

    public void getWebFragment(){
        Toast.makeText(this, "Loading Yelp Page for " + restaurant.name, Toast.LENGTH_SHORT).show();
        layout.setVisibility(layout.VISIBLE);
        fragment = yelpFragment.newInstance(restaurant.yelpUrl);
        getSupportFragmentManager().beginTransaction().replace(R.id.web_container, fragment).commit();
    }

    @Override
    public void onNoteClick(int position) {
        Dish currentDish = dishes.get(position);
        // This gets all the data of the dish selected
        Bitmap bmp = currentDish.mFoodImage;
        try {
            //Write file
            String filename = "bitmap.png";
            FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            //Cleanup
            stream.close();

            //Pop intent
            Intent intent = new Intent(this, FoodView.class);
            // All this extra code is because restaurant.image is too big, so pass it separately
            intent.putExtra("dish", currentDish);
            intent.putExtra("image", filename);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}