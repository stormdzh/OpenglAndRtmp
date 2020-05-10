package com.stormdzh.openglandrtmp.camera;

import android.util.Log;

import com.stormdzh.openglandrtmp.rtmppush.OnRtmpLisetner;

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
        if(mOnRtmpLisetner!=null){
            mOnRtmpLisetner.onConnecting();
        }
    }

    private void onConnectSuccess() {
        Log.i(TAG, "onConnectSuccess");
        if(mOnRtmpLisetner!=null){
            mOnRtmpLisetner.onConnectSuccess();
        }
    }

    private void onConnectFial(String msg) {
        Log.i(TAG, "onConnectFial msg:" + msg);
        if(mOnRtmpLisetner!=null){
            mOnRtmpLisetner.onConnectFial(msg);
        }
    }

    private OnRtmpLisetner mOnRtmpLisetner;

    public void setOnRtmpLisetner(OnRtmpLisetner lisetner){
        this.mOnRtmpLisetner=lisetner;
    }


    public void pushSPSPPS(byte[] sps,byte[] pps){
        if(sps!=null&&pps!=null){
            pushSPSPPS(sps,sps.length,pps,pps.length);
        }
    }


    private native void pushSPSPPS(byte[] sps,int sps_len,byte[] pps,int pps_len);


    private native void pushVideoData(byte[] data,int data_len,boolean keyframe);

    public void pushVideoData(byte[] data,boolean keyframe){
        pushVideoData(data,data.length,keyframe);
    }
}
