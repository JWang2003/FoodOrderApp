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
//TODO: Allow user to rename playlist?
    Context context;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static com.example.food_order.DatabaseAccess instance;
    Cursor c = null;

    // This is to add images to the database
    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInBytes;

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
        close();
        return dishes;
    }

    public void addFoodToCart(Dish dish, int quantity) {
        // TODO: Do not allow repeat dishes if already in cart, simply update the quantity
        try {
            open();
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
                Toast.makeText(context, "Data added into our table", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show();
            }
            close();
        }
        catch(Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            close();
        }
    }

    public ArrayList<Dish> getCartDishes() {
        open();
        ArrayList<Dish> dishes = new ArrayList<>();
        c = db.rawQuery("select * from Cart", null);
        if(c!=null && c.getCount() > 0) {
            while(c.moveToNext()) {
                int quantity = c.getInt(0);
                String name = c.getString(1);
                byte[] image = c.getBlob(2);

                Bitmap bmp;
                if (image != null) {
                    bmp = BitmapFactory.decodeByteArray(image, 0 , image.length);
                } else {
                    bmp = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.default_image);
                }
                String price = c.getString(3);
                String details = c.getString(4);

                dishes.add(new Dish(quantity, name, bmp, price, details));
            }
        }else {
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
        db.delete("Cart", "FoodItem = ?", new String[] {foodName});
        close();
    }

    public void updateQuantity(String foodName, int quantity) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", quantity);
        db.update("Cart", contentValues, "FoodItem= ?", new String[] { foodName });
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
                Toast.makeText(context, "Successfully saved changes", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Could not save changes!", Toast.LENGTH_SHORT).show();
            }
            close();
        }
        catch(Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            close();
        }
    }

    public ArrayList<PlaylistObject> getPlaylists() {
        open();
        ArrayList<String> playlistNames = new ArrayList<>();
        ArrayList<PlaylistObject> playlistObjects = new ArrayList<>();
        c = db.rawQuery("select * from Favourites", null);
        if(c!=null && c.getCount() > 0) {
            while(c.moveToNext()) {
                String name = c.getString(2);

                if(!playlistNames.contains(name)) {
                    byte[] image = c.getBlob(3);

                    Bitmap bmp;
                    if (image != null) {
                        bmp = BitmapFactory.decodeByteArray(image, 0 , image.length);
                    } else {
                        bmp = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.default_image);
                    }
                    PlaylistObject object = new PlaylistObject(name, bmp, context);
                    playlistObjects.add(object);
                }
            }
        } else {
            System.out.println("Failed to add, cursor is null or count is 0");
        }
        // TODO: Delete this when debugging over
        if(playlistObjects.isEmpty()) {
            c = db.rawQuery("select * from TokyoJoeSushi", null);
            if(c!=null && c.getCount() > 0) {
                while(c.moveToNext()) {
                    String name = "Default cause playlist is empty";
                    byte[] image = c.getBlob(1);

                    Bitmap bmp;
                    if (image != null) {
                        bmp = BitmapFactory.decodeByteArray(image, 0 , image.length);
                    } else {
                        bmp = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.acousticbreeze);
                    }

                    PlaylistObject object = new PlaylistObject(name, bmp, context);
                    playlistObjects.add(object);
                    System.out.println("Used the debugging playlist as there was nothing in playlist");
                }
            }else {
                System.out.println("Failed to add, cursor is null or count is 0");
            }
            
                }
        close();
        return playlistObjects;
    }

    public ArrayList<Dish> getPlaylistDishes(String nameOfPlaylist) {
        open();
        ArrayList<Dish> dishes = new ArrayList<>();
        c = db.rawQuery("select * from Favourites where Name = '" + nameOfPlaylist + "'", new String[]{});
        if(c!=null && c.getCount() > 0) {
            while(c.moveToNext()) {
                int quantity = c.getInt(0);
                String name = c.getString(2);
                byte[] image = c.getBlob(3);

                Bitmap bmp;
                if (image != null) {
                    bmp = BitmapFactory.decodeByteArray(image, 0 , image.length);
                } else {
                    bmp = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.default_image);
                }
                String price = c.getString(4);
                String details = c.getString(5);

                dishes.add(new Dish(quantity, name, bmp, price, details));
            }
        }else {
            System.out.println("Failed to add, cursor is null or count is 0");
        }
        close();
        return dishes;
    }

    public void deleteFromPlaylist(String playlistName, String foodName) {
        open();
        db.delete("Favourites", "Name=? and FoodItem=?", new String[] {playlistName, foodName});
        close();
    }

    public void deletePlaylist(String playlistName) {
        open();
        db.delete("Favourites", "Name=?", new String[] {playlistName});
        close();
    }
}

