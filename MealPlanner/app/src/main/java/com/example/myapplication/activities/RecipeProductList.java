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

import com.example.myapplication.R;
import com.example.myapplication.entities.Product;
import com.example.myapplication.entities.Recipe;
import com.example.myapplication.services.DatabaseHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecipeProductList extends AppCompatActivity {
    DatabaseHelper myDB;
    Context context;
    ListView listView;
    Integer productID;
    List<Product> products = new LinkedList<>();
    List<Recipe> recipes = new LinkedList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_product_list);
        myDB = new DatabaseHelper(this);
        listView = findViewById(R.id.listview);
        readRecords();
    }

    public void readRecords() {
        Intent intent = getIntent();
        String currentID = intent.getStringExtra("RECIPEIDNEW");
        recipes = myDB.readAllRecipes();
        final Recipe recipe = new Recipe();
        recipe.setId(recipes.get(Integer.parseInt(currentID) - 1).getId());
        products = myDB.readRecipeProducts(recipe);
        listView = findViewById(R.id.listview);

        ArrayList<Product> productsClone = new ArrayList<>();
        for (Product product : products) {
            Product newProd = new Product();
            newProd.setId(product.getId());
            newProd.setProductName(product.getProductName());
            newProd.setInventoryQuantity(product.getRecipeQuantity());
            newProd.setUnit(product.getUnit());
            productsClone.add(newProd);
        }
        ArrayAdapter<Product> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, productsClone);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = products.get(position);
                context = view.getContext();
                productID = product.getId();
                final int currentProductID = product.getId();
                final String productName = product.getProductName();
                final CharSequence[] items = {"Edit", "Delete"};

                new AlertDialog.Builder(context).setTitle("Product Record")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                if (item == 0) {
                                    Intent intent = new Intent(RecipeProductList.this, UpdateRecipeProduct.class);
                                    intent.putExtra("productId", String.valueOf(currentProductID));
                                    intent.putExtra("recipeId", String.valueOf(recipe.getId()));
                                    intent.putExtra("productName", productName);
                                    startActivity(intent);
                                } else if (item == 1) {
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                    builder1.setMessage("Do you really want to delete this Product? This may affect the Recipe as well...");
                                    builder1.setCancelable(true);

                                    builder1.setPositiveButton(
                                            "Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    Intent intent = getIntent();
                                                    String recID = intent.getStringExtra("RECIPEIDNEW");
                                                    boolean deleteSuccessful = myDB.deleteRecipeProducts(Integer.parseInt(recID), productID);
                                                    if (deleteSuccessful) {
                                                        Toast.makeText(context, "Product was deleted form recipe", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(context, "UNABLE to delete product form recipe", Toast.LENGTH_SHORT).show();
                                                    }
                                                    ((RecipeProductList) context).readRecords();
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

    public void gotoAddRecipeProduct(View view) {
        Intent intent = getIntent();
        String recipeID = intent.getStringExtra("RECIPEIDNEW");
        Intent newIntent = new Intent(this, AddProductToRecipe.class);
        newIntent.putExtra("RECIPEIDTOADD", recipeID);
        startActivity(newIntent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AllRecipes.class);
        startActivity(intent);
    }
}
