package com.example.food_order.menuRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.Dish;
import com.example.food_order.R;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    private MenuViewHolder.OnNoteListener mOnNoteListener;
    Context context;
    ArrayList<Dish> dishes;

    public MenuAdapter (Context context, ArrayList<Dish> mDishes, MenuViewHolder.OnNoteListener onNoteListener) {
        this.context = context;
        this.dishes = mDishes;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_menu_dish, parent, false);
        MenuViewHolder viewHolder = new MenuViewHolder(itemView, mOnNoteListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Dish dish = dishes.get(position);
        holder.foodImage.setImageBitmap(dish.mFoodImage);
        holder.foodName.setText(dish.mFoodName);
        holder.foodDescription.setText(dish.mDetails);
        holder.foodPrice.setText(dish.mPrice);

    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }
}


