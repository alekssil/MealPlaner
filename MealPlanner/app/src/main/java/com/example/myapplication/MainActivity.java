package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.activities.CheckInventory;
import com.example.myapplication.activities.RecipesList;
import com.example.myapplication.activities.ShoppingList;
import com.example.myapplication.activities.AllRecipes;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Button gotoCheckInventory;
    Button gotoMealPlan;
    Button gotoRecipes;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gotoCheckInventory = findViewById(R.id.button_checkInventory);
        gotoMealPlan = findViewById(R.id.button_mealPlan);
        gotoRecipes = findViewById(R.id.button_addRecipe);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        gotoCheckInventory();
        gotoMealPlan();
        gotoShoppingList();
        gotoRecipes();
    }

    public void gotoShoppingList() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_shopping_list:
                        Intent intent = new Intent(MainActivity.this, ShoppingList.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    public void gotoCheckInventory() {
        gotoCheckInventory.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), CheckInventory.class);
                        startActivity(intent);
                        if (intent != null)
                            Toast.makeText(MainActivity.this, "Check inventory", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "UNABLE check inventory", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void gotoMealPlan() {
        gotoMealPlan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), RecipesList.class);
                        startActivity(intent);
                        if (intent != null)
                            Toast.makeText(MainActivity.this, "Check meal plan", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "UNABLE check meal plan", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void gotoRecipes() {
        gotoRecipes.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), AllRecipes.class);
                        startActivity(intent);
                        if (intent != null)
                            Toast.makeText(MainActivity.this, "Check recipes", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "UNABLE check recipes", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    @Override
    public void onBackPressed(){
        finish();
    }

}
