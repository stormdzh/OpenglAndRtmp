//
// Created by yangw on 2018-9-14.
//

#ifndef WLLIVEPUSHER_RTMPPUSH_H
#define WLLIVEPUSHER_RTMPPUSH_H

#include <malloc.h>
#include <string.h>
#include "WlQueue.h"
#include "pthread.h"
#include "WlCallJava.h"

extern "C"
{
#include "librtmp/rtmp.h"
};

class RtmpPush {

public:
    RTMP *rtmp = NULL;
    char *url = NULL;
    WlQueue *queue = NULL;
    pthread_t push_thread;

    WlCallJava *callJava=NULL;

    bool startPushing=false; //是否在推流
    long startTime =0;
public:
    RtmpPush(const char *url,WlCallJava *callJava);
    ~RtmpPush();

    void init();

    void psuSPSPPS(char *sps,int sps_len,char *pps,int pps_len);

    void pushVideoData(char *data, int data_len, bool keyframe);



};


#endif //WLLIVEPUSHER_RTMPPUSH_H
