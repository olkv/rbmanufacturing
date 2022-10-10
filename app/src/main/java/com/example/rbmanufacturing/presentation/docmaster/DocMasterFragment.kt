package com.example.rbmanufacturing.presentation.docmaster

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.presentation.opmaster.OperationMasterViewModel
import kotlinx.coroutines.launch

private const val ARG_UID = "uid"
private const val ARG_DOCNUMBER = "docnumber"
private const val ARG_DOCDATE = "docdate"

class DocMasterFragment : Fragment() {
    private var uid: String? = null
    private var docnumber: String? = null
    private var docdate: String? = null

    private lateinit var vmDocMaster:  DocMasterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uid = it.getString(ARG_UID)
            docnumber = it.getString(ARG_DOCNUMBER)
            docdate = it.getString(ARG_DOCDATE)
        }

        Log.d("MYLOG","UID document = $uid")
        Log.d("MYLOG","DOCNUMBER document = $docnumber")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doc_master, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vmDocMaster = ViewModelProvider(this)[DocMasterViewModel::class.java]

        vmDocMaster.setUIDDoc(uid = uid!!)

        val progressBarDocMaster = view.findViewById<ProgressBar>(R.id.progressBarDocMaster)
        val txtNameDoc = view.findViewById<TextView>(R.id.txtNameDoc)

        val rcvDocMaster = view.findViewById<RecyclerView>(R.id.rcvDocMaster)
        rcvDocMaster?.hasFixedSize()
        rcvDocMaster?.layoutManager = LinearLayoutManager(view.context)

        txtNameDoc.text = "$docnumber от $docdate"


        lifecycleScope.launch {
            vmDocMaster.isLoading.collect {isLoading ->
                if(isLoading) {
                    progressBarDocMaster.visibility = View.VISIBLE
                    progressBarDocMaster.animate().start()
                } else {
                    progressBarDocMaster.animate().cancel()
                    progressBarDocMaster.visibility = View.GONE
                }
            }
        }



    }


    companion object {
        @JvmStatic
        fun newInstance(uid: String, docnumber: String, docdate: String) =
            DocMasterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_UID, uid)
                    putString(ARG_DOCNUMBER, docnumber)
                    putString(ARG_DOCDATE, docdate)
                }
            }
    }
}