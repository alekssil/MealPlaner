package com.example.myapplication.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.entities.Product;
import com.example.myapplication.entities.Recipe;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "mealplanner";
    private static final String TABLE_Products = "Products";
    private static final String KEY_PRODUCT_ID = "ProductID";
    private static final String KEY_PRODUCT_NAME = "ProductName";
    private static final String KEY_PRODUCT_UNIT = "Unit";
    private static final String KEY_PRODUCT_INVENTORY_QUANTITY = "InventoryQuantity";
    private static final String KEY_PRODUCT_PROTEIN_AMOUNT = "Protein";
    private static final String KEY_PRODUCT_FAT_AMOUNT = "Fat";
    private static final String KEY_PRODUCT_CARBS_AMOUNT = "Carbs";
    private static final String KEY_PRODUCT_ENERGY = "Energy";
    private static final String KEY_PRODUCT_FIBER = "Fiber";
    private static final String KEY_PRODUCT_SUGARS = "Sugars";

    private static final String TABLE_Recipes = "Recipes";
    private static final String KEY_RECIPE_ID = "RecipeID";
    private static final String KEY_RECIPE_TITLE = "RecipeTitle";
    private static final String KEY_RECIPE_URL = "RecipeURL";
    private static final String KEY_RECIPE_SELECTED = "RecipeSelected";

    private static final String TABLE_RecipeProducts = "RecipeProducts";
    private static final String KEY_RECIPE_PRODUCTS_ID = "ID";
    private static final String KEY_RECIPE_PRODUCTS_RECIPE_ID = "RecipeID";
    private static final String KEY_RECIPE_PRODUCTS_PRODUCT_ID = "ProductID";
    private static final String KEY_RECIPE_PRODUCTS_QUANTITY = "RecipeQuantity";


    private static final String VIEW_RecipeProducts = "v_RecipeProducts";
    private static final String VIEW_RecipeProductsSelected = "v_RecipeProductsSelected";
    private static final String VIEW_ShoppingList= "v_ShoppingList";
    private static final String VIEW_RecipesList= "v_RecipesList";
    private static final String VIEW_RecipesRanked = "v_RecipesRanked";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_Products + "("
                + KEY_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PRODUCT_NAME + " TEXT,"
                + KEY_PRODUCT_UNIT + " TEXT,"
                + KEY_PRODUCT_INVENTORY_QUANTITY + " NUMERIC,"
                + KEY_PRODUCT_PROTEIN_AMOUNT + " NUMERIC,"
                + KEY_PRODUCT_FAT_AMOUNT + " NUMERIC,"
                + KEY_PRODUCT_CARBS_AMOUNT + " NUMERIC,"
                + KEY_PRODUCT_ENERGY + " NUMERIC,"
                + KEY_PRODUCT_FIBER + " NUMERIC,"
                + KEY_PRODUCT_SUGARS + " NUMERIC,"+
                "UNIQUE (" + KEY_PRODUCT_NAME + "))";
        db.execSQL(CREATE_TABLE_PRODUCTS);

        String INSERT_INTO_PRODUCT1 = "INSERT INTO " + TABLE_Products + "(ProductName,Unit,InventoryQuantity, Protein, Fat, Carbs, Energy, Fiber, Sugars) "
                + "VALUES ('bread','g',0, 0, 0, 0, 0, 0, 0)";
        db.execSQL(INSERT_INTO_PRODUCT1);

        String INSERT_INTO_PRODUCT2 = "INSERT INTO " + TABLE_Products + "(ProductName,Unit,InventoryQuantity, Protein, Fat, Carbs, Energy, Fiber, Sugars) "
                + "VALUES ('milk','ml',0, 0, 0, 0, 0, 0, 0)";
        db.execSQL(INSERT_INTO_PRODUCT2);

        String INSERT_INTO_PRODUCT3 = "INSERT INTO " + TABLE_Products + "(ProductName,Unit,InventoryQuantity, Protein, Fat, Carbs, Energy, Fiber, Sugars) "
                + "VALUES ('eggs','pcs',0, 0, 0, 0, 0, 0, 0)";
        db.execSQL(INSERT_INTO_PRODUCT3);

        String INSERT_INTO_PRODUCT4 = "INSERT INTO " + TABLE_Products + "(ProductName,Unit,InventoryQuantity, Protein, Fat, Carbs, Energy, Fiber, Sugars) "
                + "VALUES ('cheese','g',0, 0, 0, 0, 0, 0, 0)";
        db.execSQL(INSERT_INTO_PRODUCT4);

        String INSERT_INTO_PRODUCT5 = "INSERT INTO " + TABLE_Products + "(ProductName,Unit,InventoryQuantity, Protein, Fat, Carbs, Energy, Fiber, Sugars)"
                + "VALUES ('lettice','g',0, 0, 0, 0, 0, 0, 0)";
        db.execSQL(INSERT_INTO_PRODUCT5);

        /*String INSERT_INTO_PRODUCTS = "INSERT INTO " + TABLE_Products + "(ProductName,Unit,InventoryQuantity) "
                + "VALUES ('Bread','g',0),"
                + "('Milk','ml',0),"
                + "('Eggs','pcs',0),"
                + "('Cheese','g',0),"
                + "('Lettice','g',0)";
        db.execSQL(INSERT_INTO_PRODUCTS);*/

        String CREATE_TABLE_RECIPES = "CREATE TABLE " + TABLE_Recipes + "("
                + KEY_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_RECIPE_TITLE + " TEXT,"
                + KEY_RECIPE_URL + " TEXT,"
                + KEY_RECIPE_SELECTED + " BOOLEAN,"
                + "UNIQUE (" + KEY_RECIPE_TITLE + "))";
        db.execSQL(CREATE_TABLE_RECIPES);

        /*String INSERT_INTO_RECIPES = "INSERT INTO " + TABLE_Recipes + "(RecipeTitle, RecipeURL, RecipeSelected)"
                + " VALUES ('Klasiskais Rasols','https://www.delfi.lv/tasty/receptes/klasiskais-rasols.d?id=43626697', 0),"
                + "('Omlete ar sieru un desu', 'https://www.delfi.lv/tasty/receptes/omlete-ar-sieru-desu-un-pienu.d?id=43625701', 0),"
                + "('Easy zesty salmon burders', 'https://tasty.co/recipe/easy-zesty-salmon-burgers',1),"
                + "('Klasiskais brilē krēms', 'https://www.delfi.lv/tasty/receptes/klasiskais-brile-krems.d?id=50262015', 1)";
        db.execSQL(INSERT_INTO_RECIPES);*/

        String INSERT_INTO_RECIPE1 = "INSERT INTO " + TABLE_Recipes + "(RecipeTitle, RecipeURL, RecipeSelected)"
                + " VALUES ('Klasiskais Rasols','https://www.delfi.lv/tasty/receptes/klasiskais-rasols.d?id=43626697', 0)";
        db.execSQL(INSERT_INTO_RECIPE1);

        String INSERT_INTO_RECIPE2 = "INSERT INTO " + TABLE_Recipes + "(RecipeTitle, RecipeURL, RecipeSelected)"
                + " VALUES ('Omlete ar sieru un desu', 'https://www.delfi.lv/tasty/receptes/omlete-ar-sieru-desu-un-pienu.d?id=43625701', 0)";
        db.execSQL(INSERT_INTO_RECIPE2);

        String INSERT_INTO_RECIPE3 = "INSERT INTO " + TABLE_Recipes + "(RecipeTitle, RecipeURL, RecipeSelected)"
                + " VALUES ('Easy zesty salmon burders', 'https://tasty.co/recipe/easy-zesty-salmon-burgers',1)";
        db.execSQL(INSERT_INTO_RECIPE3);

        String INSERT_INTO_RECIPE4 = "INSERT INTO " + TABLE_Recipes + "(RecipeTitle, RecipeURL, RecipeSelected)"
                + " VALUES ('Klasiskais brilē krēms', 'https://www.delfi.lv/tasty/receptes/klasiskais-brile-krems.d?id=50262015', 1)";
        db.execSQL(INSERT_INTO_RECIPE4);

        String CREATE_TABLE_RECIPE_PRODUCTS = "CREATE TABLE " + TABLE_RecipeProducts + "("
                + KEY_RECIPE_PRODUCTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_RECIPE_PRODUCTS_RECIPE_ID + " INTEGER,"
                + KEY_RECIPE_PRODUCTS_PRODUCT_ID + " INTEGER,"
                + KEY_RECIPE_PRODUCTS_QUANTITY + " NUMERIC,"
                + "UNIQUE (" + KEY_RECIPE_PRODUCTS_RECIPE_ID + ", " + KEY_RECIPE_PRODUCTS_PRODUCT_ID + "))";
        db.execSQL(CREATE_TABLE_RECIPE_PRODUCTS);

        /*String INSERT_INTO_RECIPE_PRODUCTS = "INSERT INTO " + TABLE_RecipeProducts + "(RecipeID,ProductID,RecipeQuantity) "
                + "VALUES "
                + "(1,4,500),"
                + "(1,3,10),"
                + "(2,4,500),"
                + "(2,3,4),"
                + "(3,3,4),"
                + "(3,4,3)";
        db.execSQL(INSERT_INTO_RECIPE_PRODUCTS);*/

        String INSERT_INTO_RECIPE_PRODUCT1 = "INSERT INTO " + TABLE_RecipeProducts + "(RecipeID,ProductID,RecipeQuantity) "
                + "VALUES (1,4,500)";
        db.execSQL(INSERT_INTO_RECIPE_PRODUCT1);

        String INSERT_INTO_RECIPE_PRODUCT2 = "INSERT INTO " + TABLE_RecipeProducts + "(RecipeID,ProductID,RecipeQuantity) "
                + "VALUES (1,3,10)";
        db.execSQL(INSERT_INTO_RECIPE_PRODUCT2);

        String INSERT_INTO_RECIPE_PRODUCT3 = "INSERT INTO " + TABLE_RecipeProducts + "(RecipeID,ProductID,RecipeQuantity) "
                + "VALUES (2,4,500)";
        db.execSQL(INSERT_INTO_RECIPE_PRODUCT3);

        String INSERT_INTO_RECIPE_PRODUCT4 = "INSERT INTO " + TABLE_RecipeProducts + "(RecipeID,ProductID,RecipeQuantity) "
                + "VALUES (2,3,4)";
        db.execSQL(INSERT_INTO_RECIPE_PRODUCT4);

        String INSERT_INTO_RECIPE_PRODUCT5 = "INSERT INTO " + TABLE_RecipeProducts + "(RecipeID,ProductID,RecipeQuantity) "
                + "VALUES (3,3,4)";
        db.execSQL(INSERT_INTO_RECIPE_PRODUCT5);

        String INSERT_INTO_RECIPE_PRODUCT6 = "INSERT INTO " + TABLE_RecipeProducts + "(RecipeID,ProductID,RecipeQuantity) "
                + "VALUES (3,4,3)";
        db.execSQL(INSERT_INTO_RECIPE_PRODUCT6);

        String CREATE_VIEW_RECIPE_PRODUCTS = "CREATE VIEW " + VIEW_RecipeProducts + " AS SELECT "
                + " Products.ProductID,"
                + " Products.ProductName,"
                + " Products.Unit,"
                + " Products.InventoryQuantity,"
                + " RecipeProducts.RecipeQuantity,"
                + " CASE "
                + " WHEN (RecipeProducts.RecipeQuantity - Products.InventoryQuantity )<0 THEN 0"
                + " ELSE (RecipeProducts.RecipeQuantity - Products.InventoryQuantity )"
                + " END AS MissingProductAmount,"
                + " Recipes.RecipeID,"
                + " Recipes.RecipeTitle,"
                + " Recipes.RecipeURL"
                + " FROM Products"
                + " INNER JOIN RecipeProducts ON RecipeProducts.ProductID = Products.ProductID"
                + " INNER JOIN Recipes ON RecipeProducts.RecipeID=Recipes.RecipeID";
        db.execSQL(CREATE_VIEW_RECIPE_PRODUCTS);

        String CREATE_VIEW_RANKED_RECIPES="CREATE VIEW " + VIEW_RecipesRanked +" AS SELECT"
                + " Recipes.RecipeID,"
                + " Recipes.RecipeTitle,"
                + " Recipes.RecipeURL,"
                + " CASE"
                + " WHEN sum(v_RecipeProducts.MissingProductAmount) IS NULL THEN 1000000"
                + " ELSE sum(v_RecipeProducts.MissingProductAmount)"
                + " END as MissingProductAmount,"
                + " Recipes.RecipeSelected"
                + " FROM Recipes"
                + " LEFT JOIN v_RecipeProducts ON v_RecipeProducts.RecipeID = Recipes.RecipeID"
                + " group by Recipes.RecipeID"
                + " order by sum(v_RecipeProducts.MissingProductAmount) asc";
        db.execSQL(CREATE_VIEW_RANKED_RECIPES);

        String CREATE_VIEW_RECIPE_PRODUCTS_SELECTED = "CREATE VIEW " + VIEW_RecipeProductsSelected + " AS SELECT "
                + " Products.ProductID,"
                + " Products.ProductName,"
                + " Products.Unit,"
                + " Products.InventoryQuantity,"
                + " RecipeProducts.RecipeQuantity,"
                + " Recipes.RecipeSelected,"
                + " Recipes.RecipeID,"
                + " Recipes.RecipeTitle,"
                + " Recipes.RecipeURL"
                + " FROM Products"
                + " INNER JOIN RecipeProducts ON RecipeProducts.ProductID = Products.ProductID"
                + " INNER JOIN Recipes ON RecipeProducts.RecipeID=Recipes.RecipeID"
                + " WHERE Recipes.RecipeSelected=1";
        db.execSQL(CREATE_VIEW_RECIPE_PRODUCTS_SELECTED);

        String CREATE_VIEW_SHOPPING_LIST = "CREATE VIEW " + VIEW_ShoppingList + " AS SELECT "
                + " Products.ProductID,"
                + " Products.ProductName,"
                + " Products.Unit,"
                + " Products.InventoryQuantity,"
                + " RecipeProducts.RecipeQuantity,"
                + " Recipes.RecipeSelected,"
                + " Recipes.RecipeID,"
                + " Recipes.RecipeTitle,"
                + " Recipes.RecipeURL,"
                + " RecipeProducts.RecipeID,"
                + " SUM( Products.InventoryQuantity ),"
                + " SUM (RecipeProducts.RecipeQuantity),"
                + " CASE"
                + "     WHEN SUM(RecipeProducts.RecipeQuantity)-SUM( Products.InventoryQuantity )<0 THEN 0"
                + "     ELSE SUM(RecipeProducts.RecipeQuantity)-SUM( Products.InventoryQuantity )"
                + " END AS ShoppingAmount"
                + " FROM Products"
                + " INNER JOIN RecipeProducts ON RecipeProducts.ProductID = Products.ProductID"
                + " INNER JOIN Recipes ON RecipeProducts.RecipeID=Recipes.RecipeID"
                + " WHERE Recipes.RecipeSelected=1"
                + " GROUP BY Products.ProductName"
                + " ORDER BY SUM(RecipeProducts.RecipeQuantity)-SUM( Products.InventoryQuantity ) DESC";
        db.execSQL(CREATE_VIEW_SHOPPING_LIST);

        String CREATE_VIEW_RECIPES_LIST = "CREATE VIEW " + VIEW_RecipesList + " AS SELECT "
                + " Recipes.RecipeID,"
                + " Recipes.RecipeTitle,"
                + " Recipes.RecipeURL,"
                + " Recipes.RecipeSelected,"
                + " SUM(v_ShoppingList.ShoppingAmount)"
                + " FROM Recipes"
                + " Inner JOIN v_ShoppingList ON Recipes.RecipeID = v_ShoppingList.RecipeID"
                + " GROUP BY Recipes.RecipeTitle"
                + " ORDER BY SUM(v_ShoppingList.ShoppingAmount) ASC";
        db.execSQL(CREATE_VIEW_RECIPES_LIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP VIEW IF EXISTS " + VIEW_RecipesList);
        db.execSQL("DROP VIEW IF EXISTS " + VIEW_ShoppingList);
        db.execSQL("DROP VIEW IF EXISTS " + VIEW_RecipeProductsSelected);
        db.execSQL("DROP VIEW IF EXISTS " + VIEW_RecipeProducts);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RecipeProducts);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Recipes);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Products);
        onCreate(db);
    }


    public boolean insertProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PRODUCT_NAME, product.getProductName());
        contentValues.put(KEY_PRODUCT_UNIT, product.getUnit());
        contentValues.put(KEY_PRODUCT_INVENTORY_QUANTITY, product.getInventoryQuantity());

        if (product.getProteinAmount()==null){
            contentValues.put(KEY_PRODUCT_PROTEIN_AMOUNT, 0);
        }else contentValues.put(KEY_PRODUCT_PROTEIN_AMOUNT, product.getProteinAmount());

        if (product.getFatAmount()==null){
            contentValues.put(KEY_PRODUCT_FAT_AMOUNT, 0);
        }else contentValues.put(KEY_PRODUCT_FAT_AMOUNT, product.getFatAmount());

        if (product.getCarbohydrateAmount()==null){
            contentValues.put(KEY_PRODUCT_CARBS_AMOUNT, 0);
        }else contentValues.put(KEY_PRODUCT_CARBS_AMOUNT, product.getCarbohydrateAmount());

        if (product.getEnergy()==null){
            contentValues.put(KEY_PRODUCT_ENERGY, 0);
        }else contentValues.put(KEY_PRODUCT_ENERGY, product.getEnergy());

        if (product.getFiber()==null){
            contentValues.put(KEY_PRODUCT_FIBER, 0);
        }else contentValues.put(KEY_PRODUCT_FIBER, product.getFiber());

        if (product.getSugars()==null){
            contentValues.put(KEY_PRODUCT_SUGARS, 0);
        }else contentValues.put(KEY_PRODUCT_SUGARS, product.getFiber());

        long result = db.insert(TABLE_Products, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PRODUCT_NAME, product.getProductName());
        contentValues.put(KEY_PRODUCT_UNIT, product.getUnit());
        contentValues.put(KEY_PRODUCT_INVENTORY_QUANTITY, product.getInventoryQuantity());
        contentValues.put(KEY_PRODUCT_PROTEIN_AMOUNT, product.getProteinAmount());
        contentValues.put(KEY_PRODUCT_FAT_AMOUNT, product.getFatAmount());
        contentValues.put(KEY_PRODUCT_CARBS_AMOUNT, product.getCarbohydrateAmount());
        contentValues.put(KEY_PRODUCT_ENERGY, product.getEnergy());
        contentValues.put(KEY_PRODUCT_FIBER, product.getFiber());
        contentValues.put(KEY_PRODUCT_SUGARS, product.getSugars());
        String whereClause = "ProductID=?";
        String whereArgs[] = {Integer.toString(product.getId())};
        long result = db.update(TABLE_Products, contentValues, whereClause, whereArgs);

        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "ProductID=?";
        String whereArgs[] = {Integer.toString(product.getId())};
        long result = db.delete(TABLE_Products, whereClause, whereArgs);

        if (result == -1)
            return false;
        else
            return true;

    }
    public boolean deleteProduct(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("Products", "ProductID ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }

    public ArrayList<Product> readAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String orderByClause=KEY_PRODUCT_INVENTORY_QUANTITY+" DESC";
        Cursor cursor = db.query(TABLE_Products,null,null,null,null,null,orderByClause);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Product product = readProduct(cursor);
                products.add(product);
            }
        }
        return products;
    }

    private Product readProduct(Cursor cursor) {
        Product product = new Product();
        product.setId(cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_ID)));
        product.setProductName(cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_NAME)));
        product.setUnit(cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_UNIT)));
        product.setInventoryQuantity(cursor.getDouble(cursor.getColumnIndex(KEY_PRODUCT_INVENTORY_QUANTITY)));
        product.setProteinAmount(cursor.getDouble(cursor.getColumnIndex(KEY_PRODUCT_PROTEIN_AMOUNT)));
        product.setFatAmount(cursor.getDouble(cursor.getColumnIndex(KEY_PRODUCT_FAT_AMOUNT)));
        product.setCarbohydrateAmount(cursor.getDouble(cursor.getColumnIndex(KEY_PRODUCT_CARBS_AMOUNT)));
        product.setEnergy(cursor.getDouble(cursor.getColumnIndex(KEY_PRODUCT_ENERGY)));
        product.setFiber(cursor.getDouble(cursor.getColumnIndex(KEY_PRODUCT_FIBER)));
        product.setSugars(cursor.getDouble(cursor.getColumnIndex(KEY_PRODUCT_SUGARS)));
        return product;
    }

    public Product readProduct(int productID) {  // ADDED form TableController

        Product product = null;

        String sql = "SELECT * FROM Products WHERE ProductID = " + productID;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_ID)));
            String productName = cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_NAME));
            Double productQuantity = Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_INVENTORY_QUANTITY)));
            String productUnit = cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_UNIT));
            Double proteinAmount = Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_PROTEIN_AMOUNT)));
            Double fatAmount = Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_FAT_AMOUNT)));
            Double carbsAmount = Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_CARBS_AMOUNT)));
            Double energy = Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_ENERGY)));
            Double fiber = Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_FIBER)));
            Double sugars = Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_SUGARS)));

            product = new Product();
            product.setId(id);
            product.setProductName(productName);
            product.setInventoryQuantity(productQuantity);
            product.setUnit(productUnit);
            product.setProteinAmount(proteinAmount);
            product.setFatAmount(fatAmount);
            product.setCarbohydrateAmount(carbsAmount);
            product.setEnergy(energy);
            product.setFiber(fiber);
            product.setSugars(sugars);
        }

        return product;
    }


    public ArrayList<Recipe> readAllRecipes() {
        ArrayList<Recipe> recipes= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
       // String orderBy = "MissingProductAmount asc";
        Cursor cursor = db.query(TABLE_Recipes,null, null, null,null,null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Recipe recipe = readRecipe(cursor);
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    private Recipe readRecipe(Cursor cursor) {
        Recipe recipe = new Recipe();
        recipe.setId(cursor.getInt(cursor.getColumnIndex("RecipeID")));
        recipe.setTitle(cursor.getString(cursor.getColumnIndex("RecipeTitle")));
        recipe.setRecipeURL(cursor.getString(cursor.getColumnIndex("RecipeURL")));
        //boolean selectedBoolean=Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(KEY_RECIPE_SELECTED)));
        //recipe.getSelected(cursor.getString(cursor.getColumnIndex(KEY_RECIPE_SELECTED)));
        boolean b = cursor.getInt(cursor.getColumnIndex("RecipeSelected"))>0;
        if(b){
           recipe.setSelected(true);
        }else
            recipe.setSelected(false);
       // recipe.setSelected(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("RecipeSelected"))));
        return recipe;
    }

    public Recipe readRecipe(int recipeID) {  // ADDED form TableController

        Recipe recipe = null;

        String sql = "SELECT * FROM Recipes WHERE RecipeID = " + recipeID;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("RecipeID")));
            String recipeTitle = cursor.getString(cursor.getColumnIndex("RecipeTitle"));
            String recipeURL = cursor.getString(cursor.getColumnIndex("RecipeURL"));

            recipe = new Recipe();
            recipe.setId(id);
            recipe.setTitle(recipeTitle);
            recipe.setRecipeURL(recipeURL);
            boolean b = cursor.getInt(cursor.getColumnIndex(KEY_RECIPE_SELECTED))>0;
            if(b){
                recipe.setSelected(true);
            }else
                recipe.setSelected(false);


        }
        cursor.close();
        db.close();
        return recipe;
    }

    public boolean insertRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_RECIPE_TITLE, recipe.getTitle());
        contentValues.put(KEY_RECIPE_URL, recipe.getRecipeURL());
        contentValues.put(KEY_RECIPE_SELECTED, recipe.getSelected());

        long result = db.insert(TABLE_Recipes, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_RECIPE_TITLE, recipe.getTitle());
        contentValues.put(KEY_RECIPE_URL, recipe.getRecipeURL());
        contentValues.put(KEY_RECIPE_SELECTED, recipe.getSelected());
        String whereClause = "RecipeID=?";
        String whereArgs[] = {Integer.toString(recipe.getId())};
        long result = db.update(TABLE_Recipes, contentValues, whereClause, whereArgs);

        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean deleteRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "RecipeID=?";
        String whereArgs[] = {Integer.toString(recipe.getId())};
        long result = db.delete(TABLE_Recipes, whereClause, whereArgs);

        if (result == -1)
            return false;
        else
            return true;

    }
    public boolean deleteRecipe(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("Recipes", "RecipeID ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }

    public ArrayList<Recipe> readSelectedRecipes() {
        ArrayList<Recipe> selectedRecipes= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + VIEW_RecipesList, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Recipe recipe = readSelectedRecipe(cursor);
                selectedRecipes.add(recipe);
            }
        }
        return selectedRecipes;
    }

    private Recipe readSelectedRecipe(Cursor cursor) {
        Recipe recipe = new Recipe();
        recipe.setId(cursor.getInt(cursor.getColumnIndex("Recipes.RecipeID")));
        recipe.setTitle(cursor.getString(cursor.getColumnIndex("Recipes.RecipeTitle")));
        recipe.setRecipeURL(cursor.getString(cursor.getColumnIndex("Recipes.RecipeURL")));
        boolean selectedBoolean=Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("Recipes.RecipeSelected")));
        recipe.setSelected(selectedBoolean);
        recipe.setShoppingAmount(cursor.getDouble(cursor.getColumnIndex("SUM(v_ShoppingList.ShoppingAmount)")));
        return recipe;
    }

    public ArrayList<String> readShoppingList() {
        ArrayList<String> shoppingList= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + VIEW_ShoppingList, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Product product = readShoppingListProduct(cursor);
                shoppingList.add(product.toShoppingList());
            }
        }
        return shoppingList;
    }

    private Product readShoppingListProduct(Cursor cursor) {
        Product product = new Product();
        product.setId(cursor.getInt(cursor.getColumnIndex("Products.ProductID")));
        product.setProductName(cursor.getString(cursor.getColumnIndex("Products.ProductName")));
        product.setUnit(cursor.getString(cursor.getColumnIndex("Products.Unit")));
        product.setInventoryQuantity(cursor.getDouble(cursor.getColumnIndex("Products.InventoryQuantity")));
        product.setShoppingAmount(cursor.getDouble(cursor.getColumnIndex("ShoppingAmount")));
        return product;
    }

    public ArrayList<Product> readRecipeProducts(Recipe recipe) {
        ArrayList<Product> recipeProducts= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String whereClause = "RecipeID=?";
        String whereArgs[] = {Integer.toString(recipe.getId())};
        Cursor cursor = db.query(VIEW_RecipeProducts,null,whereClause,whereArgs,null,null,null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Product recipeProduct = readRecipeProducts(cursor);
                recipeProducts.add(recipeProduct);
            }
        }
        return recipeProducts;
    }

    private Product readRecipeProducts(Cursor cursor) {
        Product recipeProduct = new Product();
        recipeProduct.setId(cursor.getInt(cursor.getColumnIndex("ProductID")));
        recipeProduct.setProductName(cursor.getString(cursor.getColumnIndex("ProductName")));
        recipeProduct.setUnit(cursor.getString(cursor.getColumnIndex("Unit")));
        recipeProduct.setInventoryQuantity(cursor.getDouble(cursor.getColumnIndex("InventoryQuantity")));
        recipeProduct.setRecipeQuantity(cursor.getDouble(cursor.getColumnIndex("RecipeQuantity")));
        return recipeProduct;
    }

    public boolean insertRecipeProducts(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result=0;
        long newResult=0;
        ArrayList<Product> recipeProducts=recipe.getRecipeProducts();
        for (Product product:recipeProducts){
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_RECIPE_PRODUCTS_RECIPE_ID, recipe.getId());
            contentValues.put(KEY_RECIPE_PRODUCTS_PRODUCT_ID, product.getId());
            contentValues.put(KEY_RECIPE_PRODUCTS_QUANTITY, product.getRecipeQuantity());

            newResult=db.insert(TABLE_RecipeProducts, null, contentValues);

            result=result+newResult;
        }

        if (result <=0)
            return false;
        else
            return true;
    }

    public boolean updateRecipeProducts(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result=0;
        long newResult=0;
        ArrayList<Product> recipeProducts=recipe.getRecipeProducts();
        for (Product product:recipeProducts){
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_RECIPE_PRODUCTS_RECIPE_ID, recipe.getId());
            contentValues.put(KEY_RECIPE_PRODUCTS_PRODUCT_ID, product.getId());
            contentValues.put(KEY_RECIPE_PRODUCTS_QUANTITY, product.getRecipeQuantity());
            String whereClause = "RecipeID=? AND ProductID=?";
            String whereArgs[] = {Integer.toString(recipe.getId()),Integer.toString(product.getId())};

            newResult=db.update(TABLE_RecipeProducts, contentValues, whereClause, whereArgs);
            result=result+newResult;
        }

        if (result <=0)
            return false;
        else
            return true;
    }

    public boolean deleteRecipeProducts(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result=0;
        long newResult=0;
        ArrayList<Product> recipeProducts=recipe.getRecipeProducts();
        for (Product product:recipeProducts){
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_RECIPE_PRODUCTS_RECIPE_ID, recipe.getId());
            contentValues.put(KEY_RECIPE_PRODUCTS_PRODUCT_ID, product.getId());
            contentValues.put(KEY_RECIPE_PRODUCTS_QUANTITY, product.getRecipeQuantity());
            String whereClause = "RecipeID=? AND ProductID=?";
            String whereArgs[] = {Integer.toString(recipe.getId()),Integer.toString(product.getId())};

            newResult=db.delete(TABLE_RecipeProducts, whereClause, whereArgs);
            result=result+newResult;
        }

        if (result <=0)
            return false;
        else
            return true;
    }
    public boolean isProductInDB(String productName) {  // ADDED form TableController
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM Products WHERE ProductName = " + productName;

        String whereClause = "ProductName=?";
        String whereArgs[] = {productName};
        Cursor cursor = db.query(TABLE_Products,null,whereClause,whereArgs,null,null,null);

        if (cursor.moveToNext()) {

            return true;
        }else  return false;
    }
    public Product readProductString(String productName) {  // ADDED form TableController

        Product product = null;
        String sql = "SELECT * FROM Products WHERE ProductName = " + productName;
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "ProductName=?";
        String whereArgs[] = {productName};
        Cursor cursor = db.query(TABLE_Products,null, whereClause, whereArgs,null,null,null);
        // Cursor cursor = db.rawQuery(sql, null);
        Log.println(Log.ASSERT, cursor.toString(), "cursor in readProductString ...");
        if (cursor.moveToFirst()) {

            Log.println(Log.ASSERT, String.valueOf(cursor.moveToFirst()), "cursor.moveToFirst ...");
            Log.println(Log.ASSERT, String.valueOf(cursor.getColumnIndex("ProductID")), "getColumnIndex ID cursor.moveToFirst ...");
            Log.println(Log.ASSERT, String.valueOf(cursor.getColumnIndex("Unit")), "getColumnIndex Unit cursor.moveToFirst ...");
            Log.println(Log.ASSERT, String.valueOf(cursor.getColumnIndex("InventoryQuantity")), "getColumnIndex InvQ cursor.moveToFirst ...");

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ProductID")));
            Log.println(Log.ASSERT, cursor.getString(cursor.getColumnIndex("ProductID")), "getString ID cursor.moveToFirst ...");

            //Double productQuantity = Double.parseDouble(cursor.getString(cursor.getColumnIndex("InventoryQuantity")));
            String productQuantity = String.valueOf(cursor.getString(cursor.getColumnIndex("InventoryQuantity")));
            Log.println(Log.ASSERT, productQuantity, "product Quantity in readProductString cursor.movetofirst ...");

            String productUnit = cursor.getString(cursor.getColumnIndex("Unit"));
            //Log.println(Log.ASSERT, cursor.getString(cursor.getColumnIndex("Unit")), "getString Unit cursor.moveToFirst ...");
            Log.println(Log.ASSERT, productUnit, "product Unit in readProductString cursor.movetofirst ...");

            product = new Product();
            product.setId(id);
            product.setProductName(productName);
            product.setUnit(productUnit);
            Log.println(Log.ASSERT, String.valueOf(product), " product before setInvQ ...");
            //product.setInventoryQuantity(Double.parseDouble(productQuantity));
            Log.println(Log.ASSERT, String.valueOf(product), "end product in readProductString cursor.movetofirst ...");
        }
        cursor.close();
        Log.println(Log.ASSERT, String.valueOf(product), "returnable product in readProductString ...");
        db.close();
        return product;
    }


    public boolean insertRecipeProducts(int recipeId, int productId, Double recipeQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result=0;
        long newResult=0;

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_RECIPE_PRODUCTS_RECIPE_ID, recipeId);
        contentValues.put(KEY_RECIPE_PRODUCTS_PRODUCT_ID, productId);
        contentValues.put(KEY_RECIPE_PRODUCTS_QUANTITY, recipeQuantity);

        newResult=db.insert(TABLE_RecipeProducts, null, contentValues);

        result=result+newResult;


        if (result <=0)
            return false;
        else
            return true;
    }
    public boolean deleteRecipeProducts(int recipeID, int productID) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result=0;
        long newResult=0;

        String whereClause = "RecipeID=? AND ProductID=?";
        String whereArgs[] = {Integer.toString(recipeID),Integer.toString(productID)};

        newResult=db.delete(TABLE_RecipeProducts, whereClause, whereArgs);
        result=result+newResult;

        if (result <=0)
            return false;
        else
            return true;
    }

}


