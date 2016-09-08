package com.ursus.simplejsbridgedemo.bridge;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseArray;
import android.webkit.WebView;

import com.ursus.simplejsbridgedemo.GsonHolder;
import com.ursus.simplejsbridgedemo.bean.Message;
import com.ursus.simplejsbridgedemo.bean.ResponseData;
import com.ursus.simplejsbridgedemo.plugins.AlertPlugin;
import com.ursus.simplejsbridgedemo.plugins.ChooseImagePlugins;
import com.ursus.simplejsbridgedemo.plugins.JsBridgePlugins;
import com.ursus.simplejsbridgedemo.plugins.ShowToastPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  ursus
 * Date:    16/8/29
 * Function:
 */
public class JsBridgePluginsManager {

    private static JsBridgePluginsManager mManager = new JsBridgePluginsManager();
    public Context mContext;
    public WebView mWebview;

    private final List<JsBridgePlugins> mPlugins = new ArrayList<>();
    private final SparseArray<Message> mMessageQueue = new SparseArray<>();


    private JsBridgePluginsManager(){
    }

    public static JsBridgePluginsManager getInstance(){
        return mManager;
    }

    public void dispatchMessage(Message message){
        if(message == null){
            return ;
        }
//        if(mMessageQueue.get(message.getFunctionId()) != null){
//            // 队列中已  经用此消息，就不再分发，提示发送消息繁忙
//            return ;
//        }
        mMessageQueue.put(message.getFunctionId(),message);
        for (JsBridgePlugins plugin : mPlugins) {
            if(TextUtils.equals(plugin.bindFunction(),message.getFunction())){
                plugin.handler(message);
            }
        }
    }

    public void onCreate(Context context,WebView mWebview){
        this.mContext = context;
        this.mWebview = mWebview;
        regiestPlugins();
        for (JsBridgePlugins plugin : mPlugins) {
            plugin.onCreate(context,mWebview);
        }
    }

    public void onDestory(){
        for (JsBridgePlugins plugin : mPlugins) {
            plugin.onDestory();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, final Intent data){
        for (JsBridgePlugins plugin : mPlugins) {
            plugin.onActivityResult(requestCode,resultCode,data);
        }
    }

    public void popMessage(WebView webView,String messageId){
        if(webView == null){
            return ;
        }
        webView.loadUrl(String.format("javascript:SimpleJsBridge._popMessage('%s')",messageId));
    }

    private void nativeCallback(WebView webView,String response){
        if(webView == null){
            return ;
        }
        webView.loadUrl(String.format("javascript:SimpleJsBridge._nativeCallback('%s')",response));
        //清除消息队列

    }

    public void nativeCallback(WebView webView, ResponseData response){
        if(webView == null){
            return ;
        }
        String param = GsonHolder.getInstance().getGson().toJson(response);
        webView.loadUrl(String.format("javascript:SimpleJsBridge._nativeCallback('%s')",param));
        //清除消息队列
    }

    public void nativeCallback(WebView webView, ResponseData response,int functionId){
        if(webView == null){
            return ;
        }
        String param = GsonHolder.getInstance().getGson().toJson(response);
        webView.loadUrl(String.format("javascript:SimpleJsBridge._nativeCallback('%s')",param));
        //清除消息队列
        removeMessage(functionId);
    }

    public void removeMessage(int functionId){
        mMessageQueue.remove(functionId);
    }

    private void regiestPlugins() {
        mPlugins.add(new ShowToastPlugin());
        mPlugins.add(new ChooseImagePlugins());
        mPlugins.add(new AlertPlugin());
    }
}
