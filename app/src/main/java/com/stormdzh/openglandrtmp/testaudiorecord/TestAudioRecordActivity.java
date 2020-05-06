package com.stormdzh.openglandrtmp.testaudiorecord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.stormdzh.openglandrtmp.R;

public class TestAudioRecordActivity extends AppCompatActivity {

    private AudioRecordUitl audioRecordUitl = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_audiorecord);

        findViewById(R.id.recordaudio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordaudio(view);
            }
        });
    }

    public void recordaudio(View view) {

        if(audioRecordUitl == null)
        {
            audioRecordUitl = new AudioRecordUitl();
            audioRecordUitl.setOnRecordLisener(new AudioRecordUitl.OnRecordLisener() {
                @Override
                public void recordByte(byte[] audioData, int readSize) {
                    Log.d("ywl5320", "readSize is : " + readSize);
                }
            });
            audioRecordUitl.startRecord();
        }
        else
        {
            audioRecordUitl.stopRecord();
            audioRecordUitl = null;
        }
    }
}
