package com.example.food_order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.food_order.menuRecycler.MenuAdapter;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MenuView extends AppCompatActivity {

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
        getIntents();
        connectXMLViews();

        //RecyclerView
        createDishList();
        buildRecyclerView();
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
            public void onItemClick(int position) {
                toDishDetails(position);
            }
        });
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

//        // TODO: Connect button
//        yelpLink = findViewById(R.id.yelp_link);
//        yelpLink.setOnClickListener(v -> getWebFragment());
    }




//    public void getWebFragment(){
//        layout.setVisibility(layout.VISIBLE);
//        fragment = yelpFragment.newInstance(restaurant.yelpUrl);
//        getSupportFragmentManager().beginTransaction().replace(R.id.web_container, fragment).commit();
//    }




    //TODO: Implement toDishDetails
    public void toDishDetails(int position){
        Dish currentDish = dishList.get(position);
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