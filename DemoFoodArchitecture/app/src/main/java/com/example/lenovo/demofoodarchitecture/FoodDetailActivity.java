package com.example.lenovo.demofoodarchitecture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.rey.material.widget.FloatingActionButton;

public class FoodDetailActivity extends AppCompatActivity {

    private FoodModel foodModel;
    private TextView txtfoodname,txtdiscount,txtPrice;
    private ElegantNumberButton numberButtonQuantity;
    private ImageView imvHeader;
    private Toolbar toolbar;
    android.support.design.widget.FloatingActionButton floatingActionButtonAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        Mapping();

        //getIntent
        foodModel = new FoodModel();
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            foodModel = extras.getParcelable("activityModel");
        }
        if(foodModel !=null){
            txtfoodname.setText(foodModel.getName());
            String strDouble = String.format("%.0f", foodModel.getPrice());
            txtPrice.setText(strDouble + "");

            txtdiscount.setText("Discount: "+ String.valueOf(foodModel.getDiscount())+ "%");
            Glide.with(getBaseContext())
                    .asBitmap()
                    .load(foodModel.getImage())
                    .into(imvHeader);
        }
        floatingActionButtonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                new Database(getBaseContext()).addToCart(new OrderModel(
                        foodModel,numberButtonQuantity.getNumber()));
            }
        });




    }
    private void Mapping(){
        txtfoodname = (TextView) findViewById(R.id.txtfoodname);
        txtdiscount = (TextView) findViewById(R.id.txtsaleoff);
        txtPrice = (TextView) findViewById(R.id.txtfoodprice);
        numberButtonQuantity = (ElegantNumberButton) findViewById(R.id.number_button);
        imvHeader = (ImageView) findViewById(R.id.img_food);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        floatingActionButtonAddToCart = (android.support.design.widget.FloatingActionButton)
                findViewById(R.id.btnCart);


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }//onOptionItemSelected



}
