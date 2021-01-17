package com.example.food_order;

import android.graphics.Bitmap;

public class PlaylistObject {
    public String playlistName;
    public Bitmap foodImage;
    public int size;

    public PlaylistObject(String mplaylistName, Bitmap mfoodImage) {
        playlistName = mplaylistName;
        foodImage = mfoodImage;
    }

    @Override
    public String toString() {
        return "PlaylistObject{" +
                ", playlistName='" + playlistName + '\'' +
                ", foodImage=" + foodImage +
                ", size=" + size +
                '}';
    }
}
