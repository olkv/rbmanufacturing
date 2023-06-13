package com.example.rbmanufacturing.presentation.otk.defect

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.findFragment
import com.example.rbmanufacturing.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ARG_UID_DEFECT = "uid_defect"

/**
 * A simple [Fragment] subclass.
 * Use the [AddDefectImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddDefectImageFragment : BottomSheetDialogFragment() {


    private val viewModelAddDefectImage: AddDefectImageViewModel by activityViewModels()

    private var uid_defect: String? = null

    val REQUIRED_PERMISSINS = arrayOf(Manifest.permission.CAMERA)
    val REQUEST_CODE_PERMISSIONS = 15

    private var imageCapture: ImageCapture?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uid_defect = it.getString(ARG_UID_DEFECT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_defect_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(allPermissionGranded()) {
            //Toast.makeText(this, "Camera Granted", Toast.LENGTH_SHORT).show()
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSINS,
                REQUEST_CODE_PERMISSIONS
            )
        }


        val btnCancel = view.findViewById<Button>(R.id.btnCancelCaptireImage)
        btnCancel.setOnClickListener {
            dismiss()
        }


        val btnCaptureImage = view.findViewById<FloatingActionButton>(R.id.btnCaptureImage)
        btnCaptureImage.setOnClickListener {
            takePhoto()
        }

    }

    private fun takePhoto()  {
        val imageCapture = imageCapture?: return


    }


    private fun startCamera() {
        val cameraProvideFuture = ProcessCameraProvider.getInstance(requireContext())

        val camera_view = view?.findViewById<PreviewView>(R.id.camera_view)

        cameraProvideFuture.addListener({

            val cameraProvider : ProcessCameraProvider =  cameraProvideFuture.get()

            val preview = Preview.Builder()
                .build()
                .also { mPreview ->
                    mPreview.setSurfaceProvider(
                        camera_view?.surfaceProvider
                    )
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {

                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this, cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (e:Exception) {
                Log.d("MYLOG", "starting Camera fail ${e.message}")
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode==REQUEST_CODE_PERMISSIONS) {
            if (allPermissionGranded()) {
                startCamera()
            } else {
                dismiss()
            }
        }

    }


    private fun allPermissionGranded() =
        REQUIRED_PERMISSINS.all {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            )== PackageManager.PERMISSION_GRANTED
        }

    companion object {
        @JvmStatic
        fun newInstance(uid_defect: String) =
            AddDefectImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_UID_DEFECT, uid_defect)
                }
            }
    }
}