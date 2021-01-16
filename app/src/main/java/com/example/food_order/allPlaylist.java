package com.example.food_order;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class allPlaylist extends AppCompatActivity{

    RecyclerView playlistRecyclerView;
    ImageButton plusButton;
    ArrayList<String> playlists;
    PlaylistAdapter playlistAdapter;
    DatabaseAccess db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_playlist);
        plusButton = findViewById(R.id.plus);
        playlistRecyclerView = findViewById(R.id.playlist_recycle);
        db = DatabaseAccess.getInstance(getApplicationContext());
        playlists = db.getPlaylists();
        setUpGridLayout();

    }

    public void addPlaylistToCart(int position) {
        ArrayList<Dish> playlistDishes = db.getPlaylistDishes(playlists.get(position));
        for (Dish dish : playlistDishes) {
            db.addFoodToCart(dish, dish.mQuantity);
        }
    }

    public void deleteItem(int position) {
        playlists.remove(position);
        playlistAdapter.notifyItemRemoved(position);
    }

    public void setUpGridLayout() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        playlistRecyclerView.setLayoutManager(gridLayoutManager);
        playlistAdapter = new PlaylistAdapter(playlists);
        playlistRecyclerView.setAdapter(playlistAdapter);
        playlistRecyclerView.setHasFixedSize(true);
    }

    public void buildRecyclerView() {
        playlistAdapter.setOnItemClickListener(new PlaylistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                deleteItem(position);
                System.out.println("Delete clicked at " + position);
            }

            @Override
            public void onOrderClick(int position) {
                addPlaylistToCart(position);
                System.out.println("Increment clicked at " + position);
            }
        });
    }

    @Override
    public void onNoteClick(int position) {

    }
}