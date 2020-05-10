package com.stormdzh.openglandrtmp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.stormdzh.openglandrtmp.camera.CameraActivity;
import com.stormdzh.openglandrtmp.rtmppush.LivePushActivity;
import com.stormdzh.openglandrtmp.testaudiorecord.TestAudioRecordActivity;
import com.stormdzh.openglandrtmp.testslesrecord.SLESRecordActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

//    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        initSurfaceView();

        initTest();

        requestPermissions();
    }

    private void initTest() {
        findViewById(R.id.toEglSurface).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,EglSurfaceActivity.class));
            }
        });
        findViewById(R.id.toCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
            }
        });
        findViewById(R.id.toVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, VideoActivity.class));
            }
        });
        findViewById(R.id.imageToVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ImageVideoActivity.class));
            }
        });
        findViewById(R.id.showYUV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, YuvActivity.class));
            }
        });
        findViewById(R.id.audioRecord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestAudioRecordActivity.class));
            }
        });
        findViewById(R.id.slesRecord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SLESRecordActivity.class));
            }
        });
        findViewById(R.id.testRtmp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LivePushActivity.class));

            }
        });

    }

//    private int count=0;
//    private void initSurfaceView() {
//        surfaceView = findViewById(R.id.surfaceView);
//
//        if(true) return;
//        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//
//            }
//
//            @Override
//            public void surfaceChanged(final SurfaceHolder holder, int format, final int width, final int height) {
//
//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        EglHelper eglHelper = new EglHelper();
//                        eglHelper.initEgl(holder.getSurface(), null);
//
//                        while (true) {
//                            GLES20.glViewport(0, 0, width, height);
//
//                            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//                            if(count%2==0) {
//                                GLES20.glClearColor(1.0f, 1.0f, 0.0f, 1.0f);
//                            }else{
//                                GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
//                            }
//                            count++;
//                            eglHelper.swapBuffers();
//
//                            try {
//                                Thread.sleep(1600);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }.start();
//
//
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//                //egl.destroy
//            }
//        });
//    }



    private void requestPermissions() {
        ArrayList<String> ps = new ArrayList<>();
        int per = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (per != PackageManager.PERMISSION_GRANTED) {
            ps.add(Manifest.permission.CAMERA);
        }
        per = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (per != PackageManager.PERMISSION_GRANTED) {
            ps.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        per = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (per != PackageManager.PERMISSION_GRANTED) {
            ps.add(Manifest.permission.READ_PHONE_STATE);
        }
        per = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (per != PackageManager.PERMISSION_GRANTED) {
            ps.add(Manifest.permission.RECORD_AUDIO);
        }
        per = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (per != PackageManager.PERMISSION_GRANTED) {
            ps.add(Manifest.permission.INTERNET);
        }
        if (!ps.isEmpty()) {
            String[] ps3 = new String[ps.size()];
            ps.toArray(ps3);
            ActivityCompat.requestPermissions(this, ps3, 100);
        }
    }
}
