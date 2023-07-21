package com.example.rbmanufacturing.presentation.otk.defect

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.rbmanufacturing.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_IMAGE_STR = "imageStr"

class PreviewImageDefectFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var imageStr: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageStr = it.getString(ARG_IMAGE_STR)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preview_image_defect, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgDefect = view.findViewById<ImageView>(R.id.imgDefect)

        val decodeBytes = Base64.decode(imageStr, Base64.DEFAULT)
        val bm = BitmapFactory.decodeByteArray(decodeBytes, 0, decodeBytes.size-1)

        imgDefect.setImageBitmap(bm)


    }


    companion object {

        @JvmStatic
        fun newInstance(imageStr: String) =
            PreviewImageDefectFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_IMAGE_STR, imageStr)
                }
            }
    }
}