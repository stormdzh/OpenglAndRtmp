package com.stormdzh.openglandrtmp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.stormdzh.openglandrtmp.camera.NativeSdk;

/**
 * @Description: 描述
 * @Author: dzh
 * @CreateDate: 2020-05-02 21:34
 */
public class CameraActivity extends Activity {

    private static String TAG="mCamera";

    private NativeSdk mNativeSdk;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mNativeSdk=new NativeSdk();

        String testStr = mNativeSdk.stringFromJni();
        Log.i(TAG,testStr);
    }
}
