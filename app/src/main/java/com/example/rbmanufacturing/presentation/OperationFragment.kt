package com.example.rbmanufacturing.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import kotlinx.coroutines.launch

class OperationFragment : Fragment(), ItemClickListener {

    private lateinit var vmMain: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_operation, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vmMain = ViewModelProvider(this)[MainViewModel::class.java]

        val rcvFunOperation = view?.findViewById<RecyclerView>(R.id.rcvFunOperation)
        val adapter = FunOperationAdapter(view.context, this)

        rcvFunOperation?.hasFixedSize()
        rcvFunOperation?.layoutManager = LinearLayoutManager(view.context)
        rcvFunOperation?.adapter = adapter

        lifecycleScope.launch {
            vmMain.listFunOperation.collect {list ->

                //Log.d("MYLOG", "Количество записей: ${list.size.toString()}")
                adapter.setOperationArray(list)

            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = OperationFragment()
        }

    override fun OnClick(code: String) {
        Log.d("MYLOG","Click on fragment $code")

        if(code=="00001")
            findNavController().navigate(R.id.action_operationFragment_to_operationMaster)

        if(code=="00002")
            findNavController().navigate(R.id.action_operationFragment_to_moveItemManfFragment)

    }

}