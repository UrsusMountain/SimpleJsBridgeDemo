package com.ursus.simplejsbridgedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.reflect.TypeToken;
import com.ursus.simplejsbridgedemo.bean.Message;
import com.ursus.simplejsbridgedemo.bean.ResponseData;
import com.ursus.simplejsbridgedemo.bridge.JsBridgeLoadUtils;
import com.ursus.simplejsbridgedemo.bridge.JsBridgePluginsManager;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.webview)
    WebView webview;
    private ResponseData datas;
    private JsBridgePluginsManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initWebView();
        manager = JsBridgePluginsManager.getInstance();
        manager.onCreate(this,webview);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.onDestory();
    }

    private void initWebView() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/jsbridge.html");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    url = URLDecoder.decode(url, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if (url.startsWith("yy://new_message/")) {
                    popMessage(url.replace("yy://new_message/", ""));
                    return true;
                } else if (url.startsWith("yy://message_content/")) {
                    sendMessage(url.replace("yy://message_content/", ""));
                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }
        });
    }

    private void popMessage(String url) {
        JsBridgeLoadUtils.popMessage(webview,url);
    }

    private void sendMessage(String url) {
        Type reponseType = new TypeToken<Message>() {
        }.getType();
        Message message = GsonHolder.getInstance().getGson().fromJson(url,reponseType);
        manager.dispatchMessage(message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        manager.onActivityResult(requestCode,resultCode,data);
    }
}
