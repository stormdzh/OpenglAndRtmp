package com.stormdzh.openglandrtmp.rtmppush;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.stormdzh.openglandrtmp.R;
import com.stormdzh.openglandrtmp.camera.NativeSdk;
import com.stormdzh.openglandrtmp.camera.camera.WlCameraView;

public class LivePushActivity extends AppCompatActivity{

    private NativeSdk mNativeSdk;
    private WlCameraView mCameraView;
    private boolean start;
    private WlPushEncodec wlPushEncodec;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livepush);

        mCameraView=findViewById(R.id.mCameraView);
        mNativeSdk = new NativeSdk();


        mNativeSdk.setOnRtmpLisetner(new OnRtmpLisetner() {
            @Override
            public void onConnecting() {
                Log.d("ywl5320", "链接服务器成功，可以开始推流了");
                wlPushEncodec = new WlPushEncodec(LivePushActivity.this, mCameraView.getTextureId());
                wlPushEncodec.initEncodec(mCameraView.getEglContext(), 720, 1280, 44100, 2);
                wlPushEncodec.startRecord();
            }

            @Override
            public void onConnectSuccess() {

            }

            @Override
            public void onConnectFial(String msg) {

                Log.d("ywl5320", msg);
            }
        });
    }

    public void startpush(View view) {
        start=!start;
        if(start) {
            mNativeSdk.initPush("rtmp://192.168.124.5:1935/rtmplive/room");
//        mNativeSdk.initPush("rtmp://192.168.124.5/rtmplive/room");
        }else{
            if(wlPushEncodec != null)
            {
                wlPushEncodec.stopRecord();
                wlPushEncodec = null;
            }
        }
    }

}
