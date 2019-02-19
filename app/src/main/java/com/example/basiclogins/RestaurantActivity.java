package com.example.basiclogins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;

public class RestaurantActivity extends AppCompatActivity {
    private EditText restaurantName;
    private EditText restaurantAddress;
    private EditText restaurantCuisine;
    private RatingBar restaurantRate;
    private Spinner restaurantPrice;
    private Button save;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        wireWidgets();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void wireWidgets() {
        restaurantName = findViewById(R.id.editText_name_main);
        restaurantAddress = findViewById(R.id.editText_address_main);
        restaurantCuisine = findViewById(R.id.editText_cuisine_main);
        restaurantRate = findViewById(R.id.ratingBar_rateRestaurant_main);
        restaurantPrice = findViewById(R.id.spinner_price_main);
        save = findViewById(R.id.button_save_main);
        cancel = findViewById(R.id.button_cancel_main);
    }
}
