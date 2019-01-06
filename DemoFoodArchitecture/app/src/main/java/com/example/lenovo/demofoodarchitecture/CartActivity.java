package com.example.lenovo.demofoodarchitecture;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.demofoodarchitecture.strategy.Noel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.lenovo.demofoodarchitecture.MainActivity.myLink;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    TextView txtTotalPrice;
    Button btnPlace;
    CartAdapter adapter;
    ArrayList<OrderModel> cart = new ArrayList<>();
    int IdOrder,QuantityOrder;
    private double myDiscountNoel= 0.1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
//        database = FirebaseDatabase.getInstance();
//        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice =  findViewById(R.id.total);
        btnPlace = findViewById(R.id.btnPlaceOrder);
        loadListDocument();
        //GetIdLastOrderItem(myLink+"https://phamquocdai126.000webhostapp.com/ArchitectureWebService/getidlastorderitem.php");
        GetIdLastOrderItem(myLink+"?accesstoken=2202");
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cart.size()>0)
                    showAlertDialog();
                else
                    Toast.makeText(CartActivity.this, "Giỏ hàng của bạn trống!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
        alertDialog.setTitle("Đặt Hàng");
        alertDialog.setMessage("Nhập vào địa chỉ của bạn:  ");
        final EditText edtAddress = new EditText(CartActivity.this);
        LinearLayout.LayoutParams lp =new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.shoppingcart);
        alertDialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // cart
                IdOrder++;
                if(IdOrder == 0){
                    IdOrder = 1;
                }
                //AddNewOrderToDatabase("https://phamquocdai126.000webhostapp.com/ArchitectureWebService/addnewmyorderlist.php");
                AddNewOrderToDatabase(myLink+"?accesstoken=1997");
                for (int i2 = 0; i2 < cart.size(); i2++){
                    AddNewOrderDetailToDatabase(myLink+"?accesstoken=2019",
                            cart.get(i2));
                }
                new Database(getBaseContext()).cleanCart();
                //Toast.makeText(CartActivity.this, ""+IdOrder, Toast.LENGTH_SHORT).show();
                Toast.makeText(CartActivity.this,"Cảm ơn bạn đã đặt hàng!",Toast.LENGTH_LONG).show();

                finish();
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();

    }

    private void GetIdLastOrderItem(String myUrl){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                myUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //IdOrder = 0; QuantityOrder = 0;
                for (int i =0 ;i <response.length();i++){
                    try {
                        JSONObject object =response.getJSONObject(i);
                        IdOrder = object.getInt("id");
                        QuantityOrder = object.getInt("tablenumber");
                       // Toast.makeText(CartActivity.this, ""+IdOrder, Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CartActivity.this, "Get failed!", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }


    private void AddNewOrderDetailToDatabase(String myURl, final OrderModel orderModel){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, myURl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //foodModelArrayList2.clear();
                        if (response.trim().equals("success")){
                           // Toast.makeText(CartActivity.this, "Add Successfully!", Toast.LENGTH_SHORT).show();
                        }else{
                            //Toast.makeText(CartActivity.this, "Add failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CartActivity.this, "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",String.valueOf(IdOrder));
                params.put("idfood",String.valueOf(orderModel.getFoodModel().getId()));
                params.put("quantity",String.valueOf(orderModel.getQuantity()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AddNewOrderToDatabase(String myURl){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, myURl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //foodModelArrayList2.clear();
                        if (response.trim().equals("success")){
                            Toast.makeText(CartActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(CartActivity.this, "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CartActivity.this, "Không thể đặt hàng!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",String.valueOf(IdOrder));
                params.put("tablenumber",String.valueOf(cart.size()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void loadListDocument() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this,myDiscountNoel);

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        double total =0;
        CartOder cartOder = new CartOder(cart);
        total = cartOder.TinhTien(new Noel(myDiscountNoel));
        txtTotalPrice.setText(""+total);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());
        return true;

    }

    private void deleteCart(int order) {
        cart.remove(order);
        new Database(this).cleanCart();
        for (OrderModel item:cart)
            new Database(this).addToCart(item);
        loadListDocument();
    }
}
