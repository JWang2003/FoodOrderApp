package com.example.food_order.menuRecycler;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.Dish;
import com.example.food_order.R;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    // Properties
    private ArrayList<Dish> mDishList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        public TextView dishName;
        public ImageView dishImage;
        public TextView dishPrice;
        public TextView quantity;


        public MenuViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            dishName = itemView.findViewById(R.id.menu_dish_name);
            dishImage = itemView.findViewById(R.id.menu_dish_image);
            dishPrice = itemView.findViewById(R.id.menu_dish_price);


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
        }
    }

    public MenuAdapter(ArrayList<Dish> dishList) {
        mDishList = dishList;
    }


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
        holder.quantity.setText(currentItem.mQuantity);
    }


    @Override
    public int getItemCount() {
        return mDishList.size();
    }
}


