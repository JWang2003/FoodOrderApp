package com.example.food_order.restaurantRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.R;
import com.example.food_order.Restaurant;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    private RestaurantViewHolder.OnNoteListener mOnNoteListener;

    // Properties
    Context context;
    ArrayList<Restaurant> restaurants;
    ArrayList<Restaurant> restaurantsCopy;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurants, RestaurantViewHolder.OnNoteListener onNoteListener) {
        this.context = context;
        this.restaurants = restaurants;
        restaurantsCopy = new ArrayList<>(restaurants);
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
        int Stars = 0;
        String [] starArray = restaurant.starRating.split("");
        for (String s : starArray) {
            if (s.equals("*")) {
                Stars++;
            }
        }
        holder.imageView.setImageBitmap(restaurant.image);
        holder.nameView.setText(restaurant.name);
        holder.priceView.setText(restaurant.priceRange);
        holder.starsView.setRating(Stars);
        System.out.println(Stars);
        holder.feeView.setText(restaurant.deliveryFee + " Delivery");
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public void filter(String text) {
        restaurants.clear();
        if(text.isEmpty()){
            restaurants.addAll(restaurantsCopy);
        } else{
            text = text.toLowerCase();
            for(Restaurant item: restaurantsCopy){
                if(item.name.toLowerCase().contains(text)){
                    restaurants.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
