package com.example.lenovo.demofoodarchitecture.strategy;

import com.example.lenovo.demofoodarchitecture.FoodModel;
import com.example.lenovo.demofoodarchitecture.OrderModel;

import java.util.ArrayList;

public class Noel implements discount {

    public double myDiscount ;

    public Noel(double myDiscount) {
        this.myDiscount = myDiscount;
    }

    @Override
    public double TinhTien(ArrayList<OrderModel> orderModelArrayList) {
        double total =0;
        for(OrderModel order:orderModelArrayList)
            total+=(order.getFoodModel().getPrice())*(Double.parseDouble(order.getQuantity()));
        return  total - (total*myDiscount);
    }
}
