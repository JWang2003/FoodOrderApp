package com.example.food_order;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Dish dish;
    DatabaseAccess db;
    Bitmap foodImage;
    ArrayList<String> mPlaylistNameList;
    PlaylistObject createPlaylist;

    //XML
    TextView dishName;
    ImageView dishImage;
    TextView dishQuantity;
    TextView dishPrice;

    //Buttons
    Button increment;
    Button decrement;
    Button addToCart;
    Spinner mSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_view);
        getIntents();
        connectXMLViews();
        setButtonListeners();
        setUpSpinner();
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
        dish.mQuantity = 1;
        dish.mFoodImage = foodImage;
        db = DatabaseAccess.getInstance(getApplicationContext());
        System.out.println("The dish in foodview is: " + dish);
    }

    public void connectXMLViews() {
        dishName = findViewById(R.id.food_name);
        dishName.setText(dish.mFoodName);
        dishImage = findViewById(R.id.food_image);
        dishImage.setImageBitmap(foodImage);
        dishPrice = findViewById(R.id.food_price);
        dishPrice.setText(dish.mPrice);
        dishQuantity = findViewById(R.id.food_quantity);
        dishQuantity.setText(String.valueOf(dish.mQuantity));
        increment = findViewById(R.id.food_increment);
        decrement = findViewById(R.id.food_decrement);
        addToCart = findViewById(R.id.to_cart);
    }

    public void setButtonListeners() {

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dish.mQuantity += 1;
                dishQuantity.setText(String.valueOf(dish.mQuantity));
            }
        });
        decrement.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dish.mQuantity > 1) {
                    dish.mQuantity -= 1;
                    dishQuantity.setText(String.valueOf(dish.mQuantity));
                }
            }
        }));
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.addFoodToCart(dish, dish.mQuantity);
            }
        });
    }

    public void setUpSpinner() {
        mPlaylistNameList = new ArrayList<>();
        for (PlaylistObject pl : db.getPlaylists()) {
            mPlaylistNameList.add(pl.playlistName);
        }
        //createPlaylist = new PlaylistObject("Create new playlist", BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_baseline_add_24), 0);
        mPlaylistNameList.add(0, "Create new playlist...");    //add the first option
        mSpinner = findViewById(R.id.playlist_spinner);
        mSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mPlaylistNameList);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAdapter.notifyDataSetChanged();
        mSpinner.setAdapter(mAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position==0) {
            //TODO: take a name input
        } else {
            db.addToPlaylist(mPlaylistNameList.get(position), dish);
            Toast.makeText(this, ("Added " + dish.mQuantity + "x " + dish.mFoodName + " to playlist " + mPlaylistNameList.get(position)), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}