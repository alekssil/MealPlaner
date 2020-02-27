package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.entities.Product;
import com.example.myapplication.entities.Recipe;
import com.example.myapplication.services.DatabaseHelper;

import java.util.ArrayList;

public class UpdateRecipeProduct extends AppCompatActivity {
    DatabaseHelper myDB;
    //Product product;
    Product newProduct;
    Recipe recipe;
    EditText title, recipeQuantity;
    ArrayList<Product> products = new ArrayList<>();

//    Spinner measurements;
//    String[] items = new String[]{"ml", "g", "pcs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe_product);
        myDB = new DatabaseHelper(this);
        initialize();
        setInfoFromPrevProduct();
    }

    public void setInfoFromPrevProduct(){
        Intent intent = getIntent();
        //String num = intent.getStringExtra("productId");
        String recId=intent.getStringExtra("recipeId");
        String pName=intent.getStringExtra("productName");
        recipe=myDB.readRecipe(Integer.parseInt(recId));
        products = myDB.readRecipeProducts(recipe);

        for(Product product: products){
            if(product.getProductName().equals(pName)){
                newProduct=product;
            }
        }
        title.setText(newProduct.getProductName());
        recipeQuantity.setText(newProduct.getRecipeQuantity().toString());
    }

    public void initialize(){
        title = findViewById(R.id.title_edit);
        recipeQuantity = findViewById(R.id.quantity_edit);
    }

    public void update(View view) {
        try{
            //Intent intent0 = getIntent();
            //String num = intent0.getStringExtra("productId");
            newProduct.setRecipeQuantity(Double.parseDouble(recipeQuantity.getText().toString()));

            for(Product product: products){
                if(product.getProductName().equals(newProduct.getProductName())){
                    product.setRecipeQuantity(newProduct.getRecipeQuantity());
                }
            }

            recipe.setRecipeProducts(products);
            boolean isUpdated= myDB.updateRecipeProducts(recipe);

            if(isUpdated)
                Toast.makeText(UpdateRecipeProduct.this, "Recipe Product Updated", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(UpdateRecipeProduct.this, "Recipe Product IS NOT ADD", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, RecipeProductList.class);
            String prodId=String.valueOf(recipe.getId());
            intent.putExtra("RECIPEIDNEW", prodId);
            startActivity(intent);
        } catch (NumberFormatException ex){
            Toast.makeText(UpdateRecipeProduct.this, "Enter whole positive number", Toast.LENGTH_LONG).show();
        }
    }
}
