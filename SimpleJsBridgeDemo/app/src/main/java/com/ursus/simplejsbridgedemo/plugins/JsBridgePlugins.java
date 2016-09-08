package com.ursus.simplejsbridgedemo.plugins;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;

import com.ursus.simplejsbridgedemo.bean.Message;

import java.lang.reflect.Type;

/**
 * Author:  ursus
 * Date:    16/8/29
 * Function:
 */
public interface JsBridgePlugins {

    void onCreate(Context context, WebView webView);

    void onDestory();

    void onActivityResult(int requestCode, int resultCode, final Intent data);

    void handler(Message message);

    String bindFunction();

//    int bindFunctionId();

    Type bindData();

    boolean isPermission(String id);
}
