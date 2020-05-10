//
// Created by tal on 2020-05-02.
//


#include <jni.h>
#include <unistd.h>
#include <string>


//com.stormdzh.openglandrtmp.camera.NativeSdk.stringFromJni
extern "C"
JNIEXPORT jstring JNICALL Java_com_stormdzh_openglandrtmp_camera_NativeSdk_stringFromJni(JNIEnv *env,
                                                                                jobject) {

    std::string hello = "hello jni";

    return env->NewStringUTF(hello.c_str());
}


#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
#include "RecordBuffer.h"
#include "AndroidLog.h"


SLObjectItf slObjectEngine = NULL;
SLEngineItf  engineItf = NULL;

SLObjectItf  recordObj = NULL;
SLRecordItf  recordItf = NULL;

SLAndroidSimpleBufferQueueItf recorderBufferQueue = NULL;

RecordBuffer *recordBuffer;

FILE *pcmFile = NULL;

bool finish = false;


void bqRecorderCallback(SLAndroidSimpleBufferQueueItf bq, void *context)
{
    fwrite(recordBuffer->getNowBuffer(), 1, 4096, pcmFile);
    if(finish)
    {
        LOGE("录制完成");
        (*recordItf)->SetRecordState(recordItf, SL_RECORDSTATE_STOPPED);
        //
        (*recordObj)->Destroy(recordObj);
        recordObj = NULL;
        recordItf = NULL;
        (*slObjectEngine)->Destroy(slObjectEngine);
        slObjectEngine = NULL;
        engineItf = NULL;
        delete(recordBuffer);
    } else{
        LOGE("正在录制");
        (*recorderBufferQueue)->Enqueue(recorderBufferQueue, recordBuffer->getRecordBuffer(), 4096);
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_stormdzh_openglandrtmp_camera_NativeSdk_startRecord(JNIEnv *env, jobject instance,
                                                         jstring path_) {

    if(finish)
    {
        return;
    }
    const char *path = env->GetStringUTFChars(path_, 0);
    finish = false;
    pcmFile = fopen(path, "w");
    recordBuffer = new RecordBuffer(4096);

    slCreateEngine(&slObjectEngine, 0, NULL, 0, NULL, NULL);
    (*slObjectEngine)->Realize(slObjectEngine, SL_BOOLEAN_FALSE);
    (*slObjectEngine)->GetInterface(slObjectEngine, SL_IID_ENGINE, &engineItf);



    SLDataLocator_IODevice loc_dev = {SL_DATALOCATOR_IODEVICE,
                                      SL_IODEVICE_AUDIOINPUT,
                                      SL_DEFAULTDEVICEID_AUDIOINPUT,
                                      NULL};
    SLDataSource audioSrc = {&loc_dev, NULL};


    SLDataLocator_AndroidSimpleBufferQueue loc_bq = {
            SL_DATALOCATOR_ANDROIDSIMPLEBUFFERQUEUE,
            2
    };


    SLDataFormat_PCM format_pcm = {
            SL_DATAFORMAT_PCM, 2, SL_SAMPLINGRATE_44_1,
            SL_PCMSAMPLEFORMAT_FIXED_16, SL_PCMSAMPLEFORMAT_FIXED_16,
            SL_SPEAKER_FRONT_LEFT | SL_SPEAKER_FRONT_RIGHT, SL_BYTEORDER_LITTLEENDIAN
    };

    SLDataSink audioSnk = {&loc_bq, &format_pcm};

    const SLInterfaceID id[1] = {SL_IID_ANDROIDSIMPLEBUFFERQUEUE};
    const SLboolean req[1] = {SL_BOOLEAN_TRUE};

    (*engineItf)->CreateAudioRecorder(engineItf, &recordObj, &audioSrc, &audioSnk, 1, id, req);
    (*recordObj)->Realize(recordObj, SL_BOOLEAN_FALSE);
    (*recordObj)->GetInterface(recordObj, SL_IID_RECORD, &recordItf);

    (*recordObj)->GetInterface(recordObj, SL_IID_ANDROIDSIMPLEBUFFERQUEUE, &recorderBufferQueue);


    (*recorderBufferQueue)->Enqueue(recorderBufferQueue, recordBuffer->getRecordBuffer(), 4096);

    (*recorderBufferQueue)->RegisterCallback(recorderBufferQueue, bqRecorderCallback, NULL);

    (*recordItf)->SetRecordState(recordItf, SL_RECORDSTATE_RECORDING);

    env->ReleaseStringUTFChars(path_, path);
}


extern "C"
JNIEXPORT void JNICALL
Java_com_stormdzh_openglandrtmp_camera_NativeSdk_stopRecord(JNIEnv *env, jobject instance) {

    // TODO
    finish = true;

}

#include "RtmpPush.h"
#include "WlCallJava.h"
RtmpPush * rtmpPush = NULL;
JavaVM *javaVM;
WlCallJava *wlCallJava=NULL;


extern "C"
JNIEXPORT void JNICALL
Java_com_stormdzh_openglandrtmp_camera_NativeSdk_initPush(JNIEnv *env, jobject thiz,
                                                          jstring push_url) {
    // TODO: implement initPush()

    const char *pushUrl = env->GetStringUTFChars(push_url, 0);
    wlCallJava=new WlCallJava(javaVM,env,&thiz);
    rtmpPush = new RtmpPush(pushUrl,wlCallJava);
    rtmpPush->init();

    env->ReleaseStringUTFChars(push_url, pushUrl);
}


extern "C"
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved)
{
    javaVM = vm;
    JNIEnv* env;
    if (vm->GetEnv((void**)&env, JNI_VERSION_1_4) != JNI_OK)
    {
            LOGE("GetEnv failed!");
        return -1;
    }
    return JNI_VERSION_1_4;
}

extern "C"
JNIEXPORT void JNICALL JNI_OnUnload(JavaVM* vm, void* reserved){
    javaVM = NULL;
}



extern "C"
JNIEXPORT void JNICALL
Java_com_stormdzh_openglandrtmp_camera_NativeSdk_pushSPSPPS(JNIEnv *env, jobject thiz,
                                                            jbyteArray sps_, jint sps_len,
                                                            jbyteArray pps_, jint pps_len
                                                            ) {

    jbyte *sps = env->GetByteArrayElements(sps_,NULL);
    jbyte *pps = env->GetByteArrayElements(pps_,NULL);

    if(rtmpPush!=NULL){
        rtmpPush->psuSPSPPS(reinterpret_cast<char *>(sps), sps_len, reinterpret_cast<char *>(pps), pps_len);
    }

    env->ReleaseByteArrayElements(sps_,sps,0);
    env->ReleaseByteArrayElements(pps_,pps,0);

}

extern "C"
JNIEXPORT void JNICALL
Java_com_stormdzh_openglandrtmp_camera_NativeSdk_pushVideoData(JNIEnv *env, jobject thiz,
                                                               jbyteArray data_, jint data_len,jboolean keyframe) {
    // TODO: implement pushVideoData()

    jbyte *data = env->GetByteArrayElements(data_,NULL);

    if(rtmpPush!=NULL){
        rtmpPush->pushVideoData(reinterpret_cast<char *>(data), data_len, keyframe);
    }

    env->ReleaseByteArrayElements(data_,data,0);
}