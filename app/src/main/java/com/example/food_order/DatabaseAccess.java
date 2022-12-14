package com.example.food_order;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DatabaseAccess {

    Context context;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static com.example.food_order.DatabaseAccess instance;
    Cursor c = null;
    Cursor d = null;

    // This is to add images to the database
    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInBytes;

    // Private constructor so that our database is a singleton
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
        this.context = context;
    }

    public static com.example.food_order.DatabaseAccess getInstance(Context context) {
        if (instance == null) {
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
        if (db != null) {
            this.db.close();
        }
    }

    public ArrayList<Restaurant> getRestaurants(String category) {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        System.out.println("trying to open " + category);
        open();
        c = db.rawQuery("select * from AllRestaurants where Category = '" + category + "'", new String[]{});
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String name = c.getString(1);
                byte[] image = c.getBlob(2);
                Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                String address = c.getString(3);
                String priceRange = c.getString(4);
                String starRating = c.getString(5);
                String deliveryFee = c.getString(6);
                String yelpUrl = c.getString(7);
                restaurants.add(new Restaurant(name, bmp, address, priceRange, starRating, deliveryFee, yelpUrl));
                System.out.println("Restaurant added " + name);
            }
        } else {
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
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String name = c.getString(0);
                byte[] image = c.getBlob(1);

                Bitmap bmp;
                if (image != null) {
                    bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                } else {
                    bmp = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.logo);
                }
                String price = c.getString(2);
                String details = c.getString(3);

                dishes.add(new Dish(name, bmp, price, details));
                System.out.println("Restaurant added " + name);
            }
        } else {
            System.out.println("Failed to add, cursor is null or count is 0");
        }
        close();
        return dishes;
    }

    public void addFoodToCart(Dish dish, int quantity) {
        ArrayList<Dish> dishes = getCartDishes();
        boolean alreadyExists = false;
        try {
            open();
            Dish repeatDish = dish;
            for (Dish cartDish : dishes) {
                if (cartDish.mFoodName.equals(dish.mFoodName)) {
                    alreadyExists = true;
                    repeatDish = cartDish;
                    break;
                }
            }
            if (alreadyExists) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("ID", quantity + repeatDish.mQuantity);
                db.update("Cart", contentValues, "FoodItem = ?", new String[]{String.valueOf(repeatDish.mFoodName)});
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("ID", quantity);
                contentValues.put("FoodItem", dish.mFoodName);
                // Extra steps to store bitmap image
                Bitmap imageToStoreBitmap = dish.mFoodImage;
                objectByteArrayOutputStream = new ByteArrayOutputStream();
                imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
                imageInBytes = objectByteArrayOutputStream.toByteArray();

                contentValues.put("FoodImage", imageInBytes);
                contentValues.put("FoodPrice", dish.mPrice);
                contentValues.put("FoodDetails", dish.mDetails);
                long result = db.insert("Cart", null, contentValues);
                if (result != 0) {
                    Toast.makeText(context, "Successfully added/saved to your cart!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to add to your cart :'(", Toast.LENGTH_SHORT).show();

                }
                close();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            close();
        }
    }

    public ArrayList<Dish> getCartDishes() {
        open();
        ArrayList<Dish> dishes = new ArrayList<>();
        c = db.rawQuery("select * from Cart", null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                int quantity = c.getInt(0);
                String name = c.getString(1);
                byte[] image = c.getBlob(2);

                Bitmap bmp;
                if (image != null) {
                    bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                } else {
                    bmp = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.logo);
                }
                String price = c.getString(3);
                String details = c.getString(4);

                dishes.add(new Dish(quantity, name, bmp, price, details));
            }
        } else {
            System.out.println("Failed to add, cursor is null or count is 0");
        }
        close();
        return dishes;
    }

    public void clearCart() {
        open();
        db.delete("Cart", null, null);
        close();
    }

    public void deleteFromCart(String foodName) {
        open();
        db.delete("Cart", "FoodItem = ?", new String[]{foodName});
        close();
    }

    public void updateQuantity(String foodName, int quantity) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", quantity);
        db.update("Cart", contentValues, "FoodItem= ?", new String[]{foodName});
        close();
    }

    public void addToPlaylist(String nameOfPlaylist, Dish dish) {
        try {
            open();
            ContentValues contentValues = new ContentValues();
            contentValues.put("ID", dish.mQuantity);
            contentValues.put("Name", nameOfPlaylist);
            contentValues.put("FoodItem", dish.mFoodName);
            // Extra steps to store bitmap image
            Bitmap imageToStoreBitmap = dish.mFoodImage;
            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
            imageInBytes = objectByteArrayOutputStream.toByteArray();

            contentValues.put("FoodImage", imageInBytes);
            contentValues.put("FoodPrice", dish.mPrice);
            contentValues.put("FoodDetails", dish.mDetails);
            long result = db.insert("Favourites", null, contentValues);
            if (result != 0) {
                //Toast.makeText(context, "Successfully saved changes", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(context, "Could not save changes!", Toast.LENGTH_SHORT).show();
            }
            close();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            close();
        }
    }

    public ArrayList<PlaylistObject> getPlaylists() {
        open();
        ArrayList<String> playlistNames = new ArrayList<>();
        ArrayList<PlaylistObject> playlistObjects = new ArrayList<>();
        d = db.rawQuery("select * from Favourites", null);
        if (d != null && d.getCount() > 0) {
            while (d.moveToNext()) {
                String name = d.getString(1);
                if (!playlistNames.contains(name)) {
                    playlistNames.add(name);
                    byte[] image = d.getBlob(3);

                    Bitmap bmp;
                    if (image != null) {
                        bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                    } else {
                        bmp = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.logo);
                    }

                    playlistObjects.add(new PlaylistObject(name, bmp));
                }
            }
        } else {
            System.out.println("Failed to add, cursor is null or count is 0 in getPlaylists()");
        }
        close();
        return playlistObjects;
    }

    public ArrayList<Dish> getPlaylistDishes(String nameOfPlaylist) {
        open();
        ArrayList<Dish> dishes = new ArrayList<>();
        c = db.rawQuery("select * from Favourites where Name = '" + nameOfPlaylist + "'", new String[]{});
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                int quantity = c.getInt(0);
                String name = c.getString(2);
                byte[] image = c.getBlob(3);

                Bitmap bmp;
                if (image != null) {
                    bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                } else {
                    bmp = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.logo);
                }
                String price = c.getString(4);
                String details = c.getString(5);

                dishes.add(new Dish(quantity, name, bmp, price, details));
            }
        } else {
            System.out.println("Failed to add, cursor is null or count is 0");
        }
        close();
        return dishes;
    }

    private ArrayList<Dish> getPlaylistDishes(String nameOfPlaylist, boolean a) {
        ArrayList<Dish> dishes = new ArrayList<>();
        Cursor c;
        c = db.rawQuery("select * from Favourites where Name = '" + nameOfPlaylist + "'", new String[]{});
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                int quantity = c.getInt(0);
                String name = c.getString(2);
                byte[] image = c.getBlob(3);

                Bitmap bmp;
                if (image != null) {
                    bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                } else {
                    bmp = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.logo);
                }
                String price = c.getString(4);
                String details = c.getString(5);

                dishes.add(new Dish(quantity, name, bmp, price, details));
            }
        } else {
            System.out.println("Failed to add, cursor is null or count is 0 in get playlist dishes()");
        }
        return dishes;
    }

    public void deleteFromPlaylist(String playlistName, String foodName) {
        open();
        db.delete("Favourites", "Name=? and FoodItem=?", new String[]{playlistName, foodName});
        close();
    }

    public void deletePlaylist(String playlistName) {
        open();
        db.delete("Favourites", "Name=?", new String[]{playlistName});
        close();
    }

    public ArrayList<Restaurant> getSearchRestaurants(String query) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        // This is so we don't repeat check categories
        ArrayList<String> checkedCategories = new ArrayList<>();
        query = query.toLowerCase();
        open();
        c = db.rawQuery("select Category, RestaurantName from AllRestaurants", new String[]{});
        if (c != null) {
            while (c.moveToNext()) {
                String restName = c.getString(c.getColumnIndex("RestaurantName"));
                String catName = c.getString(c.getColumnIndex("Category"));
                if (catName.toLowerCase().contains(query) && !checkedCategories.contains(catName.toLowerCase())) {
                    checkedCategories.add(catName.toLowerCase());
                    ArrayList<Restaurant> tempRestaurants = getRestaurants(catName);
                    for (Restaurant restaurant : tempRestaurants) {
                        if (!restaurants.contains(restaurant)) {
                            restaurants.add(restaurant);
                        }
                    }
                }
                if (restName.toLowerCase().contains(query)) {
                    ArrayList<Restaurant> tempRestaurants = getRestaurants(catName);
                    for(Restaurant restaurant : tempRestaurants) {
                        // Check each restaurant in category to find if it matches, we must do this as we can't just search for the restaurant name that matches since restaurants names contain special characters that break sql, thus we must search the category and get the restaurant objects and then check the name of each restaurant
                        if(restaurant.name.toLowerCase().contains(query) && !restaurants.contains(restaurant)) {
                            restaurants.add(restaurant);
                        }
                    }

                } else {
                    System.out.println(restName.toLowerCase() + "Does not Contain " + query);
                }

            }
        }
        close();
        return restaurants;
    }
}

