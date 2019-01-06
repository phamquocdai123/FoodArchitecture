package com.example.lenovo.demofoodarchitecture.strategy;

import com.example.lenovo.demofoodarchitecture.FoodModel;
import com.example.lenovo.demofoodarchitecture.OrderModel;

import java.util.ArrayList;

public interface discount {
    double TinhTien(ArrayList<OrderModel> foodModelArrayList);
}
