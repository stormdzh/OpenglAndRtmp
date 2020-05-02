package com.stormdzh.openglandrtmp;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @Description: 描述
 * @Author: dzh
 * @CreateDate: 2020-05-02 10:02
 */
public class WLMutiSuffaceView extends WLEGLSurfaceView {

    private WlMutiRender mWlMutiRender;

    public WLMutiSuffaceView(Context context) {
        this(context,null);
    }

    public WLMutiSuffaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WLMutiSuffaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mWlMutiRender=new WlMutiRender(context);
        setRender(mWlMutiRender);
    }

    public void setTextureId(int textureId,int index){

        if(mWlMutiRender!=null){
            mWlMutiRender.setTextureId(textureId,index);
        }
    }
}
