package com.flam.edgedetection.jni

import android.graphics.Bitmap

/**
 * JNI Bridge for native OpenCV processing
 * Loads native library and provides interface for frame processing
 */
object NativeProcessor {
    
    init {
        try {
            System.loadLibrary("edgedetection")
        } catch (e: UnsatisfiedLinkError) {
            // Native library not yet built - this is expected during initial setup
            android.util.Log.e("NativeProcessor", "Failed to load native library: ${e.message}")
        }
    }
    
    /**
     * Process camera frame with Canny edge detection
     * @param inputFrame Raw frame data (YUV format)
     * @param width Frame width in pixels
     * @param height Frame height in pixels
     * @return Processed frame as byte array (RGBA format)
     */
    external fun processFrame(
        inputFrame: ByteArray,
        width: Int,
        height: Int
    ): ByteArray
    
    /**
     * Test function to verify JNI is working
     * @return Test message from native code
     */
    external fun stringFromJNI(): String
}