package com.master.jorge.lionapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.master.jorge.lionapp.R;
import com.master.jorge.lionapp.RegisterActivity;
import com.master.jorge.lionapp.model.Data;
import com.master.jorge.lionapp.model.Good;

import cz.msebera.android.httpclient.Header;

public class GoodsActivity extends AppCompatActivity {
    SharedPreferences settings;
    final Gson gson = new Gson();
    Good goods[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        setGoodsList();
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
                ListView goodsLV = (ListView) findViewById(R.id.goodsLV);
                goods = gson.fromJson(new String(responseBody), Good[].class);
                ArrayAdapter<Good> adapter = new ArrayAdapter<Good>(GoodsActivity.this, android.R.layout.simple_list_item_1, goods);
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

    private void logout() {
        settings.edit().remove("client");
        settings.edit().remove("access-token");
        settings.edit().remove("uid");
        settings.edit().commit();

        Toast.makeText(GoodsActivity.this, "Logout Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(GoodsActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
