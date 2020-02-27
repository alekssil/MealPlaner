package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.services.DatabaseHelper;
import com.example.myapplication.entities.Product;
import com.example.myapplication.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SearchProductNutritionFacts extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    DatabaseHelper myDb;
    EditText editName;
            //editQuantity;
    //Spinner editMeasurement;
   // Button btnAddData;
    Button btnGetNutritient, updateNutrition;
    TextView nutrients;
    OkHttpClient client;
    Product product, productNutritient;
   // String[] items = new String[]{"ml", "g", "pcs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product_nutrition_facts);
        btnGetNutritient=findViewById(R.id.button_nutrition);
        nutrients=findViewById(R.id.nutrition);
        client = new OkHttpClient();
        updateNutrition=findViewById(R.id.button_nutritionUpdate);

        myDb=new DatabaseHelper(this);
        productNutritient=new Product();

        editName=(EditText)findViewById(R.id.editText);
        Intent intent=getIntent();
        String prodName=intent.getStringExtra("productName");
        editName.setText(prodName);
//        editQuantity=(EditText)findViewById(R.id.editText2);
//        btnAddData=(Button)findViewById(R.id.button_add);
//
//        editMeasurement = (Spinner) findViewById(R.id.spinner1);
//        editMeasurement.setOnItemSelectedListener(this);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        editMeasurement.setAdapter(adapter);
        nutrients=findViewById(R.id.nutrition);

        getNutritientData();
        updateNutritionData();
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }

    public void getNutritientData(){
        btnGetNutritient.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String searchProduct=editName.getText().toString();
                        AsyncTask asyncTask=new AsyncTask() {
                            @Override
                            protected Object doInBackground(Object[] objects) {
                                String urlStr1="https://api.nal.usda.gov/fdc/v1/search?api_key=mU7jPYGHzjMWe1eudAioDzm2ujWzJsqrBDoKIyRo&generalSearchInput=+"+searchProduct;
                                Request request = new Request.Builder()
                                        .url(urlStr1)
                                        .build();
                                Response response = null;
                                try {
                                    response= client.newCall(request).execute();
                                    String in=response.body().string();
                                    JSONObject reader = new JSONObject(in);
                                    String readerStr=reader.toString();
                                    Log.println(Log.ASSERT,readerStr, "readerString");
                                    JSONArray array  = reader.getJSONArray("foods");
                                    String arrayStr = array.toString();
                                    Log.println(Log.ASSERT,arrayStr, "arrayString");
                                    JSONObject product=new JSONObject();
                                    product=array.getJSONObject(0);
                                    String productStr=product.toString();
                                    Log.println(Log.ASSERT,productStr, "productStr");
                                    String productId=product.getString("fdcId");
                                    Log.println(Log.ASSERT,productId, "productID");


                                    return productId;

                                }catch (IOException e){
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Object o){
                                final String productId=o.toString();
                                AsyncTask asyncTask2=new AsyncTask() {

                                    @Override
                                    protected Object doInBackground(Object[] objects) {
                                        String urlStr2 = "https://api.nal.usda.gov/fdc/v1/" + productId + "?api_key=mU7jPYGHzjMWe1eudAioDzm2ujWzJsqrBDoKIyRo";
                                        Request request2 = new Request.Builder()
                                                .url(urlStr2)
                                                .build();
                                        Response response2 = null;
                                        try {
                                            response2 = client.newCall(request2).execute();
                                            //Log.println(Log.DEBUG, response2.body().string(), "response2 String");
                                            String inStr=response2.body().string();
                                            JSONObject reader2 = new JSONObject(inStr);
                                            String readerStr2=reader2.toString();
                                            Log.println(Log.DEBUG,readerStr2, "readerString");
                                            JSONArray nutrientArray  = reader2.getJSONArray("foodNutrients");
                                            String nutrientArrayStr = nutrientArray.toString();
                                            Log.println(Log.DEBUG,nutrientArrayStr, "arrayString");
                                            JSONObject nutrient=new JSONObject();
                                            nutrient=nutrientArray.getJSONObject(0);
                                            String nutrientStr=nutrient.toString();
                                            return nutrientArray;

                                        } catch(
                                                IOException e)

                                        {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Object o) {
                                        String nutrientStr=o.toString();

                                        try {
                                            JSONArray nutrientArray2  = new JSONArray(nutrientStr);

                                            JSONObject protein=nutrientArray2.getJSONObject(0);
                                            Double proteinAmount=protein.getDouble("amount");
                                            productNutritient.setProteinAmount(proteinAmount);

                                            JSONObject fat=nutrientArray2.getJSONObject(1);
                                            Double fatAmount=fat.getDouble("amount");
                                            productNutritient.setFatAmount(fatAmount);

                                            JSONObject carbohydrate=nutrientArray2.getJSONObject(2);
                                            Double carbohydrateAmount=carbohydrate.getDouble("amount");
                                            productNutritient.setCarbohydrateAmount(carbohydrateAmount);

                                            JSONObject energy=nutrientArray2.getJSONObject(3);
                                            Double energyAmount=energy.getDouble("amount");
                                            productNutritient.setEnergy(energyAmount);

                                            JSONObject sugars=nutrientArray2.getJSONObject(8);
                                            Double sugarsAmount=sugars.getDouble("amount");
                                            productNutritient.setSugars(sugarsAmount);

                                            JSONObject fiber=nutrientArray2.getJSONObject(9);
                                            Double fiberAmount=fiber.getDouble("amount");
                                            productNutritient.setFiber(fiberAmount);

                                            nutrients.setText(productNutritient.toProductNutrients());

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }.execute();

                            }
                        }.execute();
                    }
                }
        );
    }
    public void updateNutritionData(){
        updateNutrition.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = getIntent();
                        String num = intent.getStringExtra("productId");
                        Log.println(Log.ASSERT,  num, "productId ...");
                        product=myDb.readProduct(Integer.parseInt(num));
                        try {

                         //   productToUpdate.setId(product.getId());
                            product.setProteinAmount(productNutritient.getProteinAmount());
                            product.setCarbohydrateAmount(productNutritient.getCarbohydrateAmount());
                            product.setFatAmount(productNutritient.getFatAmount());
                            product.setEnergy(productNutritient.getEnergy());
                            product.setSugars(productNutritient.getSugars());
                            product.setFiber(productNutritient.getFiber());

                            updateProductInDB(product);
                            goToCheckInventory();

                        } catch (NullPointerException e) {
                            Toast.makeText(SearchProductNutritionFacts.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                        } catch (NumberFormatException e) {
                            Toast.makeText(SearchProductNutritionFacts.this, "Please type VALID NUMBER\n \f\f\f\f in \"QUANTITY\" field", Toast.LENGTH_LONG).show();
                        } catch (android.database.SQLException e) {
                            Toast.makeText(SearchProductNutritionFacts.this, "Unable update product in DATABASE", Toast.LENGTH_LONG).show();
                        }
                    }


                    public boolean isValueEmpty(String string) {
                        if (string == null || string.equals("")) {
                            return true;
                        }
                        return false;
                    }

                    public boolean isQuantityValid(Double val) {
                        if (val > 0) {
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
                        Log.println(Log.ASSERT,  myDb.readProduct(num).toString(), "getProductFormDB...");
                        return myDb.readProduct(num);
                    }

                    public boolean updateProductInDB(Product product) throws android.database.SQLException {
                        boolean isUpdated = myDb.updateProduct(product);
                        if (isUpdated)
                            Toast.makeText(SearchProductNutritionFacts.this, "Product updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(SearchProductNutritionFacts.this, "Product IS NOT updated", Toast.LENGTH_LONG).show();
                        return isUpdated;
                    }

                    public void goToCheckInventory() {
                        Intent intent = new Intent(SearchProductNutritionFacts.this, CheckInventory.class);
                        startActivity(intent);

                    }
                }
        );
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}