package com.flam.edgedetection.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Manages camera lifecycle and frame capture
 * Uses CameraX API for simplified camera operations
 */
class CameraManager(private val context: Context) {
    
    private var cameraProvider: ProcessCameraProvider? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    
    companion object {
        const val REQUEST_CODE_PERMISSIONS = 10
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
    
    /**
     * Check if camera permission is granted
     */
    fun hasPermissions(): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }
    
    /**
     * Setup camera and bind to lifecycle
     * @param lifecycleOwner Activity or Fragment lifecycle owner
     * @param frameProcessor Callback for processing captured frames
     */
    fun setupCamera(lifecycleOwner: LifecycleOwner, frameProcessor: (ImageProxy) -> Unit) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            bindCamera(lifecycleOwner, frameProcessor)
        }, ContextCompat.getMainExecutor(context))
    }
    
    /**
     * Bind camera use cases (preview + analysis)
     */
    private fun bindCamera(lifecycleOwner: LifecycleOwner, frameProcessor: (ImageProxy) -> Unit) {
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        
        // Image analysis for frame processing
        imageAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor) { image ->
                    frameProcessor(image)
                    image.close()
                }
            }
        
        try {
            cameraProvider?.unbindAll()
            cameraProvider?.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                imageAnalyzer
            )
        } catch (e: Exception) {
            android.util.Log.e("CameraManager", "Camera binding failed", e)
        }
    }
    
    /**
     * Shutdown camera and cleanup resources
     */
    fun shutdown() {
        cameraProvider?.unbindAll()
        cameraExecutor.shutdown()
    }
}