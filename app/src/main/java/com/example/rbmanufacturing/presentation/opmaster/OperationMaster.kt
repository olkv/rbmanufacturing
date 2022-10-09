package com.example.rbmanufacturing.presentation.opmaster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R

class OperationMaster : Fragment() {

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

        val rcvOperationMaster = view.findViewById<RecyclerView>(R.id.rcvOperationMaster)

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



}