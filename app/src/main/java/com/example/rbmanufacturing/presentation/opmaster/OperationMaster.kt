package com.example.rbmanufacturing.presentation.opmaster

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.domain.repository.RowClickListiner
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

        vmOperationMaster = ViewModelProvider(this)[OperationMasterViewModel::class.java]


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


        /*
        Вы можете использовать GridLayoutManager в классе адаптера. используйте его в adapter constracter.Второй параметр - количество столбцов.

        mGridLayoutManager=new GridLayoutManager(context,2);
        позже установите Layoutmanager в классе адаптера.Вы можете использовать этот код.

        @Override
        public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            recyclerView.setLayoutManager(mGridLayoutManager);
        }

         */

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