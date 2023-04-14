package com.example.rbmanufacturing.presentation.otk.defect

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.rbmanufacturing.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

private const val ARG_TYPEDEFECT = "typedefect"
private const val ARG_VIDDEFECT = "viddefect"
private const val ARG_DESCRIPTION = "description"
private const val ARG_COUNT = "count"

class EditDefectFragment : BottomSheetDialogFragment() {

    private val viewModelEditDefect: EditDefectViewModel by activityViewModels()

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

        Log.d("MYLOG", "Count = $count")

        val spnVidDefect = view.findViewById<Spinner>(R.id.spnVidDefect)
        spnVidDefect.prompt = "Вид дефекта"

        //Группа переключателей, по умолчанию первый элемент
        val rgpTypeDefect = view.findViewById<RadioGroup>(R.id.rgpTypeDefect)
        rgpTypeDefect.check(R.id.rbtnRemoveble)

        val txtEditCount = view.findViewById<TextInputEditText>(R.id.editCount)
        val txtEditDescription = view.findViewById<TextInputEditText>(R.id.editTextDescription)


        val btnCancel = view.findViewById<Button>(R.id.btnCancel)
        btnCancel.setOnClickListener {
            dismiss()
        }


        val btnSave = view.findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {

            //Результат щаносим в Bundle и передаем его в вышестоящее активити (фрагмент)
            //метод устаревший рекомендуьт использовать LiveData и ViewModel
            //я думаю можно использовать StateFloat

            typedefect = "Устранимый"
            description = txtEditDescription.text.toString()

            count = 0
            if(txtEditCount.text.toString().isNotEmpty() && txtEditCount.text.toString().isDigitsOnly()) {
                count = txtEditCount.text.toString().toInt()
            }

            val resDlg = bundleOf(
                "typedefect" to typedefect,
                "description" to description,
                "count" to count
            )

            //activity?.intent?.putExtras(resDlg)

            //targetFragment?.onActivityResult(targetRequestCode, 1, activity?.intent)

            viewModelEditDefect.setParameters(resDlg) //Передача результата рекомендовынным способом

            //dismiss()
        }


        //Подписка на результат события сохранения
        lifecycleScope.launch {
            viewModelEditDefect.requestResult.collect { resDlg->
                if (resDlg=="OK") {
                    dismiss()
                }
                //Log.d("MYLOG", "Result whith EditDefectFragment - $resDlg")
            }
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