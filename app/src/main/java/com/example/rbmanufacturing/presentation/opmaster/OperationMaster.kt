package com.example.rbmanufacturing.presentation.opmaster

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.domain.repository.RowClickListiner
import com.example.rbmanufacturing.network.getIdTelephone
import com.example.rbmanufacturing.network.getURLConnection
import com.example.rbmanufacturing.network.getUserName
import com.example.rbmanufacturing.presentation.moveitemmanf.MoveItemManfViewModelFactory
import kotlinx.coroutines.launch

class OperationMaster : Fragment(), RowClickListiner {

    private lateinit var vmOperationMaster: OperationMasterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_operation_master, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val IMEI = getIdTelephone(view.context)
        Log.d("MYLOG","IMEI = $IMEI")

        val vmOperationMasterViewModelFactory = OperationMasterViewModelFactory(getUserName(view.context), getURLConnection(view.context))
        vmOperationMaster = ViewModelProvider(this, vmOperationMasterViewModelFactory)[OperationMasterViewModel::class.java]

        val progressBarMasterOperation = view.findViewById<ProgressBar>(R.id.progressBarMasterOperation)

        val rcvOperationMaster = view.findViewById<RecyclerView>(R.id.rcvOperationMaster)
        val adaptorOperationMaster = OperationMasterAdapter(view.context, this)

        rcvOperationMaster?.hasFixedSize()

        if(resources.configuration.orientation== Configuration.ORIENTATION_PORTRAIT) {
            rcvOperationMaster?.layoutManager = GridLayoutManager(view.context,2) //сетка с 2-мя колонками
        } else {
            rcvOperationMaster?.layoutManager = GridLayoutManager(view.context,3)  //сетка с 3-мя колонками
        }


        rcvOperationMaster?.adapter = adaptorOperationMaster


        lifecycleScope.launch {
            vmOperationMaster.requestResult.collect {result ->
                if(result.isNotEmpty()) {
                    Toast.makeText(context, result, Toast.LENGTH_LONG).show()
                }
            }
        }

        lifecycleScope.launch {
            vmOperationMaster.listItemOperation.collect {list ->
                //Log.d("MYLOG", "Количество записей: ${list.size.toString()}")
                adaptorOperationMaster.setItemsOperationMaster(list)
            }
        }


        lifecycleScope.launch {
            vmOperationMaster.isLoading.collect {isLoading ->
                if(isLoading) {
                    progressBarMasterOperation.visibility = View.VISIBLE
                    progressBarMasterOperation.animate().start()
                } else {
                    progressBarMasterOperation.animate().cancel()
                    progressBarMasterOperation.visibility = View.GONE
                }
            }
        }


    }

    companion object {
        @JvmStatic
        fun newInstance() = OperationMaster()
    }

    override fun OnClick(rowid: Int) {
        Log.d("MYLOG","Click ROW id $rowid")

        val bundle = bundleOf(
                    "uid" to vmOperationMaster.listItemOperation.value[rowid].uid,
                    "docnumber" to vmOperationMaster.listItemOperation.value[rowid].number,
                    "docdate" to vmOperationMaster.listItemOperation.value[rowid].date
                )

        findNavController().navigate(R.id.action_operationMaster_to_docMasterFragment, bundle)
    }


}