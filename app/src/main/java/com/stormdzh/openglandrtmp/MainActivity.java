package com.stormdzh.openglandrtmp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.opengl.GLES20;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.stormdzh.openglandrtmp.camera.CameraActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initSurfaceView();

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

    }

    private int count=0;
    private void initSurfaceView() {
        surfaceView = findViewById(R.id.surfaceView);

        if(true) return;
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(final SurfaceHolder holder, int format, final int width, final int height) {

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        EglHelper eglHelper = new EglHelper();
                        eglHelper.initEgl(holder.getSurface(), null);

                        while (true) {
                            GLES20.glViewport(0, 0, width, height);

                            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
                            if(count%2==0) {
                                GLES20.glClearColor(1.0f, 1.0f, 0.0f, 1.0f);
                            }else{
                                GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
                            }
                            count++;
                            eglHelper.swapBuffers();

                            try {
                                Thread.sleep(1600);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();


            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //egl.destroy
            }
        });
    }



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
        if (!ps.isEmpty()) {
            String[] ps3 = new String[ps.size()];
            ps.toArray(ps3);
            ActivityCompat.requestPermissions(this, ps3, 100);
        }
    }
}
