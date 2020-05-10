//
// Created by yangw on 2018-9-14.
//



#include "RtmpPush.h"

RtmpPush::RtmpPush(const char *url,WlCallJava *callJava) {
    this->callJava=callJava;
    this->url = static_cast<char *>(malloc(512));
    strcpy(this->url, url);
    this->queue = new WlQueue();
}

RtmpPush::~RtmpPush() {
    queue->notifyQueue();
    queue->clearQueue();
    free(url);
}

void *callBackPush(void *data)
{
    RtmpPush *rtmpPush = static_cast<RtmpPush *>(data);

    rtmpPush->callJava->onConnectint(WL_THREAD_CHILD);

    rtmpPush->rtmp = RTMP_Alloc();
    RTMP_Init(rtmpPush->rtmp);
    rtmpPush->rtmp->Link.timeout = 10;
    rtmpPush->rtmp->Link.lFlags |= RTMP_LF_LIVE;
    RTMP_SetupURL(rtmpPush->rtmp, rtmpPush->url);
    RTMP_EnableWrite(rtmpPush->rtmp);
    LOGE("int the url : %s",rtmpPush->url);
    if(!RTMP_Connect(rtmpPush->rtmp, NULL))
    {
        LOGE("can not connect the url");
        rtmpPush->callJava->onConnectFail(strerror(RTMP_Connect(rtmpPush->rtmp, NULL)));
        goto end;
    }

    if(!RTMP_ConnectStream(rtmpPush->rtmp, 0))
    {
        LOGE("can not connect the stream of service:%s",strerror(RTMP_ConnectStream(rtmpPush->rtmp, 0)));
        rtmpPush->callJava->onConnectFail(strerror(RTMP_ConnectStream(rtmpPush->rtmp, 0)));
        goto end;
    }

    LOGD("链接成功， 开始推流");
    rtmpPush->callJava->onConnectsuccess();

//    while(true)
//    {}




    end:
        RTMP_Close(rtmpPush->rtmp);
        RTMP_Free(rtmpPush->rtmp);
        rtmpPush->rtmp = NULL;
    pthread_exit(&rtmpPush->push_thread);
}

void RtmpPush::init() {

    pthread_create(&push_thread, NULL, callBackPush, this);

}
