package com.example.food_order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.food_order.editPlaylistRecycler.DishAdapter;
import com.example.food_order.editPlaylistRecycler.DishViewHolder;
import com.example.food_order.restaurantRecycler.RestaurantAdapter;

import java.util.ArrayList;

public class EditPlaylist extends AppCompatActivity {

    // Properties
    DatabaseAccess db;
    ArrayList<Dish> playlistItems;
    DishAdapter playlistItemsAdapter;

    // XML Views
    RecyclerView playlistItemRecyclerView;
    TextView playlistName;
    Button addToPlaylistButton;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_playlist);
        db = DatabaseAccess.getInstance(getApplicationContext());
        Intent intent = getIntent();
        //TODO: hardcoded restaurant for debugging
        playlistItems = db.getDishes("TokyoJoeSushi");

        connectXMLViews();
        setUpGridLayout();
        playlistName.setText("todo set name from intent");
    }

    public void connectXMLViews() {
        playlistItemRecyclerView = findViewById(R.id.playlist_recycle);
        playlistName = findViewById(R.id.playlistName);
        addToPlaylistButton = findViewById(R.id.plusButton);
    }

    public void setUpGridLayout() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        playlistItemRecyclerView.setLayoutManager(gridLayoutManager);
        playlistItemsAdapter = new DishAdapter(this, playlistItems);
        playlistItemRecyclerView.setAdapter(playlistItemsAdapter);

    }
}