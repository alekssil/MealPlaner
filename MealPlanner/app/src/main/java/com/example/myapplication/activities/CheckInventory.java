package com.example.myapplication.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.activities.SearchProductNutritionFacts;
import com.example.myapplication.services.DatabaseHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.entities.Product;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class CheckInventory extends AppCompatActivity {
    DatabaseHelper myDB;
    Context context;
    ListView listView;
    Integer productID;
    String productName;
    List<Product> products = new LinkedList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_inventory);
        myDB = new DatabaseHelper(this);
        listView = findViewById(R.id.listview);
        createProductList();

    }

    public void createProductList(){
        products = getAllProductsFromDB();

        listView = findViewById(R.id.listview);
        ArrayAdapter<Product> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, products);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = products.get(position);
                context = view.getContext();
                productID = product.getId();
                productName=product.getProductName();

                final CharSequence[] items = { "Search Nutrition Facts", "Edit", "Delete" };

                new AlertDialog.Builder(context).setTitle("Product Record")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                if (item == 0) {
                                    Intent intent = new Intent(CheckInventory.this, SearchProductNutritionFacts.class);
                                    intent.putExtra("productId", productID.toString());
                                    intent.putExtra("productName", productName);
                                    startActivity(intent);
                                }
                                if (item == 1) {
                                    Intent intent = new Intent(CheckInventory.this, UpdateProduct.class);
                                    intent.putExtra("productId", productID.toString());
                                    startActivity(intent);
                                }
                                else if (item == 2) {
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                    builder1.setMessage("Do you really want to delete this product?");
                                    builder1.setCancelable(true);
                                    builder1.setPositiveButton(
                                            "Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    try {
                                                        deleteProduct(productID);
                                                    } catch (android.database.SQLException e){
                                                        Toast.makeText(CheckInventory.this, "Unable delete product from DATABASE", Toast.LENGTH_LONG).show();
                                                    }
                                                    ((CheckInventory)context).createProductList();
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

    public ArrayList<Product> getAllProductsFromDB(){
        Log.println(Log.ASSERT,  myDB.readAllProducts().toString(), "getAllProductsFromDB...");
        return myDB.readAllProducts();

    }

    public boolean deleteProduct(int productID){ //throws android.database.SQLException{
        boolean deleteSuccessful = myDB.deleteProduct(productID);
        if (deleteSuccessful){
            Toast.makeText(context, "Product is deleted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Product IS NOT deleted", Toast.LENGTH_SHORT).show();
        }
        return deleteSuccessful;
    }

    public void gotoAddProduct(View view){
        Intent intent = new Intent(this, AddProduct.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
