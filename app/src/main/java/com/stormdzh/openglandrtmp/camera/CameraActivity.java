package com.stormdzh.openglandrtmp.camera;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stormdzh.openglandrtmp.R;
import com.stormdzh.openglandrtmp.camera.camera.WlCameraView;

/**
 * @Description: 描述
 * @Author: dzh
 * @CreateDate: 2020-05-02 21:34
 */
public class CameraActivity extends Activity {

    private static String TAG="mCamera";

    private NativeSdk mNativeSdk;


    private WlCameraView mWlCameraView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_camera);

        mNativeSdk=new NativeSdk();
        String testStr = mNativeSdk.stringFromJni();
        Log.i(TAG,testStr);


        mWlCameraView=findViewById(R.id.mWlCameraView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mWlCameraView!=null){
            mWlCameraView.onDestory();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mWlCameraView.previewAngle(this);
    }
}
