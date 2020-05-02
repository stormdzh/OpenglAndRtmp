cmake_minimum_required(VERSION 3.4.1)


message(" add   native-lib")
message(${ANDROID_ABI})
message(${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libavcodec.so)

add_library( # Sets the name of the library.
        native-lib
        # Sets the library as a shared library.
        SHARED
        src/main/cpp/native-lib.cpp
        )




target_link_libraries(
        native-lib

        # 链接 android ndk 自带的一些库
        android
        log
        OpenSLES
        ${log-lib}  ${android-lib}
)