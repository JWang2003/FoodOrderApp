package com.example.nfood_order.menuRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.R;
import com.example.food_order.Restaurant;
import com.example.food_order.menuRecycler.MenuViewHolder;
import com.example.food_order.restaurantRecycler.RestaurantViewHolder;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {
    // Properties
    private ArrayList<Restaurant> restaurants;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onAddClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) { mListener = listener; }


    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView dishNameView;
        public TextView descriptionView;
        public TextView priceView;
        public ImageView starsView;
        public ImageView cartAdd;

        public MenyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            //dishNameView;
            //descriptionView;
            //priceView;
            //starsView;
            //cartAdd;

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
            cartAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddClick(position);
                        }

                }
            });






        }



    }





    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate;
        MenuViewHolder viewHolder = new
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
