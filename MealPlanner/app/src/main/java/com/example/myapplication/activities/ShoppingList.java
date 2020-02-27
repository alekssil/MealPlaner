package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.services.DatabaseHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ShoppingList extends AppCompatActivity {

    DatabaseHelper myDb;
    ListView listView;
    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        myDb = new DatabaseHelper(this);
        goBack = findViewById(R.id.back);
        getSelectedRecipes();
        gotoMealPlan();
    }

    public void getSelectedRecipes() {
        ArrayList<String> products = myDb.readShoppingList();
        listView = findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, products);
        listView.setAdapter(adapter);
    }

    public void gotoMealPlan() {
        goBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        if (intent != null)
                            Toast.makeText(ShoppingList.this, "GO TO MAIN MENU", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ShoppingList.this, "ERROR: CANNOT GO TO MAIN MENU", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}