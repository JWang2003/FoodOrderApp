package com.example.food_order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_order.cartRecycler.CartDishAdapter;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private ArrayList<String> mPlaylistList;
    private PlaylistAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onOrderClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(PlaylistAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        public ImageView mPlaylistImage;
        public TextView mPlaylistNameText;
        public TextView mItemCountText;
        public Button mOrderButton;
        public ImageButton mDeleteButton;

        public PlaylistViewHolder(View itemView, final PlaylistAdapter.OnItemClickListener listener) {
            super(itemView);
            mPlaylistImage = itemView.findViewById(R.id.image_playlist);
            mPlaylistNameText = itemView.findViewById(R.id.NameView);
            mItemCountText = itemView.findViewById(R.id.itemcount);
            mOrderButton = itemView.findViewById(R.id.orderbutton);
            mDeleteButton = itemView.findViewById(R.id.deleteplaylistbutton);

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
            mOrderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onOrderClick(position);
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

    public PlaylistAdapter(ArrayList<String> playlistList) {
        mPlaylistList = playlistList;
    }

    @Override
    public PlaylistAdapter.PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlists, parent, false);
        PlaylistAdapter.PlaylistViewHolder evh = new PlaylistAdapter.PlaylistViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistAdapter.PlaylistViewHolder holder, int position) {
        String currentItem = mPlaylistList.get(position);
        //holder.mPlaylistImage.setImageBitmap(currentItem.mFoodImage);
        holder.mPlaylistNameText.setText(currentItem.);
        holder.mItemCountText.setText(currentItem.mPrice);
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }
}
