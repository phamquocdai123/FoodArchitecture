package com.example.lenovo.demofoodarchitecture;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="EatIt.db";
    private static final int DB_VER=1;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public ArrayList<OrderModel> getCarts(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"ProductId,ProductName,Quantity,Price,Discount"};
        String sqlTable="OrderDetail";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        ArrayList<OrderModel> result = new ArrayList<>();
        if(c.moveToFirst()){
            do{

                FoodModel foodModel = new FoodModel(Integer.valueOf(c.getString(c.getColumnIndex("ProductId"))),
                        c.getString(c.getColumnIndex("ProductName")),
                        Double.valueOf( c.getString(c.getColumnIndex("Price"))),
                        Integer.valueOf(  c.getString(c.getColumnIndex("Discount"))));

                String quantity =  c.getString(c.getColumnIndex("Quantity"));
                OrderModel orderModel = new OrderModel(foodModel,quantity);
                result.add(orderModel);


            }while (c.moveToNext());
        }
        return result;
    }
    public void addToCart(OrderModel order){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format(
                "INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Discount) " +
                        "VALUES('%s','%s','%s','%s','%s');",
                order.getFoodModel().getId(),
                order.getFoodModel().getName(),
                order.getQuantity(),
                order.getFoodModel().getPrice(),
                order.getFoodModel().getDiscount());
        db.execSQL(query);
    }
    public void cleanCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }

    public void addToFavorites(String foodId){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO Favorites(FoodId) VALUES('%s');",foodId);
        db.execSQL(query);
    }
    public void  removeFromFavorites(String foodId){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM Favorites WHERE FoodId = '%s'",foodId);
        db.execSQL(query);
    }
    public boolean isFavorites(String foodId){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM Favorites WHERE FoodId = '%s'",foodId);
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()<=0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
