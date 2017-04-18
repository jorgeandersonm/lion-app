package com.master.jorge.lionapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.master.jorge.lionapp.R;

import cz.msebera.android.httpclient.Header;

public class GoodDetailsActivity extends AppCompatActivity {
    FloatingActionButton buttonDelete;
    SharedPreferences settings;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_details);

        TextView name = (TextView) findViewById(R.id.name_good);
        TextView value = (TextView) findViewById(R.id.value_good);
        TextView category = (TextView) findViewById(R.id.category_good);
        intent = getIntent();

        name.setText(intent.getStringExtra("name"));
        value.setText("U$ " + intent.getStringExtra("value"));
        category.setText(intent.getStringExtra("category"));

        buttonDelete = (FloatingActionButton) findViewById(R.id.button_delete);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeGood();
            }
        });
    }

    public void removeGood(){
        settings = getSharedPreferences("HEADER", 0);
        RequestParams rParams = new RequestParams();
        rParams.put("uid", settings.getString("uid", ""));
        rParams.put("client", settings.getString("client", ""));
        rParams.put("access-token", settings.getString("access-token", ""));
        rParams.put("id", intent.getStringExtra("id"));

        Log.d("ID RECEIVED", intent.getStringExtra("id"));

        AsyncHttpClient client = new AsyncHttpClient();
        client.delete(getString(R.string.url) + "goods/" + intent.getStringExtra("id"), rParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Result","SUCCESS");
                Log.d("Result",String.valueOf(new String(responseBody)));
                Toast.makeText(GoodDetailsActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GoodDetailsActivity.this, GoodsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Result","FAILED");
                Log.d("Result",String.valueOf(statusCode));

                Toast.makeText(GoodDetailsActivity.this, "Session expired", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GoodDetailsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
