package com.example.food_order;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Restaurant implements Parcelable {

    // Displayed information
    public String name;
    public Bitmap image;
    public String address;
    public String priceRange;
    public String starRating;
    public String deliveryFee;
    public String yelpUrl;

    // Menu which gets populated when restaurant is selected
    public ArrayList<Dish> menu;

    public Restaurant(String name, Bitmap image, String address, String priceRange, String starRating, String deliveryFee, String yelpUrl) {
        this.name = name;
        this.image = image;
        this.address = address;
        this.priceRange = priceRange;
        this.starRating = starRating;
        this.deliveryFee = deliveryFee;
        this.yelpUrl = yelpUrl;
    }

    protected Restaurant(Parcel in) {
        name = in.readString();
        address = in.readString();
        priceRange = in.readString();
        starRating = in.readString();
        deliveryFee = in.readString();
        yelpUrl = in.readString();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", image=" + image +
                ", address='" + address + '\'' +
                ", priceRange='" + priceRange + '\'' +
                ", starRating='" + starRating + '\'' +
                ", deliveryFee='" + deliveryFee + '\'' +
                ", yelpUrl='" + yelpUrl + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(priceRange);
        dest.writeString(starRating);
        dest.writeString(deliveryFee);
        dest.writeString(yelpUrl);
    }
}
