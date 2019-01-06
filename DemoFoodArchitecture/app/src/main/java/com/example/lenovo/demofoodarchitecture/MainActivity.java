package com.example.lenovo.demofoodarchitecture;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<FoodModel> foodModelArrayList;
    ArrayList<FoodModel> foodModelArrayList2 = new ArrayList<>();
    RecyclerViewFoodListAdapter myAdapter;

    public static String myLink = "https://phamquocdai126.000webhostapp.com/ArchitectureWebService/middleware.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //LoadData();
        //Mapping();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewFood);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(mGridLayoutManager);
        myAdapter = new RecyclerViewFoodListAdapter(MainActivity.this,
                foodModelArrayList2);
        recyclerView.setAdapter(myAdapter);

        //GetDataFromWebservice("http://192.168.137.1/ArchitectureWebService/getdata.php?accesstoken=123456");
        GetDataFromWebservice(myLink+"?accesstoken=123456");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCartHome);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCart = new Intent(MainActivity.this,CartActivity.class);
                startActivity(intentCart);
            }
        });
    }

    private void Mapping(){

    }



    private void GetDataFromWebservice(String myUrl){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                myUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                foodModelArrayList2.clear();
                for (int i =0 ;i <response.length();i++){
                    try {
                        JSONObject object =response.getJSONObject(i);
                        foodModelArrayList2.add(new FoodModel(
                                object.getInt("id"),
                                object.getString("name"),
                                object.getDouble("price"),
                                object.getString("image"),
                                object.getInt("discount")));
                        Log.d("cu", "onResponse: "+ foodModelArrayList2.size()+ foodModelArrayList2.get(i).getId()+
                            foodModelArrayList2.get(i).getPrice()+ foodModelArrayList2.get(i).getImage());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                myAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Get failed!", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }


    private void UpdateFoodToDatabase(String myURL){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, myURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().toString().equals("success")){
                    Toast.makeText(MainActivity.this, "Update successfully!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "update failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Update error!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

               Map<String,String> params = new HashMap<>();
                params.put("id","2");
                params.put("name","Miến Bò");
                params.put("price","35000");
                params.put("image","https://phamquocdai126.000webhostapp.com/QQ-Tr%C3%A0-xanh-chanh-d%C3%A2y-1.png");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void DeleteFoodFromDatabase(String myURL){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, myURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().toString().equals("success")){
                    Toast.makeText(MainActivity.this, "Delete successfully!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Delete error!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id","11");
               return params;
            }
        };
        requestQueue.add(stringRequest);


    }




}
