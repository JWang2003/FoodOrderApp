package com.example.food_order.editPlaylistRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.Dish;
import com.example.food_order.R;

import java.util.ArrayList;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    private ArrayList<Dish> mDishList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDecrementClick(int position);
        void onIncrementClick(int position);
        void onAddCartClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class DishViewHolder extends RecyclerView.ViewHolder {
        public ImageView mDishImage;
        public TextView mNameText;
        public TextView mPriceText;
        public Button addCart;
        public Button mDecrementCounter;
        public Button mIncrementCounter;
        public Button mDetailsButton;       //THIS IS AN OUTDATED NAME FOR THE ADD TO CART BUTTON. DO NOT DELETE!
        public TextView mCounterText;
        public ImageButton mDeleteButton;

        public DishViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            addCart = itemView.findViewById(R.id.add_to_cart);
            mDishImage = itemView.findViewById(R.id.dish_image);
            mNameText = itemView.findViewById(R.id.dish_name);
            mPriceText = itemView.findViewById(R.id.dish_price);
            mIncrementCounter = itemView.findViewById(R.id.increment_counter);
            mDecrementCounter = itemView.findViewById(R.id.decrement_counter);
            mDetailsButton = itemView.findViewById(R.id.add_to_cart);
            mCounterText = itemView.findViewById(R.id.counter);
            mDeleteButton = itemView.findViewById(R.id.delete_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            mIncrementCounter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onIncrementClick(position);
                        }
                    }
                }
            });
            mDecrementCounter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDecrementClick(position);
                        }
                    }
                }
            });

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            addCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddCartClick(position);
                        }
                    }
                }
            });
        }
    }

    public DishAdapter(ArrayList<Dish> dishList) {
        mDishList = dishList;
    }

    @Override
    public DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        DishViewHolder evh = new DishViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish currentItem = mDishList.get(position);
        holder.mDishImage.setImageBitmap(currentItem.mFoodImage);
        holder.mNameText.setText(currentItem.mFoodName);
        holder.mPriceText.setText(currentItem.mPrice);
        holder.mCounterText.setText(String.valueOf(currentItem.mQuantity));
    }

    @Override
    public int getItemCount() {
        return mDishList.size();
    }
}