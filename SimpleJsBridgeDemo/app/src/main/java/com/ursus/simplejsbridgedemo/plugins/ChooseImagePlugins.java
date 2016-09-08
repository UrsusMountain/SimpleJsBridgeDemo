package com.ursus.simplejsbridgedemo.plugins;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import com.google.gson.reflect.TypeToken;
import com.ursus.simplejsbridgedemo.bridge.JsBridgeConstants;
import com.ursus.simplejsbridgedemo.bridge.JsBridgePluginsManager;
import com.ursus.simplejsbridgedemo.bean.MessageEntity;
import com.ursus.simplejsbridgedemo.bean.ResponseData;
import com.ursus.simplejsbridgedemo.bean.ToastContentBean;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.concurrent.Executors;

/**
 * Author:  ursus
 * Date:    16/8/30
 * Function:
 */
public class ChooseImagePlugins extends BaseJsBridgePlugin<ToastContentBean> {

    private ResponseData response;

    @Override
    protected void handlerData(MessageEntity<ToastContentBean> data) {
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(mContext instanceof Activity){
            ((Activity) mContext).startActivityForResult(picture, 12);
        }
        response = new ResponseData();
        response.setSuccessCallbackId(data.getSuccessCallbackId());
        response.setFailureCallbackId(data.getFailureCallbackId());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if(requestCode == 12){
            if(resultCode == Activity.RESULT_OK){
                response.setStatus("success");
                if (data != null) {
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Uri uri = data.getData();
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
                                callbackImage(compressImageToString(bitmap));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }else{
                response.setStatus("failure");
                response.setErr("取消选择");
                JsBridgePluginsManager.getInstance().nativeCallback(mWebView,response);
            }
        }
    }

    @Override
    public String bindFunction() {
        return JsBridgeConstants.FUNCTION_GETLOCALIMAGE;
    }


    @Override
    public Type bindData() {
        return new TypeToken<ToastContentBean>(){
        }.getType();
    }

    //图片转化成base64编码过得字符串
    public static String compressImageToString(Bitmap image) {
        StringBuilder sb = new StringBuilder();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        sb.append(Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP));
        return sb.toString();
    }

    private void callbackImage(final String content) {
        response.setData(content);
        if(mContext instanceof Activity){
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JsBridgePluginsManager.getInstance().nativeCallback(mWebView,response);
                }
            });
        }
    }
}
