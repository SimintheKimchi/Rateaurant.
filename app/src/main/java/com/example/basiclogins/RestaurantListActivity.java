package com.example.basiclogins;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.lang.reflect.Array;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {

    private ListView listViewRestaurant;
    private FloatingActionButton fabAddRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        wireWidgets();
        populateListView();

        fabAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantListActivity.this, RestaurantActivity.class);
                startActivity(intent);
            }
        });
    }

    private void populateListView() {
        //refactor to onlt get the items that belong to the user
        //get current user's objectID
        //make a dataquery and use the advanced object retrieval pattern
        //to find all restaurants whose ownerID matches user;s objectID
        //sample WHERE clause with a string: name = 'Joe'

        String ownerId = Backendless.UserService.CurrentUser().getObjectId();
        String whereClause = "ownerId = '" + ownerId + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        Backendless.Data.of(Restaurant.class).find(new AsyncCallback<List<Restaurant>>() {
            @Override
            public void handleResponse(List<Restaurant> restaurantList) {
                //all Restaurant instances have been found
                RestaurantAdapter adapter = new RestaurantAdapter(
                        RestaurantListActivity.this,
                        R.layout.item_restaurantlist,
                        restaurantList);
                listViewRestaurant.setAdapter(adapter);
                //set onItemClickListener to open Restaurant Activity
                //take clicked object and include in Intent
                //in RestaurantActivity's onCreate, check if there is Parcelable extra
                //if there is, get Restaurant object and populate fields
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                //an error has occurred, error code can be retrieved with fault.g...
                Toast.makeText(RestaurantListActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void wireWidgets() {
        listViewRestaurant = findViewById(R.id.listview_restaurantlist);
        fabAddRestaurant = findViewById(R.id.fab_addRestaurant_main);
    }
}
