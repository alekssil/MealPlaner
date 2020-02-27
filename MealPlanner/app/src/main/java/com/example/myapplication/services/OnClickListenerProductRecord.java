package com.example.myapplication.services;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.activities.CheckInventory;
import com.example.myapplication.activities.UpdateProduct;

public class OnClickListenerProductRecord implements View.OnClickListener {
    Context context;
    DatabaseHelper myDB;
    String id;
    String[] items = new String[]{"ml", "g", "pcs"};

    @Override
    public void onClick(View view) {
        context = view.getContext();
        myDB = new DatabaseHelper(context);
        id = view.getTag().toString();

        final CharSequence[] items = { "Edit", "Delete" };

        new AlertDialog.Builder(context).setTitle("Product Record")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            Intent intent = new Intent(context, UpdateProduct.class);
                            intent.putExtra("productId", id);
                            context.startActivity(intent);
                        }
                        else if (item == 1) {
                            boolean deleteSuccessful = myDB.deleteProduct(Integer.parseInt(id));

                            if (deleteSuccessful){
                                Toast.makeText(context, "Product was deleted", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Unable to delete product", Toast.LENGTH_SHORT).show();
                            }
                            ((CheckInventory)context).createProductList();
                        }
                        dialog.dismiss();
                    }
                }).show();
    }
}

