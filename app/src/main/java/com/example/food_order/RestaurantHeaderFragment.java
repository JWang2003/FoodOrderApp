package com.example.food_order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantHeaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantHeaderFragment extends Fragment {

    private View rootView; // Again, can we do this since there is more than 1 restaurant?

    public RestaurantHeaderFragment() {
        // Required empty public constructor
    }


    // Can we pass in a list of restaurants?
    public static RestaurantHeaderFragment newInstance() {
        RestaurantHeaderFragment fragment = new RestaurantHeaderFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setupViews();
        rootView = inflater.inflate(R.layout.fragment_restaurant_header, container, false);
        return rootView; // I don't think this can work
    }

    private void setupViews() {
//        Restaurant restaurant = Resaurant.getInstance(); // We can't use this as Roland made this a singleton but we have more than 1 restaurant
    }
}