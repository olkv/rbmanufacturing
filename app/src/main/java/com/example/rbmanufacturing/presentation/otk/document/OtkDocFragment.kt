package com.example.rbmanufacturing.presentation.otk.document

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
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.network.getURLConnection
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

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
        val btnCloseDocMaster = view.findViewById<FloatingActionButton>(R.id.btnCloseDocMaster)

        val progressBarOtkDoc = view.findViewById<ProgressBar>(R.id.progressBarOtkDoc)

        txtOtkDocType.text = typedoc
        txtOtkDocNumDate.text = "${docnumber?.toInt().toString()} от $docdate"


        val rcvOtkDoc = view.findViewById<RecyclerView>(R.id.rcvOtkDoc)
        rcvOtkDoc?.hasFixedSize()
        rcvOtkDoc?.layoutManager = LinearLayoutManager(view.context)

        val adapter = OtkDocAdapter(view.context) { rowid ->

            val codeitem = vmOtkDoc.docOtk.value[rowid!!].codestr
            val nameitem = vmOtkDoc.docOtk.value[rowid!!].name
            val countitem = vmOtkDoc.docOtk.value[rowid!!].count.toFloat()

            Log.d("MYLOG", "Click ROWID=$rowid  TypeDoc=$typedoc  NameItem=$nameitem")

            //Вызываем фрагмент редактирования дефектов
            val bundle = bundleOf(
                "typedoc" to typedoc,
                "uid" to uid,
                "docnumber" to docnumber,
                "docdate" to docdate,
                "codeitem" to codeitem,
                "nameitem" to nameitem,
                "countitem" to countitem
            )

            findNavController().navigate(R.id.action_otkDocFragment_to_defectFragment, bundle)


        }

        rcvOtkDoc?.adapter = adapter

        //Слушаитель изменения переменной результата вызова функции к сервису
        lifecycleScope.launch {
            vmOtkDoc.requestResult.collect {result ->
                if(result.isNotEmpty()) {
                    Toast.makeText(context, result, Toast.LENGTH_LONG).show()
                }
            }
        }


        //Слушатель для изменения содержания документа
        //при изменении передает данные в адаптер
        lifecycleScope.launch {
            vmOtkDoc.docOtk.collect {list ->
                //Log.d("MYLOG", "Количество записей: ${list.size.toString()}")
                adapter.setDocOtk(list)
            }
        }

        //Слушатель переменной выполнения запросов к HTTP сервису
        //при ожидании вызвается ProgreeBar, после завершения ProgressBar скрываем
        lifecycleScope.launch {
            vmOtkDoc.isLoading.collect {isLoading ->
                if(isLoading) {
                    progressBarOtkDoc.visibility = View.VISIBLE
                    progressBarOtkDoc.animate().start()

                    //Отключаем сенсор для блокировки управления
                    activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                } else {
                    progressBarOtkDoc.animate().cancel()
                    progressBarOtkDoc.visibility = View.GONE

                    //Включаем сенсор
                    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
            }
        }


        //Нажатие кнопки Закрытия отчета мастера
        btnCloseDocMaster.setOnClickListener {

            val dlgYesNo = AlertDialog.Builder(it.context)
            dlgYesNo.setTitle("Закрытие отчета мастера смены")
            dlgYesNo.setMessage("Закрыть отчет мастера смены ?")
            dlgYesNo.setIcon(R.drawable.ic_commit)
            dlgYesNo.setPositiveButton("Да") {dialog, id ->

                dialog.cancel()
                //vmDocMaster.closeDocMaster()

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