package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.services.DatabaseHelper;
import com.example.myapplication.entities.Product;
import com.example.myapplication.R;

public class AddProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper myDb;
    EditText editName, editQuantity;
    Spinner editMeasurement;
    Button btnAddData;
    String[] items = new String[]{"ml", "g", "pcs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        myDb = new DatabaseHelper(this);

        editName = findViewById(R.id.editText);
        editQuantity = findViewById(R.id.editText2);
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
                        try {
                            String prodName = editName.getText().toString();
                            String prodQuant = editQuantity.getText().toString();
                            String prodMeasure = editMeasurement.getSelectedItem().toString();

                            if (isValueEmpty(prodName) || isValueEmpty(prodQuant) || isValueEmpty(prodMeasure)) {
                                throw new NullPointerException();
                            }
                            prodName = correctName(prodName);
                            Double prodQuantDouble = Double.parseDouble(prodQuant);
                            if (!isQuantityValid(prodQuantDouble)) {
                                throw new NumberFormatException();
                            }
                            Product product = new Product();
                            product.setProductName(prodName);
                            product.setInventoryQuantity(prodQuantDouble);
                            product.setUnit(prodMeasure);

                            saveProductIntoDB(product);
                            goToCheckInventory();

                        } catch (NullPointerException e) {
                            Toast.makeText(AddProduct.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                        } catch (NumberFormatException e) {
                            Toast.makeText(AddProduct.this, "Please type VALID NUMBER\n \f\f\f\f in \"QUANTITY\" field", Toast.LENGTH_LONG).show();
                        } catch (android.database.SQLException e) {
                            Toast.makeText(AddProduct.this, "Unable to save product in DATABASE", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
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

    public boolean saveProductIntoDB(Product product) throws android.database.SQLException {
        boolean isInserted = myDb.insertProduct(product);
        if (isInserted)
            Toast.makeText(AddProduct.this, "Product saved", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(AddProduct.this, "\f\f\f\fProduct IS NOT SAVED.\nCheck your inventory, maybe product already exists.", Toast.LENGTH_LONG).show();
        return isInserted;
    }

    public void goToCheckInventory() {
        Intent intent = new Intent(AddProduct.this, CheckInventory.class);
        startActivity(intent);
    }
}
