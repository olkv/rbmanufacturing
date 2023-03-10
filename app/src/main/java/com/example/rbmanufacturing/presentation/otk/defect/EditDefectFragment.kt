package com.example.rbmanufacturing.presentation.otk.defect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import com.example.rbmanufacturing.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val ARG_TYPEDEFECT = "typedefect"
private const val ARG_VIDDEFECT = "viddefect"
private const val ARG_DESCRIPTION = "description"
private const val ARG_COUNT = "count"

class EditDefectFragment : BottomSheetDialogFragment() {
    private var typedefect: String? = null
    private var viddefect: String? = null
    private var description: String? = null
    private var count: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typedefect = it.getString(ARG_TYPEDEFECT)
            viddefect = it.getString(ARG_VIDDEFECT)
            description = it.getString(ARG_DESCRIPTION)
            count = it.getInt(ARG_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_defect, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spnVidDefect = view.findViewById<Spinner>(R.id.spnVidDefect)
        spnVidDefect.prompt = "Вид дефекта"

        //Группа переключателей, по умолчанию первый элемент
        val rgpTypeDefect = view.findViewById<RadioGroup>(R.id.rgpTypeDefect)
        rgpTypeDefect.check(R.id.rbtnRemoveble)

        val btnCancel = view.findViewById<Button>(R.id.btnCancel)
        btnCancel.setOnClickListener {
            dismiss()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(typedefect: String, viddefect: String, description: String, count: Int) =
            EditDefectFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TYPEDEFECT, typedefect)
                    putString(ARG_VIDDEFECT, viddefect)
                    putString(ARG_DESCRIPTION, description)
                    putInt(ARG_COUNT, count)
                }
            }
    }
}