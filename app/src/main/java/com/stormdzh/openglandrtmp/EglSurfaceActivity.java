package com.stormdzh.openglandrtmp;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class EglSurfaceActivity extends Activity {

    private WlGlSurfaceView mGlSurfaceView;
    private LinearLayout llContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egl);

        mGlSurfaceView = findViewById(R.id.mGlSurfaceView);
        llContent = findViewById(R.id.llContent);


        mGlSurfaceView.getmTextureRender().setOnTextureListener(new WlTextureRender.OnTextureListener() {
            @Override
            public void onRenderCreate(final int textureid) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addMutiView(textureid);
                    }
                });
            }
        });
    }

    private void addMutiView(int textureId) {

        if (llContent.getChildCount() > 0) {
            llContent.removeAllViews();
        }

        for (int i = 0; i < 3; i++) {
            WLMutiSuffaceView mutiSuffaceView = new WLMutiSuffaceView(getBaseContext());
            mutiSuffaceView.setTextureId(textureId,i);
            mutiSuffaceView.setSurfaceAndEglContext(null, mGlSurfaceView.getEglContext());

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.width = 200;
            lp.height = 300;
            mutiSuffaceView.setLayoutParams(lp);

            llContent.addView(mutiSuffaceView);
        }


    }
}
