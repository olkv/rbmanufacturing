package com.example.rbmanufacturing.presentation.otk.defect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.network.getURLConnection

private const val ARG_TYPEDOC = "typedoc"
private const val ARG_UIDDOC = "uid_doc"
private const val ARG_DOCDATE = "docdate"
private const val ARG_DOCNUMBER = "docnumber"
private const val ARG_CODEITEM= "codeitem"
private const val ARG_NAMEITEM= "nameitem"
private const val ARG_COUNTITEM= "countitem"

class DefectFragment : Fragment() {

    private lateinit var vmDefect:  DefectFragmentViewModel

    private var typedoc: String? = null
    private var uid_doc: String? = null
    private var docdate: String? = null
    private var docnumber: String? = null
    private var nameitem: String? = null
    private var codeitem: Int? = null
    private var countitem: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typedoc = it.getString(ARG_TYPEDOC)
            uid_doc = it.getString(ARG_UIDDOC)
            docdate = it.getString(ARG_DOCDATE)
            nameitem = it.getString(ARG_NAMEITEM)
            docnumber = it.getString(ARG_DOCNUMBER)
            codeitem = it.getInt(ARG_CODEITEM)
            countitem = it.getFloat(ARG_COUNTITEM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_defect, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Инициализируем фабрику модели и передаем параметр
        val vmDefectViewModelFactory = DefectFragmentViewModelFactory(getURLConnection(view.context))
        //создаем ViewModel на основании произвольной фабрики
        vmDefect = ViewModelProvider(this, vmDefectViewModelFactory)[DefectFragmentViewModel::class.java]


        val txtOtkDocType = view.findViewById<TextView>(R.id.txtOtkDocType)
        val txtOtkDocNumDate = view.findViewById<TextView>(R.id.txtOtkDocNumDate)
        val txtNameItem = view.findViewById<TextView>(R.id.txtNameItem)
        val txtItemCount = view.findViewById<TextView>(R.id.txtItemCount)

        txtOtkDocType.text = typedoc
        txtOtkDocNumDate.text = "${docnumber?.toInt().toString()} от $docdate"
        txtNameItem.text = nameitem
        txtItemCount.text = "Необходимо проверить ${countitem.toString()} шт."

    }


    companion object {
        @JvmStatic
        fun newInstance(typedoc: String, uid_doc: String, docdate:String, docnumber: String, codeitem: Int, nameitem: String, countitem: Float) =
            DefectFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TYPEDOC, typedoc)
                    putString(ARG_UIDDOC, uid_doc)
                    putString(ARG_DOCDATE, docdate)
                    putString(ARG_DOCNUMBER, docnumber)
                    putInt(ARG_CODEITEM, codeitem)
                    putString(ARG_NAMEITEM, nameitem)
                    putFloat(ARG_COUNTITEM, countitem)
                }
            }
    }
}