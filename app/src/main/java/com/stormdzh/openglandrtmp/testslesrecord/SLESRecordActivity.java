package com.stormdzh.openglandrtmp.testslesrecord;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.stormdzh.openglandrtmp.R;
import com.stormdzh.openglandrtmp.camera.NativeSdk;

public class SLESRecordActivity extends Activity {

   private NativeSdk mNativeSdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testsles_record);

        mNativeSdk=new NativeSdk();
    }


    public void start(View view) {
        mNativeSdk.startRecord(Environment.getExternalStorageDirectory().getAbsolutePath() + "/wl_opensl_record.pcm");
    }

    public void stop(View view) {
        mNativeSdk.stopRecord();
    }

}
