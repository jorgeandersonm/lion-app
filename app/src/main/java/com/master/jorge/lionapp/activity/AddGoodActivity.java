package com.master.jorge.lionapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.master.jorge.lionapp.R;

import cz.msebera.android.httpclient.Header;

public class AddGoodActivity extends AppCompatActivity {
    Button btnAdd;
    EditText name, category, value;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_good);

        name = (EditText) findViewById(R.id.input_name_good);
        category = (EditText) findViewById(R.id.input_category_good);
        value = (EditText) findViewById(R.id.input_value_good);

        btnAdd = (Button) findViewById(R.id.btn_add_good);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGood();
            }
        });
    }

    private void addGood() {
        settings = getSharedPreferences("HEADER", 0);
        RequestParams rParams = new RequestParams();
        rParams.put("uid", settings.getString("uid", ""));
        rParams.put("client", settings.getString("client", ""));
        rParams.put("access-token", settings.getString("access-token", ""));
        rParams.put("name", name.getText());
        rParams.put("category", category.getText());
        rParams.put("value", value.getText());

        Log.d("access", settings.getString("access-token", ""));
        Log.d("client", settings.getString("client", ""));
        Log.d("uid", settings.getString("uid", ""));

        final AsyncHttpClient client = new AsyncHttpClient();
        client.post(getString(R.string.url) + "goods", rParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Result","SUCCESS");
                Log.d("Result",String.valueOf(new String(responseBody)));
                Toast.makeText(AddGoodActivity.this, "Good Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddGoodActivity.this, GoodsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Result","FAILED");
                Log.d("Result",String.valueOf(statusCode));
                Toast.makeText(AddGoodActivity.this, "Failed", Toast.LENGTH_LONG).show();
                client.cancelRequests(AddGoodActivity.this, true);
            }
        });
    }
}
