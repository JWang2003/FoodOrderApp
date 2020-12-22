package com.example.food_order;

import androidx.annotation.NonNull;

public class Dish {
    public String name;
    public String description;
    public int price;
    public int photo;

    public Dish(@NonNull String name, @NonNull String description, int price, int photo) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.photo = photo;
    }
    // Show Dish information when printing
    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", photo=" + photo +
                ", description='" + description + '\'' +
                '}';
    }

    public Boolean equals(Dish dish) {
        return (name.equals(dish.name) && description.equals(dish.description) && price == dish.price && photo == dish.photo);
    }
}
