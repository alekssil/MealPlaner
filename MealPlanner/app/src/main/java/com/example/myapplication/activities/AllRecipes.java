package com.example.myapplication.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.services.DatabaseHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.entities.Product;
import com.example.myapplication.R;
import com.example.myapplication.entities.Recipe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AllRecipes extends AppCompatActivity {
    DatabaseHelper myDB;
    Context context;
    ListView listView;
    Integer recipeID;
    String recipeURL, recipeName;
    List<Recipe> recipes = new LinkedList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_recipes);
        myDB = new DatabaseHelper(this);
        listView = findViewById(R.id.listview);
        readRecords();
    }

    public void readRecords() {
        recipes = myDB.readAllRecipes();
        listView = findViewById(R.id.listview);
        ArrayAdapter<Recipe> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, recipes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Recipe recipe = recipes.get(position);
                context = view.getContext();
                recipeID = recipe.getId();
                recipeName = recipe.getTitle();
                recipeURL = recipe.getRecipeURL();

                final CharSequence[] items = {"Details", "Edit", "Delete"};

                new AlertDialog.Builder(context).setTitle("Recipe Record")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                if (item == 0) {

                                    Intent intent = new Intent(AllRecipes.this, RecipeDetailsEditProducts.class);
                                    ArrayList<Product> recipeProducts;
                                    ArrayList<String> str = new ArrayList<>();
                                    recipeProducts = myDB.readRecipeProducts(recipe);
                                    for (Product product : recipeProducts) {
                                        String s = product.toStringRecipeQuantity();
                                        str.add(s);
                                    }
                                    intent.putExtra("RECIPEPRODUCTS", String.valueOf(str));
                                    intent.putExtra("URL", recipeURL);
                                    intent.putExtra("TITLE", recipeName);
                                    intent.putExtra("RecipeId", recipeID.toString());
                                    startActivity(intent);
                                } else if (item == 1) {
                                    Intent intent = new Intent(AllRecipes.this, UpdateRecipe.class);
                                    intent.putExtra("RecipeId", recipeID.toString());
                                    startActivity(intent);
                                } else if (item == 2) {
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                    builder1.setMessage("Do you really want to delete this Recipe? ");
                                    builder1.setCancelable(true);

                                    builder1.setPositiveButton(
                                            "Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    boolean deleteSuccessful = myDB.deleteRecipe(recipeID);
                                                    if (deleteSuccessful) {
                                                        Toast.makeText(context, "Recipe was deleted", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(context, "UNABLE to delete recipe", Toast.LENGTH_SHORT).show();
                                                    }
                                                    ((AllRecipes) context).readRecords();
                                                }
                                            });

                                    builder1.setNegativeButton(
                                            "No",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();
                                }
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    public void gotoAddRecipe(View view) {
        Intent intent = new Intent(this, AddRecipe.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
