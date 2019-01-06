package com.example.lenovo.demofoodarchitecture;

public class OrderModel {
    FoodModel foodModel;
    String quantity;

    public OrderModel(FoodModel foodModel, String quantity) {
        this.foodModel = foodModel;
        this.quantity = quantity;
    }

    public OrderModel() {

    }

    public FoodModel getFoodModel() {
        return foodModel;
    }

    public void setFoodModel(FoodModel foodModel) {
        this.foodModel = foodModel;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
