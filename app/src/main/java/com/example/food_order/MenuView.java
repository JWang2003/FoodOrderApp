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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.food_order.menuRecycler.MenuAdapter;


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

    // RecyclerView
    ArrayList<Dish> dishList;
    RecyclerView mRecyclerView;
    MenuAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_view);

        //Main Display
        connectDatabase();
        connectXMLViews();

        //RecyclerView
        createDishList();
        buildRecyclerView();
    }
    //TODO: Implement decrement
    public void decrement(int position) {

    }

    //TODO: Implement increment
    public void increment() {

    }

    //TODO: Implement addtoCart
    public void addToCart(int position){

    }

    //TODO: Implement toDishDetails
    public void toDishDetails(int position){

    }



    public void createDishList() {
        dishList= new ArrayList<>();
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.menu_recycler_view);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MenuAdapter(dishList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                toDishDetails();
            }

            @Override
            public void onIncrementClick() {
                increment();
            }

            @Override
            public void onDecrementClick() {
                decrement();
            }

            @Override
            public void onAddToCartClick() {
                addToCart();
            }
        });
    }







































    public void connectDatabase() {
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
//        layout = findViewById(R.id.web_container);
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

//        // TODO: Connect button
//        yelpLink = findViewById(R.id.yelp_link);
//        yelpLink.setOnClickListener(v -> getWebFragment());
    }




//    public void getWebFragment(){
//        layout.setVisibility(layout.VISIBLE);
//        fragment = yelpFragment.newInstance(restaurant.yelpUrl);
//        getSupportFragmentManager().beginTransaction().replace(R.id.web_container, fragment).commit();
//    }
}