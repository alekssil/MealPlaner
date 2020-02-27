package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.entities.Recipe;

public class RecipeDetailsEditProducts extends AppCompatActivity {

    //Recipe recipe;
    Button gotoEditRecipeProducts;
    TextView title, recipeURL, rProducts;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details_edit_products);
        title = findViewById(R.id.recipeTitle);
        recipeURL = findViewById(R.id.recipeURL);
        rProducts = findViewById(R.id.recipeProducts);
        gotoEditRecipeProducts = findViewById(R.id.updateRecipeProducts);
        readRecipe();
        gotoEditRecipeProducts();
    }

    public void readRecipe() {
        Intent intent = getIntent();
        String currentRecipe = intent.getStringExtra("RECIPEPRODUCTS");
        String currentTitle = intent.getStringExtra("TITLE");
        String currentURL = intent.getStringExtra("URL");
        //String currentid = intent.getStringExtra("RecipeId");
        title.setText(currentTitle);
        rProducts.setText(currentRecipe);
        recipeURL.setText(Html.fromHtml(currentURL));
    }

    public void gotoEditRecipeProducts() {
        gotoEditRecipeProducts.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = getIntent();
                        //String intentStr = intent.toString();
                        String currentID = intent.getStringExtra("RecipeId");
                        Intent newIntent = new Intent(RecipeDetailsEditProducts.this, RecipeProductList.class);
                        newIntent.putExtra("RECIPEIDNEW", currentID);
                        startActivity(newIntent);

                        if (intent != null)
                            Toast.makeText(RecipeDetailsEditProducts.this, "Go to recipe product list", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(RecipeDetailsEditProducts.this, "ERROR: can not go to recipe product list", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AllRecipes.class);
        startActivity(intent);
    }
}