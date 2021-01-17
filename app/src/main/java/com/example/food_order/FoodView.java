package com.example.food_order;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.util.ArrayList;

public class FoodView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Dish dish;
    DatabaseAccess db;
    Bitmap foodImage;
    ArrayList<String> mPlaylistNameList;
    PlaylistObject createPlaylist;
    String input;

    //XML
    TextView dishName;
    ImageView dishImage;
    TextView dishQuantity;
    TextView dishPrice;
    TextView dishDescription;
    ImageButton favouritesAdd;
    ImageButton cartButton;

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
        favouritesAdd = findViewById(R.id.imageButton);
        dishDescription = findViewById(R.id.details);
        dishDescription.setText(dish.mDetails);
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
        cartButton = findViewById(R.id.cartButton);
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
                Toast.makeText(FoodView.this, ("Added " + dish.mQuantity + "x " + dish.mFoodName + " to Cart"), Toast.LENGTH_LONG).show();
            }
        });
        favouritesAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpinner.performClick();
            }
        });
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!db.getCartDishes().isEmpty()) {
                    Intent openCart = new Intent(FoodView.this, CheckoutView.class);
                    startActivity(openCart);
                } else {
                    Toast.makeText(FoodView.this, "You have nothing in your cart!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void setUpSpinner() {
        mPlaylistNameList = new ArrayList<>();
        for (PlaylistObject pl : db.getPlaylists()) {
            mPlaylistNameList.add(pl.playlistName);
        }                                                   //copy db to list of names
        mPlaylistNameList.add("+ Create new playlist...");    //add create selection
        mPlaylistNameList.add(0, "− Cancel...");    //since null selection isn't possible
        mSpinner = findViewById(R.id.playlist_spinner);
        mSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mPlaylistNameList);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAdapter.notifyDataSetChanged();
        mSpinner.setAdapter(mAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            // do nothing
        } else if (position == mPlaylistNameList.size() - 1) {
            showPopUp();
        } else {
            db.addToPlaylist(mPlaylistNameList.get(position), dish);
            Toast.makeText(this, ("Added " + dish.mQuantity + "x " + dish.mFoodName + " to playlist " + mPlaylistNameList.get(position)), Toast.LENGTH_LONG).show();
            mSpinner.setSelection(0);
        }
    }

    public void showPopUp() {
        input = "";
        final EditText taskEditText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create new playlist");
        builder.setMessage("Insert playlist name:");
        builder.setView(taskEditText);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                input += taskEditText.getText().toString();
                System.out.println(input);
                input = input.replaceAll("[^A-Za-z]+", "");
                if (!input.equals("")) {
                    db.addToPlaylist(input, dish);
                    setUpSpinner();
                    Toast.makeText(FoodView.this, ("Added " + dish.mQuantity + "x " + dish.mFoodName + " to playlist " + input), Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        System.out.println("CLOSED POP UP");
        mSpinner.setSelection(0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}