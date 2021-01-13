package com.example.food_order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistViewHolder> {

    private PlaylistViewHolder.OnNoteListener mOnNoteListener;

    // Properties
    Context context;
    ArrayList<Playlist> playlists;

    public PlaylistAdapter(Context context, ArrayList<Playlist> playlists, PlaylistViewHolder.OnNoteListener onNoteListener) {
        this.context = context;
        this.playlists = playlists;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_playlists, parent, false);
        PlaylistViewHolder viewHolder = new PlaylistViewHolder(itemView, mOnNoteListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        holder.nameView.setText(playlist.playlistName);
        holder.imageView.setImageResource(playlist.playlistPicture);
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }
}
