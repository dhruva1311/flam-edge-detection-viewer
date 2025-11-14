#include <jni.h>
#include <string>
#include <android/log.h>

#define TAG "EdgeDetection-Native"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_com_flam_edgedetection_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    LOGD("Native library loaded successfully");
    return env->NewStringUTF(hello.c_str());
}