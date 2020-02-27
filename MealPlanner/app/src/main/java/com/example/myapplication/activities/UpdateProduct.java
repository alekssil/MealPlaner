package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.services.DatabaseHelper;
import com.example.myapplication.entities.Product;
import com.example.myapplication.R;


public class UpdateProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper myDB;
    Product product;
    EditText title, quantity;
    Spinner measurements;
    String[] items = new String[]{"ml", "g", "pcs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        myDB = new DatabaseHelper(this);
        initialize();
        setInfoFromPrevProduct();
    }

    public void setInfoFromPrevProduct() {
        Intent intent = getIntent();
        String num = intent.getStringExtra("productId");
        Log.println(Log.ASSERT,  num, "productId ...");
        //product = getProductFormDB(Integer.parseInt(num));
        product=myDB.readProduct(Integer.parseInt(num));
        Log.println(Log.ASSERT,  product.toString(), "products array ...");
        title.setText(product.getProductName());  //preset product name in form
        int position = 0;
        for (int i = 0; i < measurements.getCount(); i++) { //preset product measurement in form
            if (measurements.getItemAtPosition(i).toString().equalsIgnoreCase(product.getUnit())) {
                position = i;
            }
        }
        measurements.setSelection(position);

        if (measurements.getItemAtPosition(position).equals("pcs")) { //preset product measurement in form
            String quantInInt = product.getInventoryQuantity().toString();
            quantInInt = quantInInt.substring(0, quantInInt.length() - 2);
            quantity.setText(quantInInt);
        } else {
            quantity.setText(product.getInventoryQuantity().toString());
        }
    }

    public void initialize() {
        title = findViewById(R.id.title_edit);
        quantity = findViewById(R.id.quantity_edit);
        measurements = findViewById(R.id.spinner2);
        measurements.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measurements.setAdapter(adapter);
    }

    public void update(View view) {
        try {
            String prodName = title.getText().toString();
            String prodQuant = quantity.getText().toString();
            String prodMeasure = measurements.getSelectedItem().toString();

            if (isValueEmpty(prodName) || isValueEmpty(prodQuant) || isValueEmpty(prodMeasure)) {
                throw new NullPointerException();
            }

            prodName = correctName(prodName);
            Double prodQuantDouble = Double.parseDouble(prodQuant);

            if (!isQuantityValid(prodQuantDouble)) {
                throw new NumberFormatException();
            }

            Product productToUpdate = new Product();
            productToUpdate.setId(product.getId());
            productToUpdate.setProductName(prodName);
            productToUpdate.setInventoryQuantity(prodQuantDouble);
            productToUpdate.setUnit(prodMeasure);

            updateProductInDB(productToUpdate);
            goToCheckInventory();

        } catch (NullPointerException e) {
            Toast.makeText(UpdateProduct.this, "Please fill all fields", Toast.LENGTH_LONG).show();
        } catch (NumberFormatException e) {
            Toast.makeText(UpdateProduct.this, "Please type VALID NUMBER\n \f\f\f\f in \"QUANTITY\" field", Toast.LENGTH_LONG).show();
        } catch (android.database.SQLException e) {
            Toast.makeText(UpdateProduct.this, "Unable update product in DATABASE", Toast.LENGTH_LONG).show();
        }
    }


    public boolean isValueEmpty(String string) {
        if (string == null || string.equals("")) {
            return true;
        }
        return false;
    }

    public boolean isQuantityValid(Double val) {
        if (val >= 0) {
            return true;
        }
        return false;
    }

    public String correctName(String name) {
        name = name.toLowerCase().trim();
        if (name.endsWith(".")) {
            name = name.substring(0, name.length() - 1);
        }
        return name;
    }

    public Product getProductFormDB(int num) {
        Log.println(Log.ASSERT,  myDB.readProduct(num).toString(), "getProductFormDB...");
        return myDB.readProduct(num);
    }

    public boolean updateProductInDB(Product product) throws android.database.SQLException {
        boolean isUpdated = myDB.updateProduct(product);
        if (isUpdated)
            Toast.makeText(UpdateProduct.this, "Product updated", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(UpdateProduct.this, "Product IS NOT updated", Toast.LENGTH_LONG).show();
        return isUpdated;
    }

    public void goToCheckInventory() {
        Intent intent = new Intent(this, CheckInventory.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
