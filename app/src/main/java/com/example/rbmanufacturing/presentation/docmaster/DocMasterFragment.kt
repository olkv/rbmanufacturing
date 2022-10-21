package com.example.rbmanufacturing.presentation.docmaster

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.domain.repository.RowChangeListiner
import com.example.rbmanufacturing.domain.repository.RowClickListiner
import com.example.rbmanufacturing.network.getURLConnection
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

private const val ARG_UID = "uid"
private const val ARG_DOCNUMBER = "docnumber"
private const val ARG_DOCDATE = "docdate"

class DocMasterFragment : Fragment(), RowClickListiner, RowChangeListiner {
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

        //Инициализируем фабрику модели и передаем параметр
        val vmDocMasterViewModelFactory = DocMasterViewModelFactory(uid!!, getURLConnection(view.context))
        //создаем ViewModel на основании произвольной фабрики
        vmDocMaster = ViewModelProvider(this, vmDocMasterViewModelFactory)[DocMasterViewModel::class.java]


        val progressBarDocMaster = view.findViewById<ProgressBar>(R.id.progressBarDocMaster)
        val txtNameDoc = view.findViewById<TextView>(R.id.txtNameDoc)
        val btnCloseDocMaster = view.findViewById<FloatingActionButton>(R.id.btnCloseDocMaster)
        val btnSendDocMasterTo1C = view.findViewById<FloatingActionButton>(R.id.btnSendDocMasterTo1C)

        val rcvDocMaster = view.findViewById<RecyclerView>(R.id.rcvDocMaster)
        rcvDocMaster?.hasFixedSize()
        rcvDocMaster?.layoutManager = LinearLayoutManager(view.context)

        txtNameDoc.text = "$docnumber от $docdate"

        val adapter = DocMasterAdapter(view.context, this, this)
        rcvDocMaster?.adapter = adapter

        lifecycleScope.launch {
            vmDocMaster.docMaster.collect {list ->
                //Log.d("MYLOG", "Количество записей: ${list.size.toString()}")
                adapter.setDocMaster(list)
            }
        }


        lifecycleScope.launch {
            vmDocMaster.isLoading.collect {isLoading ->
                if(isLoading) {
                    progressBarDocMaster.visibility = View.VISIBLE
                    progressBarDocMaster.animate().start()

                    //Отключаем сенсор для блокировки управления
                    activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                } else {
                    progressBarDocMaster.animate().cancel()
                    progressBarDocMaster.visibility = View.GONE

                    //Включаем сенсор
                    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
            }
        }

        lifecycleScope.launch {
            vmDocMaster.isCloseDocMaster.collect {isClose ->
                if(isClose) {
                    findNavController().navigate(R.id.action_docMasterFragment_to_operationMaster)
                }
            }
        }

        lifecycleScope.launch {
            vmDocMaster.enableCloseDocMaster.collect {isEnable ->
                btnCloseDocMaster.isVisible = isEnable
            }
        }

        //vmDocMaster.getDocument()

        btnSendDocMasterTo1C.setOnClickListener {
            val t_docmaster = adapter.t_items

            if(t_docmaster.isNotEmpty()) {

                val dlgYesNo = AlertDialog.Builder(it.context)
                dlgYesNo.setTitle("Перенос данных")
                dlgYesNo.setMessage("Сохранить изменения в 1С:ERP ?")
                dlgYesNo.setPositiveButton("Да") {dialog, id ->


                    vmDocMaster.updateDocMaster(docMaster = t_docmaster)

                    dialog.cancel()
                }

                dlgYesNo.setNegativeButton("Нет") {dialog, id ->
                    dialog.cancel()
                }

                dlgYesNo.create()

                dlgYesNo.show()

            }

        }

        btnCloseDocMaster.setOnClickListener {

            val dlgYesNo = AlertDialog.Builder(it.context)
            dlgYesNo.setTitle("Закрытие отчета мастера смены")
            dlgYesNo.setMessage("Закрыть отчет мастера смены ?")
            dlgYesNo.setIcon(R.drawable.ic_commit)
            dlgYesNo.setPositiveButton("Да") {dialog, id ->

                dialog.cancel()
                vmDocMaster.closeDocMaster()

            }


            dlgYesNo.setNegativeButton("Нет") {dialog, id ->
                dialog.cancel()
            }

            dlgYesNo.create()

            dlgYesNo.show()


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

    override fun OnClick(rowid: Int) {
        Log.d("MYLOG","Click ROW id $rowid")
    }

    // CallBack функция признак изменения строки, для того чтобы скрыть кнопку Закрытия отчета
    override fun onChange(rowid: Int) {
        vmDocMaster.editRow(rowid = rowid)
    }

}