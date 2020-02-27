package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.entities.Product;
import com.example.myapplication.services.DatabaseHelper;

public class AddProductToRecipe extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper myDb;
    EditText editName, editRecipeQuantity;
    Spinner editMeasurement;
    Button btnAddData;
    String[] items = new String[]{"ml", "g", "pcs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_to_recipe);

        myDb = new DatabaseHelper(this);

        editName = findViewById(R.id.editText);
        editRecipeQuantity = findViewById(R.id.editText2);
        btnAddData = findViewById(R.id.button_add);

        editMeasurement = findViewById(R.id.spinner1);
        editMeasurement.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editMeasurement.setAdapter(adapter);

        addData();
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }

    public void addData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Product product = new Product();
                        product.setProductName(editName.getText().toString());
                        product.setRecipeQuantity(Double.parseDouble(editRecipeQuantity.getText().toString()));
                        product.setUnit(editMeasurement.getSelectedItem().toString());

                        Intent intent = getIntent();
                        String recipeId = intent.getStringExtra("RECIPEIDTOADD");
                        Toast.makeText(AddProductToRecipe.this, "PRODUCT INSERTED INTO PRODUCTS", Toast.LENGTH_LONG).show();
                        String prdName = product.getProductName();
                        boolean productTest = myDb.isProductInDB(prdName);
                        product.setRecipeQuantity(Double.parseDouble(editRecipeQuantity.getText().toString()));

                        if (!productTest) {
                            product.setInventoryQuantity(0.0);
                            boolean isInsertedIntoProduct = myDb.insertProduct(product);
                            if (isInsertedIntoProduct) {
                                String name = product.getProductName();
                                product = myDb.readProductString(name);
                                boolean isInserted = myDb.insertRecipeProducts(Integer.parseInt(recipeId), product.getId(), product.getRecipeQuantity());
                                if (isInserted)
                                    Toast.makeText(AddProductToRecipe.this, "Product is saved to recipe", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(AddProductToRecipe.this, "Product IS NOT saved to recipe", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(AddProductToRecipe.this, "Product IS NOT saved to inventory", Toast.LENGTH_LONG).show();
                        } else {
                            String name = product.getProductName();
                            product = myDb.readProductString(name);
                            boolean isInserted = myDb.insertRecipeProducts(Integer.parseInt(recipeId), product.getId(), product.getRecipeQuantity());

                            if (isInserted)
                                Toast.makeText(AddProductToRecipe.this, "Product is saved to recipe", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(AddProductToRecipe.this, "Product IS NOT saved to recipe", Toast.LENGTH_LONG).show();

                        }

                        Intent intentNew = new Intent(AddProductToRecipe.this, RecipeProductList.class);
                        intentNew.putExtra("RECIPEIDNEW", recipeId);
                        Log.println(Log.ASSERT, String.valueOf(intentNew), "intent that allegedly crashes...");
                        startActivity(intentNew);
                    }
                }
        );
    }
}