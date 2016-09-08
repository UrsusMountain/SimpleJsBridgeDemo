package com.ursus.simplejsbridgedemo.plugins;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.google.gson.reflect.TypeToken;
import com.ursus.simplejsbridgedemo.bean.MessageEntity;
import com.ursus.simplejsbridgedemo.bean.ResponseData;
import com.ursus.simplejsbridgedemo.bean.ToastContentBean;
import com.ursus.simplejsbridgedemo.bridge.JsBridgeConstants;
import com.ursus.simplejsbridgedemo.bridge.JsBridgePluginsManager;

import java.lang.reflect.Type;

/**
 * Author:  ursus
 * Date:    16/8/30
 * Function:
 */
public class AlertPlugin extends BaseJsBridgePlugin<ToastContentBean> {

    @Override
    protected void handlerData(MessageEntity<ToastContentBean> data) {
        if (mContext instanceof Activity) {
            final ResponseData response = new ResponseData();
            response.setSuccessCallbackId(data.getSuccessCallbackId());
            response.setFailureCallbackId(data.getFailureCallbackId());
            new AlertDialog.Builder(mContext)
                    .setTitle("标题")
                    .setMessage(data.getData().getContent())
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            response.setStatus(ResponseData.STATUS_SUCCESS);
                            response.setData("点击确定");
                            JsBridgePluginsManager.getInstance().nativeCallback(mWebView,response);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            response.setStatus(ResponseData.STATUS_SUCCESS);
                            response.setData("点击取消");
                            JsBridgePluginsManager.getInstance().nativeCallback(mWebView,response);
                        }
                    })
                    .show();
        }
    }

    @Override
    public String bindFunction() {
        return JsBridgeConstants.FUNCTION_ALERT;
    }

    @Override
    public Type bindData() {
        return new TypeToken<ToastContentBean>() {
        }.getType();
    }
}
