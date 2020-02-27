package com.example.myapplication.entities;

import java.util.ArrayList;

public class Recipe {

    int id;
    String title;
    String recipeURL;
    Boolean selected;
    double shoppingAmount;
    ArrayList<Product> recipeProducts;

    public ArrayList<Product> getRecipeProducts() {
        return recipeProducts;
    }

    public void setRecipeProducts(ArrayList<Product> recipeProducts) {
        this.recipeProducts = recipeProducts;
    }

    public double getShoppingAmount() {
        return shoppingAmount;
    }

    public void setShoppingAmount(double shoppingAmount) {
        this.shoppingAmount = shoppingAmount;
    }

    public Recipe(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecipeURL() {
        return recipeURL;
    }

    public void setRecipeURL(String recipeURL) {
        this.recipeURL = recipeURL;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    @Override
    public String toString() {
        return  title + " ";
    }
}

