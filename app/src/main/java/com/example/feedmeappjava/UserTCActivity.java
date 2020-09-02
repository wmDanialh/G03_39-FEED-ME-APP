package com.example.feedmeappjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class UserTCActivity extends AppCompatActivity {

    WebView webView;

    String url= "https://feedmeapptermsandconditions.blogspot.com/2020/09/feed-me-appt-published2020-terms-ofuse.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_t_c);

        Toolbar toolbar = findViewById(R.id.toolbarTc);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Terms & Conditions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar.setTitleTextColor(getResources().getColor(android.R.color.holo_red_dark));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        webView = (WebView)findViewById(R.id.webTerms) ;
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

    }
}