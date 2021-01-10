package com.example.food_order.editPlaylistRecycler;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.R;

public class DishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // Properties
    View itemView;
    ImageView imageView;
    TextView nameView;
    TextView priceView;
    TextView counterView;
    Button incrementButton;
    Button decrementButton;
    Button detailsButton;
    OnNoteListener onNoteListener;

    public DishViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
        super(itemView);
        this.itemView = itemView;
        imageView = itemView.findViewById(R.id.food_image);
        nameView = itemView.findViewById(R.id.dish_name);
        priceView = itemView.findViewById(R.id.food_price);
        counterView = itemView.findViewById(R.id.counter);
        incrementButton = itemView.findViewById(R.id.increment_counter);
        decrementButton = itemView.findViewById(R.id.decrement_counter);
        detailsButton = itemView.findViewById(R.id.detailsbutton);

        // Make item clickable
        this.onNoteListener = onNoteListener;
        itemView.setOnClickListener(this);

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoteListener.onIncrementClick(getAdapterPosition());
            }
        });
        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoteListener.onDecrementClick(getAdapterPosition());
            }
        });
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoteListener.onDetailsClick(getAdapterPosition());
            }
        });


    }

    @Override
    public void onClick(View v) {
        onNoteListener.onNoteClick(getAdapterPosition());
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
        void onIncrementClick(int position);
        void onDecrementClick(int position);
        void onDetailsClick(int position);
    }


}
