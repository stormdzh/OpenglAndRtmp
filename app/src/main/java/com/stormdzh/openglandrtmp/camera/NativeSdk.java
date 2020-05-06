package com.stormdzh.openglandrtmp.camera;

/**
 * @Description: 描述
 * @Author: dzh
 * @CreateDate: 2020-05-02 22:04
 */
public class NativeSdk {

    static {
        System.loadLibrary("native-lib");
    }

    //测试获取字符串
    public native String stringFromJni();


    public native void startRecord(String path);

    public native void stopRecord();


}
