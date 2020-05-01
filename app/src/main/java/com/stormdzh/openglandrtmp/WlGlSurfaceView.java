package com.stormdzh.openglandrtmp;

import android.content.Context;
import android.util.AttributeSet;

public class WlGlSurfaceView extends WLEGLSurfaceView{

    public WlGlSurfaceView(Context context) {
        this(context, null);
    }

    public WlGlSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WlGlSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setRender(new WlTextureRender(context));
        setRenderMode(WLEGLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
