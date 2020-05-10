package com.stormdzh.openglandrtmp.camera;

import android.util.Log;

/**
 * @Description: 描述
 * @Author: dzh
 * @CreateDate: 2020-05-02 22:04
 */
public class NativeSdk {

    private static final String TAG = "NativeSdk";

    static {
        System.loadLibrary("native-lib");
    }

    //测试获取字符串
    public native String stringFromJni();


    public native void startRecord(String path);

    public native void stopRecord();


    public native void initPush(String pushUrl);


    private void onConnecting() {
        Log.i(TAG, "onConnecting");
    }

    private void onConnectSuccess() {
        Log.i(TAG, "onConnectSuccess");
    }

    private void onConnectFial(String msg) {
        Log.i(TAG, "onConnectFial msg:" + msg);
    }


}
