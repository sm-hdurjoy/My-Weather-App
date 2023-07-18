package com.example.myweather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class Fetch {
    private String url;
    private ResponseListener responseListener;
    private Context context;

    Fetch(Context context, String url, ResponseListener responseListener) {
        this.context = context;
        this.url = url;
        this.responseListener = responseListener;
    }

    void getData() {
        WebView webView = new WebView(this.context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new CustomBridge(this.context, this.responseListener), "CustomBridge");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:CustomBridge.pushResponse(document.body.innerText)");
            }
        });
        webView.loadUrl(this.url);
    }
}

class CustomBridge {
    Context context;
    ResponseListener responseListener;

    public CustomBridge(Context context, ResponseListener responseListener) {
        this.context = context;
        this.responseListener = responseListener;
    }

    @JavascriptInterface
    public void pushResponse(String data) {
        responseListener.onResponse(data);
    }
}