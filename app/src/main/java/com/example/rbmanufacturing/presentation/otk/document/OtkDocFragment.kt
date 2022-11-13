package com.example.rbmanufacturing.presentation.otk.document

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.network.getURLConnection
import com.example.rbmanufacturing.presentation.docmaster.DocMasterViewModel

private const val ARG_TYPEDOC = "typedoc"
private const val ARG_UID = "uid"
private const val ARG_DOCNUMBER = "docnumber"
private const val ARG_DOCDATE = "docdate"

/**
 * A simple [Fragment] subclass.
 * Use the [OtkDocFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OtkDocFragment : Fragment() {

    private var typedoc: String? = null
    private var uid: String? = null
    private var docnumber: String? = null
    private var docdate: String? = null

    private lateinit var vmOtkDoc:  OtkDocFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typedoc = it.getString(ARG_TYPEDOC)
            uid = it.getString(ARG_UID)
            docnumber = it.getString(ARG_DOCNUMBER)
            docdate = it.getString(ARG_DOCDATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_otk_doc, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Инициализируем фабрику модели и передаем параметр
        val vmOtkDocViewModelFactory = OtkDocFragmantViewModelFactory(typedoc!!, uid!!, getURLConnection(view.context))
        //создаем ViewModel на основании произвольной фабрики
        vmOtkDoc = ViewModelProvider(this, vmOtkDocViewModelFactory)[OtkDocFragmentViewModel::class.java]


        val txtOtkDocType = view.findViewById<TextView>(R.id.txtOtkDocType)
        val txtOtkDocNumDate = view.findViewById<TextView>(R.id.txtOtkDocNumDate)

        val progressBarOtkDoc = view.findViewById<ProgressBar>(R.id.progressBarOtkDoc)

        txtOtkDocType.text = typedoc
        txtOtkDocNumDate.text = "${docnumber?.toInt().toString()} от $docdate"


        val rcvOtkDoc = view.findViewById<RecyclerView>(R.id.rcvOtkDoc)
        rcvOtkDoc?.hasFixedSize()
        rcvOtkDoc?.layoutManager = LinearLayoutManager(view.context)

        val adapter = OtkDocAdapter(view.context) { rowid ->

            Log.d("MYLOG", "Click ROWID=$rowid")

        }


    }

    companion object {
        @JvmStatic
        fun newInstance(typedoc: String, uid: String, docnumber: String, docdate: String) =
            OtkDocFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TYPEDOC, typedoc)
                    putString(ARG_UID, uid)
                    putString(ARG_DOCNUMBER, docnumber)
                    putString(ARG_DOCDATE, docdate)
                }
            }
    }
}