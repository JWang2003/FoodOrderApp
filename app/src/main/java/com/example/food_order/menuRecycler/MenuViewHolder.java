package com.example.food_order.menuRecycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // Properties
    View itemView;
    TextView dishNameView;
    TextView descriptionView;
    TextView priceView;
    ImageView starsView;
    OnNoteListener onNoteListener;

    public MenuViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
        super(itemView);
        this.itemView = itemView;
        dishNameView = itemView.findViewById(R.id.);
        descriptionView = itemView.findViewById(R.id);
        priceView = itemView.findViewById(R.id.);
        starsView = itemView.findViewById(R.id.);
        ///


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
