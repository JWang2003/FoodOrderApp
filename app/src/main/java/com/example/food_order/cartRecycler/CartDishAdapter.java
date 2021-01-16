package com.example.food_order.cartRecycler;

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

public class CartDishAdapter extends RecyclerView.Adapter<CartDishAdapter.CartDishViewHolder> {
    private ArrayList<Dish> mCartList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDecrementClick(int position);
        void onIncrementClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class CartDishViewHolder extends RecyclerView.ViewHolder {
        public ImageView mDishImage;
        public TextView mNameText;
        public TextView mPriceText;
        public Button mDecrementCounter;
        public Button mIncrementCounter;
        public TextView mCounterText;
        public ImageButton mDeleteButton;

        public CartDishViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mDishImage = itemView.findViewById(R.id.food_image);
            mNameText = itemView.findViewById(R.id.dish_name);
            mPriceText = itemView.findViewById(R.id.food_price);
            mIncrementCounter = itemView.findViewById(R.id.increment_counter);
            mDecrementCounter = itemView.findViewById(R.id.decrement_counter);
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
        }
    }

    public CartDishAdapter(ArrayList<Dish> dishList) {
        mCartList = dishList;
    }

    @Override
    public CartDishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cartdish, parent, false);
        CartDishViewHolder evh = new CartDishViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull CartDishViewHolder holder, int position) {
        Dish currentItem = mCartList.get(position);
        holder.mDishImage.setImageBitmap(currentItem.mFoodImage);
        holder.mNameText.setText(currentItem.mFoodName);
        holder.mPriceText.setText(currentItem.mPrice);
        holder.mCounterText.setText(String.valueOf(currentItem.mQuantity));
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }
}