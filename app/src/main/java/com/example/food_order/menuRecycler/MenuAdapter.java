package com.example.food_order.menuRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.Dish;
import com.example.food_order.R;
import com.example.food_order.Restaurant;
import com.example.food_order.menuRecycler.MenuViewHolder;
import com.example.food_order.restaurantRecycler.RestaurantViewHolder;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    // Properties
    private ArrayList<Dish> mDishList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick();
        void onIncrementClick();
        void onDecrementClick();
        void onAddToCartClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) { mListener = listener; }


    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        public TextView dishName;
        public ImageView dishImage;
        public TextView dishPrice;
        public TextView quantity;
        public Button decrement;
        public Button increment;
        public ImageView cartAdd;

        public MenuViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dish_name);
            dishImage = itemView.findViewById(R.id.dish_image);
            dishPrice = itemView.findViewById(R.id.dish_price);


            cartAdd = itemView.findViewById(R.id.add_to_cart);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onIncrementClick(position);
                        }
                    }
                }
            });

            decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDecrementClick(position);
                        }
                    }
                }
            });


            cartAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddToCartClick(position);
                        }
                    }
                }
            });

        }
    }

    public MenuAdapter(ArrayList<Dish> dishList) {mDishList = dishList; }





    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        MenuViewHolder mvh = new MenuViewHolder(v, mListener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Dish currentItem = mDishList.get(position);
        holder.dishName.setText(currentItem.mFoodName);
        holder.dishImage.setImageBitmap(currentItem.mFoodImage);
        holder.dishPrice.setText(currentItem.mPrice);
        holder.quantity.setText(0);
    }


    @Override
    public int getItemCount() {
        mDishList.size();
    }
}
