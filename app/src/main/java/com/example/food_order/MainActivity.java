package com.example.food_order;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.categoryRecycler.Category;
import com.example.food_order.categoryRecycler.CategoryAdapter;
import com.example.food_order.categoryRecycler.CategoryViewHolder;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements CategoryViewHolder.OnNoteListener {

    // Properties
    DatabaseAccess db;
    ArrayList<Category> categories;
    CategoryAdapter categoryAdapter;

    // XML Views
    RecyclerView categoriesRecyclerView;
    ImageView restaurantImage;
    SearchView searchView;
    ImageButton cartButton;
    Button allPlaylistsButton;
    RecyclerView playlistRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = DatabaseAccess.getInstance(getApplicationContext());
        connectXMLViews();
        populateCategories();
        setUpGridLayout();
    }

    public void connectXMLViews() {
        categoriesRecyclerView = findViewById(R.id.categories_recycle);
        restaurantImage = findViewById(R.id.image);
        searchView = findViewById(R.id.search_bar);
        cartButton = findViewById(R.id.checkout);
        allPlaylistsButton = findViewById(R.id.view_all);
        playlistRecyclerView = findViewById(R.id.playlist_recycler);
    }

    private void populateCategories() {
        // The name of the category matches those in the database
        categories = new ArrayList<Category>();
        categories.add(new Category("American"));
        categories.add(new Category("European"));
        categories.add(new Category("Oriental"));
        categories.add(new Category("South and Southeast Asian"));
        categories.add(new Category("Bubble Tea"));
    }

    public void setUpGridLayout() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        categoriesRecyclerView.setLayoutManager(gridLayoutManager);
        categoryAdapter = new CategoryAdapter(this, categories, this);
        categoriesRecyclerView.setAdapter(categoryAdapter);
        categoriesRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onNoteClick(int position) {
        Category currentCategory = categories.get(position);
        // This gets all the restaurants in the category selected
        //TODO: comment for debug
        //Intent intent = new Intent(this, RestaurantView.class);
        Intent intent = new Intent(this, EditPlaylist.class);
        intent.putExtra("catname", currentCategory.categoryName);
        startActivity(intent);
    }
}