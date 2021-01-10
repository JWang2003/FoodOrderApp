package com.example.food_order.categoryRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private CategoryViewHolder.OnNoteListener mOnNoteListener;

    // Properties
    Context context;
    ArrayList<Category> categories;

    public CategoryAdapter(Context context, ArrayList<Category> categories, CategoryViewHolder.OnNoteListener onNoteListener) {
        this.context = context;
        this.categories = categories;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(com.example.food_order.R.layout.item_category, parent, false);
        CategoryViewHolder viewHolder = new CategoryViewHolder(itemView, mOnNoteListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.nameView.setText(category.categoryName);
        holder.imageView.setImageResource(category.categoryPicture);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
