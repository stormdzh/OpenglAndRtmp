package com.stormdzh.openglandrtmp.rtmppush;

/**
 * @Description: 描述
 * @Author: dzh
 * @CreateDate: 2020-05-10 15:54
 */
public interface OnRtmpLisetner {

    void onConnecting();
    void onConnectSuccess();
    void onConnectFial(String msg);
}
