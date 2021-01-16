package com.example.food_order;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.cartRecycler.CartDishAdapter;

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
        db.deletePlaylist("Miso Soup");
        playlists = db.getPlaylists();
        System.out.println(playlists);
        if (playlists.isEmpty()) {
            PlaylistObject playlistObject = new PlaylistObject("My Playlist", BitmapFactory.decodeResource(this.getResources(), R.drawable.barry_20b__20benson_large), this);
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
        if (playlists.size() > 0) {
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
                Intent intent = new Intent(allPlaylist.this, EditPlaylist.class);
                intent.putExtra("playlistname", db.getPlaylists().get(position).playlistName);
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