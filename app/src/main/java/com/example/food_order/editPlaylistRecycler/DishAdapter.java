package com.example.food_order.editPlaylistRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.Dish;
import com.example.food_order.R;

import java.util.ArrayList;

public class DishAdapter extends RecyclerView.Adapter<DishViewHolder> {

    private DishViewHolder.OnNoteListener mOnNoteListener;

    // Properties
    Context context;
    ArrayList<Dish> cartItems;

    public DishAdapter(Context context, ArrayList<Dish> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_dish, parent, false);
        DishViewHolder viewHolder = new DishViewHolder(itemView, mOnNoteListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish cartItem = cartItems.get(position);
        holder.imageView.setImageBitmap(cartItem.foodImage);
        holder.nameView.setText(cartItem.foodName);
        holder.priceView.setText(cartItem.price);

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }



}
