package com.example.food_order.restaurantRecycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.R;
public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // Properties
    View itemView;
    ImageView imageView;
    TextView nameView;
    TextView priceView;
    RatingBar starsView;
    TextView feeView;
    OnNoteListener onNoteListener;

    public RestaurantViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
        super(itemView);
        this.itemView = itemView;
        imageView = itemView.findViewById(R.id.restaurant_image);
        nameView = itemView.findViewById(R.id.restaurant_name);
        priceView = itemView.findViewById(R.id.restaurant_price);
        starsView = itemView.findViewById(R.id.ratingBar);
        feeView = itemView.findViewById(R.id.restaurant_deliveryFee);

        // Make item clickable
        this.onNoteListener = onNoteListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onNoteListener.onNoteClick(getAdapterPosition());
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
