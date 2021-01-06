package com.example.food_order.restaurantRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.R;
import com.example.food_order.Restaurant;
import com.example.food_order.categoryRecycler.Category;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    private RestaurantViewHolder.OnNoteListener mOnNoteListener;

    // Properties
    Context context;
    ArrayList<Restaurant> restaurants;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurants, RestaurantViewHolder.OnNoteListener onNoteListener) {
        this.context = context;
        this.restaurants = restaurants;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_restaurant, parent, false);
        RestaurantViewHolder viewHolder = new RestaurantViewHolder(itemView, mOnNoteListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.imageView.setImageBitmap(restaurant.image);
        holder.nameView.setText(restaurant.name);
        holder.priceView.setText(restaurant.priceRange);
        holder.starsView.setText(restaurant.starRating);
        holder.feeView.setText(restaurant.deliveryFee);

    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }
}
