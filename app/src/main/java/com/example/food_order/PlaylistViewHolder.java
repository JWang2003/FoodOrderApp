package com.example.food_order;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlaylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // Properties
    View itemView;
    TextView nameView;
    ImageView imageView;
    com.example.food_order.PlaylistViewHolder.OnNoteListener onNoteListener;

    public PlaylistViewHolder(@NonNull View itemView, com.example.food_order.PlaylistViewHolder.OnNoteListener onNoteListener) {
        super(itemView);
        this.itemView = itemView;
        nameView = itemView.findViewById(R.id.NameView);
        imageView = itemView.findViewById(R.id.image_playlist);
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