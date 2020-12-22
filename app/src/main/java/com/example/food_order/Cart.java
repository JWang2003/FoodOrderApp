package com.example.food_order;

import java.util.ArrayList;

public class Cart {

    // Private Properties
    private ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
    private static Cart sharedInstance = null;

    // Make Cart a Singleton, now there can only be one Cart. Static makes it so you get it through the class, not an instance of it
    public static Cart getInstance() {
        // If this is the first time creating a Cart, make a new cart, else return the existing one
        if (sharedInstance == null) {
            sharedInstance = new Cart();
        }
        return sharedInstance;
    }

    private Cart() {
        // Don't allow to use constructor, to call Cart, have to use Cart.getInstance();
    }

    // Public methods
    public Integer numberOfItems() {
        Integer numberOfItems = 0;
        for (CartItem cartItem : cartItems) {
            numberOfItems += cartItem.getQuantity();
        }
        return numberOfItems;
    }

    public Integer totalPrice() {
        Integer totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getSubtotalPrice();
        }
        return totalPrice;
    }

    public void add(Dish dish, Integer quantity) {
        // Check if dish is already in our cart
        CartItem existingCartItem = null;
        for (CartItem cartItem : cartItems) {
            if (cartItem.dish.equals(dish)) {
                existingCartItem = cartItem;
                break;
            }
        }
        // Did we find matching cart item?
        if (existingCartItem != null) {
            // If yes, add 1 to the quantity
            existingCartItem.quantity += quantity;

        } else {
            // Else, add new dish to cart
            CartItem newCartItem = new CartItem(dish, quantity);
            cartItems.add(newCartItem);
        }
    }

    // Nest CartItem inside Cart as now Cart can access the private properties of CartItem
    public class CartItem {

        // Private properties
        private Dish dish;
        private Integer quantity;

        // Constructor
        public CartItem(Dish dish, Integer quantity) {
            this.dish = dish;
            this.quantity = quantity;
        }

        // Public Getter Methods
        public Integer getQuantity() {
            return quantity; // Getter for quantity
        }

        public Integer getPrice() {
            return dish == null ? 0 : dish.price; // Ternary operator, if dish is null return 0, else return its price
        }

        public String getDishName() {
            return dish == null ? "" : dish.name;
        }
        public Integer getSubtotalPrice() {
            return quantity * getPrice();
        }
    }
}
