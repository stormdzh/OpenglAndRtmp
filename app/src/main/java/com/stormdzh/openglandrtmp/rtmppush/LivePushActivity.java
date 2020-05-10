package com.stormdzh.openglandrtmp.rtmppush;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.stormdzh.openglandrtmp.R;
import com.stormdzh.openglandrtmp.camera.NativeSdk;

public class LivePushActivity extends AppCompatActivity{

    private NativeSdk mNativeSdk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livepush);
        mNativeSdk = new NativeSdk();
    }

    public void startpush(View view) {
        mNativeSdk.initPush("rtmp://192.168.124.5:1935/rtmplive/room");
//        mNativeSdk.initPush("rtmp://192.168.124.5/rtmplive/room");
    }
}
