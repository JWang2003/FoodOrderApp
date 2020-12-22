package com.example.food_order;

public enum Cuisine {
    // Full list of possible food categories a restaurant can have
    BREAKFAST("Breakfast"),
    BURGERS("Burgers"),
    CHINESE("Chinese"),
    DESSERT("Dessert"),
    FISH("Fish"),
    JAPANESE("Japanese"),
    KOREAN("Korean"),
    MEXICAN("Mexican"),
    SALAD("Salad"),
    STEAK("Steak"),
    SUSHI("Sushi");

    private String cuisineName;

    Cuisine(String cuisineName) {
        this.cuisineName = cuisineName;
    }

    @Override
    public String toString() {
        return cuisineName;
    }
}
