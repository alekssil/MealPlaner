package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.entities.Recipe;
import com.example.myapplication.services.CustomAdapter;
import com.example.myapplication.services.DatabaseHelper;

import java.util.ArrayList;

public class RecipesList extends AppCompatActivity {
    DatabaseHelper myDb;
    private ListView lv;
    private ArrayList<Recipe> recipeArrayList;
    private CustomAdapter customAdapter;
    private Button btn_generate_shopping_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        myDb = new DatabaseHelper(this);
        lv = findViewById(R.id.lv);
        btn_generate_shopping_list = findViewById(R.id.generate_shopping_list);
        recipeArrayList = getRecipe();
        customAdapter = new CustomAdapter(this, recipeArrayList);
        lv.setAdapter(customAdapter);
        btn_generate_shopping_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesList.this, ShoppingList.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Recipe> getRecipe() {
        ArrayList<Recipe> recipes = myDb.readAllRecipes();
        ArrayList<Recipe> selectedRecipes = new ArrayList();
        for (Recipe recipe : recipes) {
            recipe.getSelected();
            selectedRecipes.add(recipe);
            boolean value = recipe.getSelected();
            //String s = Boolean.toString(value);
        }
        return selectedRecipes;
    }
}