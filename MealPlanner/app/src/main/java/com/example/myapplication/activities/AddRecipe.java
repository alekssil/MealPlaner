package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.entities.Product;
import com.example.myapplication.entities.Recipe;
import com.example.myapplication.services.DatabaseHelper;

import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper myDb;
    EditText editName, editURL;
    Button btnAddToRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        myDb = new DatabaseHelper(this);
        editName = findViewById(R.id.editText);
        editURL = findViewById(R.id.editText2);
        btnAddToRecipe = findViewById(R.id.button_add);
        addData();
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }

    public void addData() {
        btnAddToRecipe.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Recipe recipe = new Recipe();
                        recipe.setTitle(editName.getText().toString());
                        recipe.setRecipeURL(editURL.getText().toString());

                        boolean isInserted = myDb.insertRecipe(recipe);

                        if (isInserted)
                            Toast.makeText(AddRecipe.this, "Recipe is saved", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(AddRecipe.this, "Recipe IS NOT saved", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(AddRecipe.this, RecipeDetailsEditProducts.class);
                        ArrayList<Product> recipeProducts =  myDb.readRecipeProducts(recipe);
                        recipe.setRecipeProducts(recipeProducts);
                        String s = String.valueOf(recipe.getRecipeProducts());
                        String title = recipe.getTitle();
                        String url = recipe.getRecipeURL();
                        intent.putExtra("RECIPEPRODUCTS", s);
                        intent.putExtra("URL", url);
                        intent.putExtra("TITLE", title);
                        startActivity(intent);
                    }
                }
        );
    }
}