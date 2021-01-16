package com.example.food_order;

import android.content.Context;
import android.graphics.Bitmap;

public class PlaylistObject {
    DatabaseAccess db;
    String playlistName;
    Bitmap foodImage;
    int size;

    public PlaylistObject (String playlistName, Bitmap foodImage, Context context) {
        db =  DatabaseAccess.getInstance(context);
        this.playlistName = playlistName;
        this.foodImage = foodImage;
        this.size = db.getPlaylistDishes(playlistName).size();
    }
}
