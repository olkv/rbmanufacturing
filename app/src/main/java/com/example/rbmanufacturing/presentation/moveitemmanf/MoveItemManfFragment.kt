package com.example.rbmanufacturing.presentation.moveitemmanf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import kotlinx.coroutines.launch

class MoveItemManfFragment : Fragment() {

    private lateinit var vmMoveItemMan: MoveItemManfViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_move_item_manf, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vmMoveItemMan = ViewModelProvider(this)[MoveItemManfViewModel::class.java]

        val rcvItemMoveManf = view?.findViewById<RecyclerView>(R.id.rcvItemMoveManf)
        val adapter = MoveItemManfAdapter(view.context)

        rcvItemMoveManf?.hasFixedSize()
        rcvItemMoveManf?.layoutManager = LinearLayoutManager(view.context)
        rcvItemMoveManf?.adapter = adapter

        lifecycleScope.launch {

            vmMoveItemMan.listItemWarehouse.collect {list ->

                //Log.d("MYLOG", "Количество записей: ${list.size.toString()}")
                adapter.setItemManf(list)

            }


        }


    }

    companion object {
        @JvmStatic
        fun newInstance() = MoveItemManfFragment()
    }
}