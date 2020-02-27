package com.example.myapplication.entities;


public class Product {

    int id;
    String productName;
    Double inventoryQuantity;
    String unit;
    Double shoppingAmount;
    int recipeID;
    Double recipeQuantity;
    Double ProteinAmount;
    Double FatAmount;
    Double CarbohydrateAmount;
    Double Energy;
    Double Sugars;
    Double Fiber;

    public Double getProteinAmount() {
        return ProteinAmount;
    }

    public void setProteinAmount(Double proteinAmount) {
        ProteinAmount = proteinAmount;
    }

    public Double getFatAmount() {
        return FatAmount;
    }

    public void setFatAmount(Double fatAmount) {
        FatAmount = fatAmount;
    }

    public Double getCarbohydrateAmount() {
        return CarbohydrateAmount;
    }

    public void setCarbohydrateAmount(Double carbohydrateAmount) {
        CarbohydrateAmount = carbohydrateAmount;
    }

    public Double getEnergy() {
        return Energy;
    }

    public void setEnergy(Double energy) {
        Energy = energy;
    }

    public Double getSugars() {
        return Sugars;
    }

    public void setSugars(Double sugars) {
        Sugars = sugars;
    }

    public Double getFiber() {
        return Fiber;
    }

    public void setFiber(Double fiber) {
        Fiber = fiber;
    }



    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public Double getRecipeQuantity() {
        return recipeQuantity;
    }

    public void setRecipeQuantity(Double recipeQuantity) {
        this.recipeQuantity = recipeQuantity;
    }

    public Double getShoppingAmount() {
        return shoppingAmount;
    }

    public void setShoppingAmount(Double shoppingAmount) {
        this.shoppingAmount = shoppingAmount;
    }

    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(Double inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
/*
    @Override
    public String toString() {
        return  productName + " " + inventoryQuantity + " "+ unit;
    }

    public String toShoppingList() {
        return  productName + " " + shoppingAmount + " "+ unit;
    }*/

    public String toProductNutrients(){
        return "Energy: " + Energy +
                "; Protein: " + ProteinAmount +
                "; Fats: " + FatAmount +
                "; Sugars: " + Sugars +
                "; Carbohydrates:"  + CarbohydrateAmount +
                "; Fiber: " + Fiber + ";";
    }

    @Override
    public String toString() {
        return  productName + " " + inventoryQuantity + " "+ unit;
    }

    public String toShoppingList() {
        return  productName + " " + shoppingAmount + " "+ unit;
    }

    public String toId() {
        return  String.valueOf(id);
    }
    public String toStringRecipeQuantity() {
        return  productName + " " + recipeQuantity + " "+ unit;
    }
}
