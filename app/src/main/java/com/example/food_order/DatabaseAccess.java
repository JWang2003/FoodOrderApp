package com.example.food_order;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class DatabaseAccess {

    Context context;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static com.example.food_order.DatabaseAccess instance;
    Cursor c = null;

    // Private constructor so that our database is a singleton
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
        this.context = context;
    }

    public static com.example.food_order.DatabaseAccess getInstance(Context context) {
        if(instance == null) {
            instance = new com.example.food_order.DatabaseAccess(context);
        }
        return instance;
    }

    // To open the database
    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    // To close the database connection
    public void close() {
        if(db!= null) {
            this.db.close();
        }
    }

    public ArrayList<Restaurant> getRestaurants(String category) {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        System.out.println("trying to open " + category);
        open();
        c = db.rawQuery("select * from AllRestaurants where Category = '" + category + "'", new String[]{});
        StringBuffer buffer = new StringBuffer();
        if(c!=null && c.getCount() > 0) {
            while(c.moveToNext()) {
                    String name = c.getString(1);
                    byte[] image = c.getBlob(2);
                    Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
                    String address = c.getString(3);
                    String priceRange = c.getString(4);
                    String starRating = c.getString(5);
                    String deliveryFee = c.getString(6);
                    String yelpUrl = c.getString(7);
                    restaurants.add(new Restaurant(name, bmp, address, priceRange, starRating, deliveryFee, yelpUrl));
                    System.out.println("Restaurant added " + name);
            }
        }else {
            System.out.println("Failed to add, cursor is null or count is 0");
        }
        System.out.println(restaurants);
        close();
        return restaurants;
    }

    public ArrayList<Dish> getDishes(String restaurantName) {

        // Get rid of all spaces and punctuation from the restaurantName
        restaurantName = restaurantName.replaceAll("[^A-Za-z]+", "");
        ArrayList<Dish> dishes = new ArrayList<Dish>();
        open();
        System.out.println("trying to open " + restaurantName);
        c = db.rawQuery("select * from " + restaurantName, null);
        if(c!=null && c.getCount() > 0) {
            while(c.moveToNext()) {
                String name = c.getString(0);
                byte[] image = c.getBlob(1);

                Bitmap bmp;
                if (image != null) {
                    bmp = BitmapFactory.decodeByteArray(image, 0 , image.length);
                } else {
                    bmp = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.acousticbreeze);
                }
                String price = c.getString(2);
                String details = c.getString(3);

                dishes.add(new Dish(name, bmp, price, details));
                System.out.println("Restaurant added " + name);
            }
        }else {
            System.out.println("Failed to add, cursor is null or count is 0");
        }
//        close();
        return dishes;
    }
}

