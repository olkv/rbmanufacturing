package com.example.rbmanufacturing.presentation.otk.defect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rbmanufacturing.R

private const val ARG_TYPEDOC = "type_doc"
private const val ARG_UIDDOC = "uid_doc"
private const val ARG_UIDNOM = "uidnom"

class DefectFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var type_doc: String? = null
    private var uid_doc: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type_doc = it.getString(ARG_TYPEDOC)
            uid_doc = it.getString(ARG_UIDDOC)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_defect, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(type_doc: String, uid_doc: String) =
            DefectFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TYPEDOC, type_doc)
                    putString(ARG_UIDDOC, uid_doc)
                }
            }
    }
}