package com.example.food_order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;


import com.example.food_order.categoryRecycler.Category;

import java.util.ArrayList;

public class allPlaylist extends AppCompatActivity implements PlaylistViewHolder.OnNoteListener{

    RecyclerView playlistRecyclerView;
    ImageButton plusButton;
    ArrayList<Playlist> playlists;
    PlaylistAdapter playlistAdapter;
    DatabaseAccess db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_playlist);
        plusButton = findViewById(R.id.plus);
        playlistRecyclerView = findViewById(R.id.playlist_recycle);
        playlists = new ArrayList<Playlist>();
        playlists.add(new Playlist("American", R.drawable.americanfood));
        db = DatabaseAccess.getInstance(getApplicationContext());
        setUpGridLayout();
    }

    public void setUpGridLayout() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        playlistRecyclerView.setLayoutManager(gridLayoutManager);
        playlistAdapter = new PlaylistAdapter(this, playlists, this);
        playlistRecyclerView.setAdapter(playlistAdapter);
        playlistRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onNoteClick(int position) {

    }
}