package com.example.food_order;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;     //DO NOT TOUCH THIS (terrible mistake)

import com.example.food_order.categoryRecycler.Category;
import com.example.food_order.categoryRecycler.CategoryAdapter;
import com.example.food_order.categoryRecycler.CategoryViewHolder;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements CategoryViewHolder.OnNoteListener {

    // Properties
    DatabaseAccess db;
    ArrayList<Category> categories;
    ArrayList<PlaylistObject> playlists;
    CategoryAdapter categoryAdapter;
    PlaylistAdapter playlistAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    // XML Views
    ConstraintLayout constraintLayout;
    RecyclerView categoriesRecyclerView;
    ImageView restaurantImage;
    SearchView searchView;
    ImageButton cartButton;
    Button allPlaylistsButton;
    RecyclerView playlistRecyclerView;

    @Override
    protected void onRestart() {
        super.onRestart();
        setUpSecondRecycler();      //update the recycler when a new playlist is made
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = DatabaseAccess.getInstance(getApplicationContext());
        connectXMLViews();
        populateCategories();
        setUpGridLayout();
        setUpSecondRecycler();
    }
    public void connectXMLViews() {
        constraintLayout = findViewById(R.id.topbar);
        categoriesRecyclerView = findViewById(R.id.categories_recycle);
        restaurantImage = findViewById(R.id.image);
        searchView = findViewById(R.id.search_bar);
        cartButton = findViewById(R.id.checkout);
        allPlaylistsButton = findViewById(R.id.view_all);
        playlistRecyclerView = findViewById(R.id.playlist_recycler);
        SlidingUpPanelLayout slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

        // SET UP BUTTON CLICKS
        slidingUpPanelLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
        allPlaylistsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, allPlaylist.class);
                startActivity(intent);
            }
        });
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!db.getCartDishes().isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, CheckoutView.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "You have nothing in your cart!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println(query);
                Intent intent = new Intent(MainActivity.this, RestaurantView.class);
                intent.putExtra("search", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus();
            }
        });
    }
    private void populateCategories() {
        // The name of the category matches those in the database
        categories = new ArrayList<Category>();
        categories.add(new Category("American", R.drawable.americanfood));
        categories.add(new Category("European", R.drawable.european));
        categories.add(new Category("Oriental", R.drawable.oriental));
        categories.add(new Category("South and Southeast Asian", R.drawable.indian));
        categories.add(new Category("Bubble Tea", R.drawable.tea));
        categories.add(new Category("Other", R.drawable.logo));
    }
    public void setUpGridLayout() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        categoriesRecyclerView.setLayoutManager(gridLayoutManager);
        categoryAdapter = new CategoryAdapter(this, categories, this);
        categoriesRecyclerView.setAdapter(categoryAdapter);
        categoriesRecyclerView.setHasFixedSize(true);
    }

    public void setUpSecondRecycler() {
        playlists = db.getPlaylists();
        if (playlists.isEmpty()) {
            PlaylistObject playlistObject = new PlaylistObject("", BitmapFactory.decodeResource(this.getResources(), R.drawable.logo));
            playlists.add(playlistObject);
        }
        // Set the size of each playlist
        for (PlaylistObject playlist: playlists) {
            playlist.size = db.getPlaylistDishes(playlist.playlistName).size();
        }
        mLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        playlistRecyclerView.setLayoutManager(mLayoutManager);
        playlistAdapter = new PlaylistAdapter(playlists);
        playlistRecyclerView.setAdapter(playlistAdapter);
        playlistAdapter.setOnItemClickListener(new PlaylistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("Playlist clicked at " + position);
                Intent intent = new Intent(MainActivity.this, EditPlaylist.class);
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

    public void addPlaylistToCart(int position) {
        ArrayList<Dish> playlistDishes = db.getPlaylistDishes(playlists.get(position).playlistName);
        for (Dish dish : playlistDishes) {
            if (dish.mQuantity > 0) {
                db.addFoodToCart(dish, dish.mQuantity);
                Toast.makeText(MainActivity.this, ("Added playlist " + playlists.get(position).playlistName + " to Cart"), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void deleteItem(int position) {
        db.deletePlaylist(playlists.get(position).playlistName);
        if (playlists.size() > 1) {
            playlists.remove(position);
            playlistAdapter.notifyItemRemoved(position);
        } else {
            PlaylistObject playlistObject = new PlaylistObject("", BitmapFactory.decodeResource(this.getResources(), R.drawable.logo));
            playlists.set(position, playlistObject);
            playlistAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onNoteClick(int position) {
        Category currentCategory = categories.get(position);
        // This gets all the restaurants in the category selected
        Intent intent = new Intent(this, RestaurantView.class);
        intent.putExtra("catname", currentCategory.categoryName);
        startActivity(intent);
    }
}