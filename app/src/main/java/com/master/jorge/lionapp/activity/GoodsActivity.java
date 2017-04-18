package com.master.jorge.lionapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.master.jorge.lionapp.R;
import com.master.jorge.lionapp.model.Good;

import cz.msebera.android.httpclient.Header;

public class GoodsActivity extends AppCompatActivity {
    SharedPreferences settings;
    final Gson gson = new Gson();
    Good goods[];
    ListView goodsLV;
    ArrayAdapter<Good> adapter;
    FloatingActionButton buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        goodsLV = (ListView) findViewById(R.id.goodsLV);
        setGoodsList();

        goodsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("HelloListView", "You clicked Item: " + i + " at position:" + l);
                Log.i("Array","Good: " + goods[i].getName());

                Intent intent = new Intent(GoodsActivity.this, GoodDetailsActivity.class);
                intent.putExtra("name", goods[i].getName());
                intent.putExtra("category", goods[i].getCategory());
                intent.putExtra("value", String.valueOf(goods[i].getValue()));
                intent.putExtra("id", String.valueOf(goods[i].getId()));
                startActivity(intent);
            }
        });

        buttonAdd = (FloatingActionButton) findViewById(R.id.button_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGood();
            }
        });
    }

    public void setGoodsList(){
        settings = getSharedPreferences("HEADER", 0);
        RequestParams rParams = new RequestParams();
        rParams.put("uid", settings.getString("uid", ""));
        rParams.put("client", settings.getString("client", ""));
        rParams.put("access-token", settings.getString("access-token", ""));

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(getString(R.string.url) + "goods/list", rParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Result","SUCCESS");
                Log.d("Result",String.valueOf(new String(responseBody)));
                goods = gson.fromJson(new String(responseBody), Good[].class);
                adapter = new ArrayAdapter<Good>(GoodsActivity.this, android.R.layout.simple_list_item_1, goods);
                goodsLV.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Result","FAILED");
                Log.d("Result",String.valueOf(statusCode));

                Toast.makeText(GoodsActivity.this, "Session expired", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GoodsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.goods, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logout() {
        settings.edit().remove("client");
        settings.edit().remove("access-token");
        settings.edit().remove("uid");
        settings.edit().commit();

        Toast.makeText(GoodsActivity.this, "Logout Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(GoodsActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void addGood(){
        Intent intent = new Intent(GoodsActivity.this, AddGoodActivity.class);
        startActivity(intent);
    }
}
