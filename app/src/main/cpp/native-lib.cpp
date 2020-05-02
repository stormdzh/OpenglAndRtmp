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

