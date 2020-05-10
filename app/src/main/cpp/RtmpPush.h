//
// Created by yangw on 2018-9-14.
//

#ifndef WLLIVEPUSHER_RTMPPUSH_H
#define WLLIVEPUSHER_RTMPPUSH_H

#include <malloc.h>
#include <string.h>
#include "WlQueue.h"
#include "pthread.h"

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
public:
    RtmpPush(const char *url);
    ~RtmpPush();

    void init();



};


#endif //WLLIVEPUSHER_RTMPPUSH_H
