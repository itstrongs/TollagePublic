package com.itstrongs.tollage.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.itstrongs.tollage2.R;

/**
 * Created by itstrong on 2017/6/4.
 */

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView mWebView = (WebView) findViewById(R.id.web_view);
        String url = getIntent().getStringExtra("file_url");
        mWebView.loadUrl(url);
    }
}
