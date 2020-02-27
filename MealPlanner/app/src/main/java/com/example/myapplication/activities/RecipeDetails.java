package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class RecipeDetails extends AppCompatActivity {

    TextView title, recipeURL, rProducts;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);
        title = findViewById(R.id.recipeTitle);
        recipeURL = findViewById(R.id.recipeURL);
        rProducts = findViewById(R.id.recipeProducts);
        readRecipe();
    }

    public void readRecipe() {
        Intent intent = getIntent();
        String currentRecipe = intent.getStringExtra("RECIPEPRODUCTS");
        String currentTitle = intent.getStringExtra("TITLE");
        String currentURL = intent.getStringExtra("URL");
        title.setText(currentTitle);
        rProducts.setText(currentRecipe);
        recipeURL.setText(Html.fromHtml(currentURL));
    }
}



