package com.example.rbmanufacturing.presentation.moveitemmanf

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.domain.models.CItemWarehouse
import com.example.rbmanufacturing.domain.repository.RowClickListiner
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MoveItemManfFragment : Fragment(), RowClickListiner {

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

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        val rcvItemMoveManf = view.findViewById<RecyclerView>(R.id.rcvItemMoveManf)
        val adapter = MoveItemManfAdapter(view.context, this)

        rcvItemMoveManf?.hasFixedSize()
        rcvItemMoveManf?.layoutManager = LinearLayoutManager(view.context)
        rcvItemMoveManf?.adapter = adapter

        lifecycleScope.launch {
            vmMoveItemMan.listItemWarehouse.collect {list ->
                //Log.d("MYLOG", "Количество записей: ${list.size.toString()}")
                adapter.setItemManf(list)
            }
        }


        lifecycleScope.launch {
            vmMoveItemMan.isLoading.collect {isLoading ->
                if(isLoading) {
                    progressBar.visibility = View.VISIBLE
                    progressBar.animate().start()
                    //Отключаем сенсор для блокировки управления
                    activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                } else {
                    progressBar.animate().cancel()
                    progressBar.visibility = View.GONE

                    //Включаем сенсор
                    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                }
            }
        }



        val btnPushItemsManf = view.findViewById<FloatingActionButton>(R.id.btnPushItemsManf)

        btnPushItemsManf.setOnClickListener {

            //Toast.makeText(it.context,"Push to 1C", Toast.LENGTH_SHORT).show()

            val t_itemListManf = adapter.t_items.filter {item->
                item.editcount>0
            } as MutableList<CItemWarehouse>

            if (t_itemListManf.isNotEmpty()) {
                val dlgYesNo = AlertDialog.Builder(it.context)
                dlgYesNo.setTitle("Перенос данных")
                dlgYesNo.setMessage("Перенести данные в 1С:ERP ?")
                dlgYesNo.setPositiveButton("Да") {dialog, id ->

                    vmMoveItemMan.pushSelectItemWarehouseManf(t_itemListManf)

                    dialog.cancel()
                }

                dlgYesNo.setNegativeButton("Нет") {dialog, id ->
                    dialog.cancel()
                }

                dlgYesNo.create()

                dlgYesNo.show()
            } else {
                Toast.makeText(it.context,"Ничего не выбрано.", Toast.LENGTH_SHORT).show()
            }

        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = MoveItemManfFragment()
    }

    override fun OnClick(rowid: Int) {
        Log.d("MYLOG","Click ROW id $rowid")
    }
}