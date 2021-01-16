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
    ImageView foodImage;
    TextView foodName;
    TextView foodDescription;
    TextView foodPrice;
    OnNoteListener onNoteListener;

    public MenuViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
        super(itemView);
        this.itemView = itemView;
        foodImage = itemView.findViewById(R.id.menu_dish_image);
        foodName = itemView.findViewById(R.id.menu_dish_name);
        foodDescription = itemView.findViewById(R.id.menu_dish_description);
        foodPrice = itemView.findViewById(R.id.menu_dish_price);

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
