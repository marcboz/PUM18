package com.example.mboz.pum18.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

    String requestURL = "https://a2.wykop.pl/Links/Top/" + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/appkey/QBP7k6rRJG";
    String secret = "CPfzNwFer7";
    String sign = Helpers.md5(secret + requestURL);

    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestURL, null, new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {
            String requestResponse = response.toString();
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("requestData", requestResponse);
            startActivity(intent);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("WebRequest error", error.toString());

        }
    }) {
        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> params = new HashMap<>();
            params.put("Content-Type", "application/json; charset=UTF-8");
            params.put("apisign", sign);
            return params;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebRequest.getInstance(this).addToRequestQueue(request);
    }
}
