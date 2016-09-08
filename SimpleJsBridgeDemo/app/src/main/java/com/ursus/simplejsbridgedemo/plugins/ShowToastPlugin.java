package com.ursus.simplejsbridgedemo.plugins;

import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.ursus.simplejsbridgedemo.bean.MessageEntity;
import com.ursus.simplejsbridgedemo.bean.ResponseData;
import com.ursus.simplejsbridgedemo.bean.ToastContentBean;
import com.ursus.simplejsbridgedemo.bridge.JsBridgeConstants;
import com.ursus.simplejsbridgedemo.bridge.JsBridgePluginsManager;

import java.lang.reflect.Type;

/**
 * Author:  ursus
 * Date:    16/8/29
 * Function:
 */
public class ShowToastPlugin extends BaseJsBridgePlugin<ToastContentBean> {

    @Override
    public String bindFunction() {
        return JsBridgeConstants.FUNCTION_SHOWTOAST;
    }

    @Override
    public Type bindData() {
        return new TypeToken<ToastContentBean>() {
        }.getType();
    }

    @Override
    protected void handlerData(MessageEntity<ToastContentBean> data) {
        Toast.makeText(mContext, data.getData().getContent(), Toast.LENGTH_SHORT).show();
        ResponseData response = new ResponseData();
        response.setStatus("success");
        response.setData("success");
        response.setSuccessCallbackId(data.getSuccessCallbackId());
        response.setFailureCallbackId(data.getFailureCallbackId());
        JsBridgePluginsManager.getInstance().nativeCallback(mWebView, response);
    }
}
