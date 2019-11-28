package com.league.foorballleagueapp;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * The type Web view activity.
 */
public class WebViewActivity extends AppCompatActivity {

    /**
     * The Unbinder.
     */
    Unbinder unbinder;
    /**
     * The Web view.
     */
    @BindView(R.id.webView)
    WebView webView;

    /**
     * The Url.
     */
    String url;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        unbinder = ButterKnife.bind(this);

        /*
         * Load Team Url to webview and show the website
         */
        url = getIntent().getStringExtra("url");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
