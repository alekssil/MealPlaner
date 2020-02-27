package com.example.myapplication;

import com.example.myapplication.activities.AddProduct;
import com.example.myapplication.activities.AddRecipe;
import com.example.myapplication.activities.AllRecipes;
import com.example.myapplication.activities.CheckInventory;
import com.example.myapplication.activities.RecipeDetailsEditProducts;
import com.example.myapplication.activities.RecipesList;
import com.example.myapplication.activities.ShoppingList;
import com.example.myapplication.activities.UpdateProduct;
import com.example.myapplication.entities.Product;
import com.example.myapplication.services.DatabaseHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowListView;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import static android.os.Build.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk = VERSION_CODES.O)
public class MealPlannerAppTests {
    private MainActivity activity;
    private RecipesList recipesActivity;
    private ShoppingList shoppingActivity;
    private ListView listView;
    private AddProduct addProductActivity;
    private AllRecipes allRecipesActivity;
    private AddRecipe addRecipeActivity;
    private CheckInventory checkInventoryActivity;
    DatabaseHelper db;


    @Test
    public void testDatabaseHelper() throws Exception {

        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();

        System.out.println(activity);
        db = new DatabaseHelper(activity);
        db.getWritableDatabase();

        assertNotNull(activity);
        assertNotNull(db);
        Product product = new Product();
        product.setProductName("brocolli");
        product.setUnit("g");
        product.setInventoryQuantity(0.0);
        db.insertProduct(product);
        List<Product> products = db.readAllProducts();
        assertEquals("mealplanner", db.getDatabaseName());
        System.out.println(db.getDatabaseName());
        assertTrue(products.size() >= 1);
        System.out.println(products.toString());
        assertTrue(products.get(0).getProductName().equals("bread"));
        System.out.println(products.get(0).toString());

        db.getWritableDatabase().close();

    }

    @Test
    public void checkActivityNotNull() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();

        System.out.println(activity);
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveCorrectAppName() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();

        System.out.println(activity);
        String hello = activity.getResources().getString(R.string.app_name);
        assertThat(hello, equalTo("My Application"));
        System.out.println(hello);

    }

    @Test
    public void buttonClickShouldGoToRecipesList() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();

        Button button = (Button) activity.findViewById(R.id.button_mealPlan);
        button.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(RecipesList.class.getCanonicalName(), intent.getComponent().getClassName());

    }

    @Test
    public void buttonClickShouldGoToCheckInventory() throws Exception {

        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();

        Button button = (Button) activity.findViewById(R.id.button_checkInventory);
        button.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(CheckInventory.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void bottomNavigationShouldGoToShoppingList() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();

        Intent expectedIntent = new Intent(activity, ShoppingList.class);

        activity.findViewById(R.id.action_shopping_list).callOnClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(expectedIntent.filterEquals(actualIntent));


    }

    @Test
    public void recipeListTest() throws Exception {
        recipesActivity = Robolectric.buildActivity(RecipesList.class)
                .create()
                .resume()
                .get();

        Intent expectedIntent = new Intent(recipesActivity, ShoppingList.class);

        recipesActivity.findViewById(R.id.generate_shopping_list).callOnClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(recipesActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(expectedIntent.filterEquals(actualIntent));


    }

    @Test
    public void shoppingListTest() throws Exception {
        shoppingActivity = Robolectric.buildActivity(ShoppingList.class)
                .create()
                .resume()
                .get();


        listView=(ListView)shoppingActivity.findViewById(R.id.list_view);

        ShadowListView shadowListView = Shadows.shadowOf(listView);

        shadowListView.populateItems();// will populate the adapter
        assertTrue(listView.getChildCount()>0);


        Button button = (Button) shoppingActivity.findViewById(R.id.back);
        button.performClick();
        Intent intent = Shadows.shadowOf(shoppingActivity).peekNextStartedActivity();
        assertEquals(MainActivity.class.getCanonicalName(), intent.getComponent().getClassName());
        System.out.println(intent.getComponent().getClassName());

    }

    @Test
    public void addProductTest() throws Exception {
        addProductActivity = Robolectric.buildActivity(AddProduct.class)
                .create()
                .resume()
                .get();


        EditText editName=(EditText) addProductActivity.findViewById(R.id.editText);
        EditText editQuantity=(EditText)addProductActivity.findViewById(R.id.editText2);
        Spinner editMeasurement = (Spinner) addProductActivity.findViewById(R.id.spinner1);

        editName.requestFocus();
        editName.setText("TestProduct");
        editQuantity.requestFocus();
        editQuantity.setText("100.0");
        editMeasurement.requestFocus();
        editMeasurement.setSelection(2);


        Button buttonAdd = (Button) addProductActivity.findViewById(R.id.button_add);
        buttonAdd.performClick();
        Intent expectedIntent = new Intent(addProductActivity, CheckInventory.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(addProductActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(expectedIntent.filterEquals(actualIntent));

    }

    @Test
    public void AllRecipesTest() throws Exception {
        allRecipesActivity = Robolectric.buildActivity(AllRecipes.class)
                .create()
                .resume()
                .get();

        listView=(ListView)allRecipesActivity.findViewById(R.id.listview);

        ShadowListView shadowListView = Shadows.shadowOf(listView);

        shadowListView.populateItems();// will populate the adapter
        assertTrue(listView.getChildCount()>0);


    }

    @Test
    public void addRecipeTest() throws Exception {
        addRecipeActivity = Robolectric.buildActivity(AddRecipe.class)
                .create()
                .resume()
                .get();


        EditText editTitle=(EditText) addRecipeActivity.findViewById(R.id.editText);
        EditText editURL=(EditText)addRecipeActivity.findViewById(R.id.editText2);

        editTitle.requestFocus();
        editTitle.setText("Test Recipe");
        editURL.requestFocus();
        editURL.setText("https://test_recipe");



        Button buttonAdd = (Button) addRecipeActivity.findViewById(R.id.button_add);
        buttonAdd.performClick();
        Intent expectedIntent = new Intent(addRecipeActivity, RecipeDetailsEditProducts.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(addRecipeActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(expectedIntent.filterEquals(actualIntent));

    }


    @Test
    public void checkInventoryTest() throws Exception {
        checkInventoryActivity = Robolectric.buildActivity(CheckInventory.class)
                .create()
                .resume()
                .get();

        listView=(ListView)checkInventoryActivity.findViewById(R.id.listview);

        ShadowListView shadowListView = Shadows.shadowOf(listView);

        shadowListView.populateItems();// will populate the adapter
        assertTrue(listView.getChildCount()>0);

        Button addProduct = (Button)(Button) checkInventoryActivity.findViewById(R.id.add);
        addProduct.performClick();
        Intent expectedIntent = new Intent(checkInventoryActivity, AddProduct.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(checkInventoryActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(expectedIntent.filterEquals(actualIntent));

    }

}