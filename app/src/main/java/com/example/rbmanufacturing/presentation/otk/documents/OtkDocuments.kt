package com.example.rbmanufacturing.presentation.otk.documents

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.domain.repository.RowClickListiner
import com.example.rbmanufacturing.network.getURLConnection
import com.example.rbmanufacturing.network.getUserName
import com.example.rbmanufacturing.presentation.opmaster.OperationMasterAdapter
import kotlinx.coroutines.launch

class OtkDocuments : Fragment(), RowClickListiner {

    private lateinit var vmOtkDocuments: OtkDocumentsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_otk_documents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vmOtkDocumentsViewModelFactory = OtkDocumentsViewModelFactory(getUserName(view.context), getURLConnection(view.context))
        vmOtkDocuments = ViewModelProvider(this, vmOtkDocumentsViewModelFactory)[OtkDocumentsViewModel::class.java]

        val progressBarOtkDocuments = view.findViewById<ProgressBar>(R.id.progressBarOtkDocuments)

        val rcvOtkDocuments = view.findViewById<RecyclerView>(R.id.rcvOtkDocuments)
        val adaptorOtkDocuments = OtkDocumentsAdapter(view.context, this)

        rcvOtkDocuments?.hasFixedSize()


        if(resources.configuration.orientation== Configuration.ORIENTATION_PORTRAIT) {
            rcvOtkDocuments?.layoutManager = GridLayoutManager(view.context,2) //сетка с 2-мя колонками
        } else {
            rcvOtkDocuments?.layoutManager = GridLayoutManager(view.context,3)  //сетка с 3-мя колонками
        }


        rcvOtkDocuments?.adapter = adaptorOtkDocuments


        lifecycleScope.launch {
            vmOtkDocuments.listItem.collect {list ->
                //Log.d("MYLOG", "Количество записей: ${list.size.toString()}")
                adaptorOtkDocuments.setItemsOtkDocuments(list)
            }
        }

        lifecycleScope.launch {
            vmOtkDocuments.requestResult.collect {result ->
                if(result.isNotEmpty()) {
                    Toast.makeText(context, result, Toast.LENGTH_LONG).show()
                }
            }
        }


        lifecycleScope.launch {
            vmOtkDocuments.isLoading.collect {isLoading ->
                if(isLoading) {
                    progressBarOtkDocuments.visibility = View.VISIBLE
                    progressBarOtkDocuments.animate().start()
                } else {
                    progressBarOtkDocuments.animate().cancel()
                    progressBarOtkDocuments.visibility = View.GONE
                }
            }
        }


    }

    override fun OnClick(rowid: Int) {
        val type_doc = vmOtkDocuments.listItem.value[rowid].type_doc
        val uid = vmOtkDocuments.listItem.value[rowid].uid

        Log.d("MYLOG", "Click type_doc=$type_doc, uid=$uid")
    }


    companion object {
        @JvmStatic
        fun newInstance() = OtkDocuments()
    }


}