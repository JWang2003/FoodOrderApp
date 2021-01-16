package com.example.food_order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.food_order.editPlaylistRecycler.DishAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class EditPlaylist extends AppCompatActivity {

    private DatabaseAccess db;
    private ArrayList<Dish> mDishList;
    private RecyclerView mRecyclerView;
    private DishAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mPlaylistName;
    private String currentPlaylistName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_playlist);
        db = DatabaseAccess.getInstance(getApplicationContext());
        Intent intent = getIntent();
        currentPlaylistName = intent.getStringExtra("playlistname");
        mDishList = db.getPlaylistDishes(currentPlaylistName);
        mPlaylistName = findViewById(R.id.playlistName);
        mPlaylistName.setText(currentPlaylistName);

        buildRecyclerView();
    }

    public void incrementCounter(int position) {
        Dish currentDish = mDishList.get(position);
        currentDish.mQuantity += 1;
        mAdapter.notifyDataSetChanged();
    }

    public void decrementCounter(int position) {
        Dish currentDish = mDishList.get(position);
        if (currentDish.mQuantity > 0) {
            currentDish.mQuantity -= 1;
        }
        mAdapter.notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        mDishList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void addToCart(int position) {
        Dish currentDish = mDishList.get(position);
        if (currentDish.mQuantity!=0) {
            db.addFoodToCart(currentDish, currentDish.mQuantity);
            System.out.println(currentDish.mQuantity + "x " + currentDish.mFoodName + " added to cart");
        }
        System.out.println(db.getCartDishes());
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.playlist_recycle);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DishAdapter(mDishList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new DishAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDecrementClick(int position) {
                decrementCounter(position);
                System.out.println("Decrement clicked at " + position);
            }

            @Override
            public void onIncrementClick(int position) {
                incrementCounter(position);
                System.out.println("Increment clicked at " + position);
            }

            @Override
            public void onDetailsClick(int position) {
                addToCart(position);
            }

            @Override
            public void onDeleteClick(int position) {
                deleteItem(position);
                System.out.println("Delete clicked at " + position);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        db.deletePlaylist(currentPlaylistName);
        for (Dish dish : mDishList) {
            db.addToPlaylist(currentPlaylistName, dish);
        }
        System.out.println("Added " + mDishList + " to playlist " + currentPlaylistName);
    }

}
