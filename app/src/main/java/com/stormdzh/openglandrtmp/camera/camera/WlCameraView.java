package com.stormdzh.openglandrtmp.camera.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.AttributeSet;

import com.stormdzh.openglandrtmp.camera.egl.WLEGLSurfaceView;

/**
 * @Description: 描述
 * @Author: dzh
 * @CreateDate: 2020-05-02 22:17
 */
public class WlCameraView extends WLEGLSurfaceView {

    private WlCameraRender wlCameraRender;
    private WlCamera wlCamera;

    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

    public WlCameraView(Context context) {
        this(context, null);
    }

    public WlCameraView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WlCameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        wlCameraRender = new WlCameraRender(context);
        wlCamera = new WlCamera();
        setRender(wlCameraRender);

        wlCameraRender.setOnSurfaceCreateListener(new WlCameraRender.OnSurfaceCreateListener() {
            @Override
            public void onSurfaceCreate(SurfaceTexture surfaceTexture) {
                wlCamera.initCamera(surfaceTexture, cameraId);
            }

        });
    }


    public void onDestory() {
        if (wlCamera != null) {
            wlCamera.stopPreview();
        }
    }
}
