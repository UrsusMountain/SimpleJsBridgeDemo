package com.ursus.simplejsbridgedemo.bridge;

import android.webkit.WebView;

import com.ursus.simplejsbridgedemo.GsonHolder;
import com.ursus.simplejsbridgedemo.bean.ResponseData;


/**
 * Author:  ursus
 * Date:    16/8/30
 * Function:
 */
public class JsBridgeLoadUtils {

    public static void popMessage(WebView webView,String messageId){
        if(webView == null){
            return ;
        }
        webView.loadUrl(String.format("javascript:SimpleJsBridge._popMessage('%s')",messageId));
    }

    public static void nativeCallback(WebView webView,String response){
        if(webView == null){
            return ;
        }
        webView.loadUrl(String.format("javascript:SimpleJsBridge._nativeCallback('%s')",response));
        //清除消息队列
    }

    public static void nativeCallback(WebView webView, ResponseData response){
        if(webView == null){
            return ;
        }
        String param = GsonHolder.getInstance().getGson().toJson(response);
        webView.loadUrl(String.format("javascript:SimpleJsBridge._nativeCallback('%s')",param));
        //清除消息队列
    }
}
