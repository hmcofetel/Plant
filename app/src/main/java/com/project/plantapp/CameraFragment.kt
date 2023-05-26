package com.project.plantapp


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.media.ImageReader
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.plantapp.databinding.FragmentCameraBinding
import java.io.File
import java.io.FileOutputStream



@Suppress("DEPRECATION")
class CameraFragment : Fragment() {

    private lateinit var capReq: CaptureRequest.Builder
    private lateinit var handler: Handler
    private lateinit var handlerThread: HandlerThread
    private lateinit var binding: FragmentCameraBinding
    private lateinit var cameraManager:CameraManager
    private lateinit var cameraCaptureSession: CameraCaptureSession
    private lateinit var cameraDevice : CameraDevice
    private lateinit var imageReader: ImageReader
    private lateinit var cameraDeviceStateCallback : CameraDevice.StateCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        requireActivity().findViewById<CoordinatorLayout>(R.id.coordinatorLayout).visibility =View.GONE
        cameraManager = activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        handlerThread = HandlerThread("videoThread")
        handlerThread.start()
        handler = Handler((handlerThread).looper)


        imageReader = ImageReader.newInstance(1080, 1920, ImageFormat.JPEG,1)
        imageReader.setOnImageAvailableListener({ p0 ->
            val image = p0?.acquireLatestImage()
            val buffer = image!!.planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)
            val file = File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_DCIM), "img.jpeg")
            file.createNewFile()
            val opStream = FileOutputStream(file).apply {
                write(bytes)
            }

            opStream.close()
            image.close()
            Toast.makeText(context, "Image Captured", Toast.LENGTH_SHORT).show()
        }, handler)

        binding.textureView.surfaceTextureListener = object: TextureView.SurfaceTextureListener{
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
                openCamera()
            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
                //TODO: implement later
            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                //TODO: implement later
                return false
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
                //TODO: implement later
            }
        }
        cameraDeviceStateCallback = object :CameraDevice.StateCallback(){
            override fun onOpened(p0: CameraDevice) {
                TODO("Not yet implemented")
            }

            override fun onDisconnected(p0: CameraDevice) {
                TODO("Not yet implemented")
            }

            override fun onError(p0: CameraDevice, p1: Int) {
                TODO("Not yet implemented")
            }

        }

        binding.captureBnt.apply {
            setOnClickListener{
                capReq = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
                capReq.addTarget(imageReader.surface)
                cameraCaptureSession.capture(capReq.build(), null, null)
            }
        }
        binding.cameraFragmentBackBnt.setOnClickListener{
            findNavController().navigate(R.id.mainProfileFragment)
            requireActivity().findViewById<CoordinatorLayout>(R.id.coordinatorLayout).visibility = View.VISIBLE
        }
        return binding.root
    }
    @SuppressLint("MissingPermission")
    private fun openCamera(){
        cameraManager.openCamera(cameraManager.cameraIdList[0],object:CameraDevice.StateCallback(){
            override fun onOpened(p0: CameraDevice) {
                cameraDevice = p0

                capReq = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                val surface = Surface(binding.textureView.surfaceTexture)
                capReq.addTarget(surface)

                cameraDevice.createCaptureSession(listOf(surface, imageReader.surface), object: CameraCaptureSession.StateCallback(){
                    override fun onConfigured(p0: CameraCaptureSession) {
                        cameraCaptureSession = p0
                        cameraCaptureSession.setRepeatingRequest(capReq.build(), null, null)
                    }

                    override fun onConfigureFailed(p0: CameraCaptureSession) {
                        TODO("Not yet implemented")
                    }

                }, handler)
            }

            override fun onDisconnected(p0: CameraDevice) {
                TODO("Not yet implemented")
            }

            override fun onError(p0: CameraDevice, p1: Int) {
                TODO("Not yet implemented")
            }

        }, handler)
    }



}

