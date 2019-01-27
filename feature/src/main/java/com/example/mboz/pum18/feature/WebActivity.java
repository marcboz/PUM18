package com.example.mboz.pum18.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {
    String sourceUrl;
    WebView web;
    boolean resumable = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Bundle extras = getIntent().getExtras();
        sourceUrl=extras.getString("sourceUrl");
        web = findViewById(R.id.webview);

    }

    @Override
    protected void onStart(){
        super.onStart();
        web.loadUrl(sourceUrl);
    }

    @Override
    protected void onPause(){
        super.onPause();

        resumable = true;
    }

    @Override
    protected void onResume(){
        super.onResume();

        Intent intent = new Intent(WebActivity.this, LaunchActivity.class);
        if(resumable){
            startActivity(intent);
        }

    }
}
