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
    ArrayList<FoodModel> foodModelArrayList = new ArrayList<>();
    RecyclerViewFoodListAdapter myAdapter;

    public static String myLink = "https://phamquocdai126.000webhostapp.com/ArchitectureWebService/middleware.php?accesstoken=123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewFood);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(mGridLayoutManager);
        myAdapter = new RecyclerViewFoodListAdapter(MainActivity.this, foodModelArrayList);
        recyclerView.setAdapter(myAdapter);

        //GetDataFromWebservice("http://192.168.137.1/ArchitectureWebService/getdata.php?accesstoken=123456");
        GetDataFromWebservice(myLink+"&type=1");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCartHome);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCart = new Intent(MainActivity.this,CartActivity.class);
                startActivity(intentCart);
            }
        });
    }

    private void GetDataFromWebservice(String myUrl){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                myUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                foodModelArrayList.clear();
                for (int i =0 ;i <response.length();i++){
                    try {
                        JSONObject object =response.getJSONObject(i);
                        foodModelArrayList.add(new FoodModel(
                                object.getInt("id"),
                                object.getString("name"),
                                object.getDouble("price"),
                                object.getString("image"),
                                object.getInt("discount")));
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




}
