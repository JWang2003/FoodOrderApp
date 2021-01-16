package com.example.food_order;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class allPlaylist extends AppCompatActivity{

    RecyclerView playlistRecyclerView;
    ImageButton plusButton;
    ArrayList<PlaylistObject> playlists;
    PlaylistAdapter playlistAdapter;
    DatabaseAccess db;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_playlist);
        playlistRecyclerView = findViewById(R.id.playlist_recycle);
        db = DatabaseAccess.getInstance(getApplicationContext());
        ArrayList<Dish> dishes = new ArrayList<>();
        dishes = db.getDishes("Jenjudan");

        for (Dish dish : dishes) {
            db.addToPlaylist("Monkey", dish);
        }
        playlists = db.getPlaylists();
        System.out.println(playlists);
        if (playlists.isEmpty()) {
            PlaylistObject playlistObject = new PlaylistObject("Favourites", BitmapFactory.decodeResource(this.getResources(), R.drawable.barry_20b__20benson_large), 0);
            playlists.add(playlistObject);
        }

        buildRecyclerView();

    }

    public void addPlaylistToCart(int position) {
        ArrayList<Dish> playlistDishes = db.getPlaylistDishes(playlists.get(position).playlistName);
        for (Dish dish : playlistDishes) {
            if (dish.mQuantity > 0) {
                db.addFoodToCart(dish, dish.mQuantity);
            }
        }
    }

    public void deleteItem(int position) {
        if (playlists.size() > 1) {
            playlists.remove(position);
            db.deletePlaylist(playlists.get(position).playlistName);
            playlistAdapter.notifyItemRemoved(position);
        }
    }

    public void buildRecyclerView() {
        mLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        playlistRecyclerView.setLayoutManager(mLayoutManager);
        playlistAdapter = new PlaylistAdapter(playlists);
        playlistRecyclerView.setAdapter(playlistAdapter);
        playlistAdapter.setOnItemClickListener(new PlaylistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("Playlist clicked at " + position);
                Intent intent = new Intent(allPlaylist.this, EditPlaylist.class);
                intent.putExtra("playlistname", playlists.get(position).playlistName);
                startActivity(intent);

            }

            @Override
            public void onDeleteClick(int position) {
                deleteItem(position);
                System.out.println("Delete clicked at " + position);
            }

            @Override
            public void onOrderClick(int position) {
                addPlaylistToCart(position);
                System.out.println("Order clicked at " + position);
            }
        });
    }

}