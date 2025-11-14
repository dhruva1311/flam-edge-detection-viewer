#include <jni.h>
#include <string>
#include <android/log.h>
#include <android/bitmap.h>

#define TAG "EdgeDetection-Native"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

/**
 * Test function to verify JNI bridge is working
 * Called from: NativeProcessor.stringFromJNI()
 */
extern "C" JNIEXPORT jstring JNICALL
Java_com_flam_edgedetection_jni_NativeProcessor_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "JNI Bridge Working! Native library loaded successfully.";
    LOGI("stringFromJNI() called successfully");
    return env->NewStringUTF(hello.c_str());
}

/**
 * Process camera frame with OpenCV edge detection
 * Called from: NativeProcessor.processFrame()
 * 
 * @param inputFrame: Raw camera frame data (YUV format)
 * @param width: Frame width in pixels
 * @param height: Frame height in pixels
 * @return: Processed frame data (RGBA format)
 * 
 * Algorithm flow:
 * 1. Convert YUV to RGB/BGR
 * 2. Convert to grayscale
 * 3. Apply Gaussian blur
 * 4. Apply Canny edge detection
 * 5. Convert back to RGBA for display
 */
extern "C" JNIEXPORT jbyteArray JNICALL
Java_com_flam_edgedetection_jni_NativeProcessor_processFrame(
        JNIEnv* env,
        jobject /* this */,
        jbyteArray inputFrame,
        jint width,
        jint height) {
    
    LOGD("processFrame() called: %dx%d", width, height);
    
    // Get input frame data
    jbyte* frameData = env->GetByteArrayElements(inputFrame, nullptr);
    jsize frameSize = env->GetArrayLength(inputFrame);
    
    // TODO: Implement OpenCV processing
    // Step 1: Convert YUV to BGR using cv::cvtColor()
    // Step 2: Convert to grayscale using cv::cvtColor()
    // Step 3: Apply Gaussian blur using cv::GaussianBlur()
    // Step 4: Apply Canny edge detection using cv::Canny()
    // Step 5: Convert result to RGBA for OpenGL
    
    // For now, create placeholder output (same size as input)
    jbyteArray outputArray = env->NewByteArray(frameSize);
    env->SetByteArrayRegion(outputArray, 0, frameSize, frameData);
    
    // Release input frame
    env->ReleaseByteArrayElements(inputFrame, frameData, JNI_ABORT);
    
    LOGD("processFrame() completed");
    return outputArray;
}

/**
 * JNI_OnLoad - Called when library is loaded
 * Can be used for initialization
 */
JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    LOGI("Native library loaded, version: 1.0");
    return JNI_VERSION_1_6;
}