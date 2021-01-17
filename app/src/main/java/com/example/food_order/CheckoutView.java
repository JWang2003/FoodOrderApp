package com.example.food_order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_order.cartRecycler.CartDishAdapter;

import java.util.ArrayList;

public class CheckoutView extends AppCompatActivity {

    private DatabaseAccess db;
    private ArrayList<Dish> mCartList;
    private RecyclerView mRecyclerView;
    private CartDishAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mSubtotalView;
    private TextView mTaxView;
    private TextView mTotalPriceView;
    private ImageButton checkoutButton;
    private double subtotal = 0.00;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkout_view);
        db = DatabaseAccess.getInstance(getApplicationContext());
        Intent intent = getIntent();
        mCartList = db.getCartDishes();

        buildRecyclerView();
        setButtons();
        calculatePriceText();
    }

    public void incrementCounter(int position) {
        Dish currentDish = mCartList.get(position);
        currentDish.mQuantity += 1;
        mAdapter.notifyDataSetChanged();
    }

    public void decrementCounter(int position) {
        Dish currentDish = mCartList.get(position);
        if (currentDish.mQuantity > 0) {
            currentDish.mQuantity -= 1;
        }
        mAdapter.notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        mCartList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void setButtons() {
        checkoutButton = findViewById(R.id.proceed_to_checkout);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.clearCart();
                mCartList.clear();
                mAdapter.notifyDataSetChanged();
                Toast.makeText(CheckoutView.this, "Order successfully placed!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(CheckoutView.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void calculatePriceText() {
        mSubtotalView = findViewById(R.id.price1);
        mTaxView = findViewById(R.id.price2);
        mTotalPriceView = findViewById(R.id.finalprice);

        subtotal = 0;
        for (Dish dish : mCartList) {
            subtotal += (Double.parseDouble(dish.mPrice.substring(1)) * dish.mQuantity);
        }   //not rounded

        mSubtotalView.setText("Subtotal: $" + String.format("%.2f", subtotal));
        mTaxView.setText("+ Tax: $" + String.format("%.2f", subtotal * 0.05));
        mTotalPriceView.setText("$" + String.format("%.2f", subtotal * 1.05));



    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.cart_recycler);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CartDishAdapter(mCartList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CartDishAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDecrementClick(int position) {
                decrementCounter(position);
                calculatePriceText();
                System.out.println("Decrement clicked at " + position);
            }

            @Override
            public void onIncrementClick(int position) {
                incrementCounter(position);
                calculatePriceText();
                System.out.println("Increment clicked at " + position);
            }

            @Override
            public void onDeleteClick(int position) {
                deleteItem(position);
                calculatePriceText();
                System.out.println("Delete clicked at " + position);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        db.clearCart();
        for (Dish dish : mCartList) {
            db.addFoodToCart(dish, dish.mQuantity);
        }
        System.out.println("Added " + mCartList + " to cart");
    }
}