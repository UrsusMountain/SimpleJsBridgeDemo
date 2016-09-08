package com.ursus.simplejsbridgedemo.plugins;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.WebView;

import com.ursus.simplejsbridgedemo.GsonHolder;
import com.ursus.simplejsbridgedemo.bean.Message;
import com.ursus.simplejsbridgedemo.bean.MessageEntity;
import com.ursus.simplejsbridgedemo.bean.ResponseData;
import com.ursus.simplejsbridgedemo.bridge.JsBridgePluginsManager;

/**
 * Author:  ursus
 * Date:    16/8/29
 * Function:
 */
public abstract class BaseJsBridgePlugin<T> implements JsBridgePlugins {

    public T data;
    public WebView mWebView;
    public Context mContext;

    @Override
    public void onCreate(Context context, WebView webView) {
        this.mContext = context;
        this.mWebView = webView;
    }

    @Override
    public void onDestory() {
        this.mContext = null;
        this.mWebView = null;
    }

    @Override
    public void handler(Message message) {
        if(!isPermission("")){
            ResponseData data = new ResponseData(
                    ResponseData.STATUS_FAILURE,
                    null,
                    "权限不足",
                    message.getSuccessCallbackId(),
                    message.getFailureCallbackId()
            );
            JsBridgePluginsManager.getInstance().nativeCallback(mWebView,data);
            return ;
        }
        if (TextUtils.isEmpty(message.getData())) {
            data = null;
        } else {
            data = GsonHolder.getInstance().getGson().fromJson(message.getData(),bindData());
        }
        MessageEntity<T> entity = new MessageEntity(message, data);
        handlerData(entity);
    }

    @Override
    public boolean isPermission(String id) {
//        Random rd = new Random();
//        if((rd.nextInt(10)+1)%2 == 0){
//            return true;
//        }
//        return false;
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    protected abstract void handlerData(MessageEntity<T> data);

}
