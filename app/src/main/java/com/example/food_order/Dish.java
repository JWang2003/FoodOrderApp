package com.example.food_order;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Dish implements Parcelable {
    public int mQuantity;
    public String mFoodName;
    public Bitmap mFoodImage;
    public String mPrice;
    public String mDetails;

    public Dish(String foodName, Bitmap foodImage, String price, String details) {
        mQuantity = 1;
        mFoodName = foodName;
        mFoodImage = foodImage;
        mPrice = price;
        mDetails = details;
    }

    protected Dish(Parcel in) {
        mQuantity = in.readInt();
        mFoodName = in.readString();
        mFoodImage = in.readParcelable(Bitmap.class.getClassLoader());
        mPrice = in.readString();
        mDetails = in.readString();
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    @Override
    public String toString() {
        return "Dish{" +
                ", quantity='" + mQuantity + '\'' +
                "foodName='" + mFoodName + '\'' +
                ", foodImage=" + mFoodImage +
                ", price='" + mPrice + '\'' +
                ", details='" + mDetails + '\'' +
                '}';
    }

    public Dish(int quantity, String foodName, Bitmap foodImage, String price, String details) {
        mQuantity = quantity;
        mFoodName = foodName;
        mFoodImage = foodImage;
        mPrice = price;
        mDetails = details;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(mFoodName);
        dest.writeString(mPrice);
        dest.writeString(mDetails);
    }
}
