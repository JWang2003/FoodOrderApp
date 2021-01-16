package com.example.food_order.categoryRecycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // Properties
    View itemView;
    TextView nameView;
    ImageView imageView;
    OnNoteListener onNoteListener;

    public CategoryViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
        super(itemView);
        this.itemView = itemView;
        nameView = itemView.findViewById(R.id.category_name);
        imageView = itemView.findViewById(R.id.piclol);
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
