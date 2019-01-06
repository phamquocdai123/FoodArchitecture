package com.example.lenovo.demofoodarchitecture;

import com.example.lenovo.demofoodarchitecture.strategy.discount;

import java.util.ArrayList;

public class CartOder {
    public ArrayList<OrderModel> orders;

    public CartOder() {
    }

    public CartOder(ArrayList<OrderModel> orders) {
        this.orders = orders;
    }

    public double TinhTien(discount d) {
        return d.TinhTien(orders);
    }
}

