package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.entities.Recipe;
import com.example.myapplication.services.DatabaseHelper;

public class UpdateRecipe extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    DatabaseHelper myDB;
    Recipe recipe;
    EditText title, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);
        myDB = new DatabaseHelper(this);
        initialize();
        setInfoFromPrevProduct();
    }

    public void setInfoFromPrevProduct(){
        Intent intent = getIntent();
        String num = intent.getStringExtra("RecipeId");
        recipe = myDB.readRecipe(Integer.parseInt(num));
        title.setText(recipe.getTitle());
        url.setText(recipe.getRecipeURL());
    }

    public void initialize(){
        title = findViewById(R.id.title_edit);
        url = findViewById(R.id.url_edit);
    }

    public void update(View view) {
        try{
            Recipe recipeToUpdate = new Recipe();
            recipeToUpdate.setId(recipe.getId());
            recipeToUpdate.setTitle(title.getText().toString());
            recipeToUpdate.setRecipeURL(url.getText().toString());
            boolean isUpdated= myDB.updateRecipe(recipeToUpdate);
            if(isUpdated)
                Toast.makeText(UpdateRecipe.this, "Recipe Updated", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(UpdateRecipe.this, "Recipe IS NOT Updated", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, AllRecipes.class);
            startActivity(intent);

        } catch (NumberFormatException ex){
            Toast.makeText(UpdateRecipe.this, "Enter whole positive number", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}