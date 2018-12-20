package com.example.tje.food;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    WebSettings webSettings;

    Intent receiveIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        receiveIntent = getIntent();
        String restaurant_name = receiveIntent.getStringExtra("restaurant_name");



        webView = (WebView)findViewById(R.id.webView);
        //클릭시 새창 안뜨도록
        webView.setWebChromeClient(new WebChromeClient());
        //url 매핑
        webView.loadUrl("https://search.naver.com/search.naver?where=post&sm=tab_jum&query="+restaurant_name);
    }
}
