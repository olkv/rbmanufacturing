package com.example.rbmanufacturing.presentation.otk.defect

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.HorizontalScrollView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.Orientation
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.network.getURLConnection
import com.example.rbmanufacturing.util.ImageUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

private const val ARG_TYPEDOC = "typedoc"
private const val ARG_UIDDOC = "uid_doc"
private const val ARG_DOCDATE = "docdate"
private const val ARG_DOCNUMBER = "docnumber"
private const val ARG_CODEITEM = "codeitem"
private const val ARG_NAMEITEM = "nameitem"
private const val ARG_COUNTITEM = "countitem"

class DefectFragment : Fragment() {

    private val viewModelEditDefect: EditDefectViewModel by activityViewModels()

    private lateinit var vmDefect:  DefectFragmentViewModel

    private var typedoc: String? = null
    private var uid_doc: String? = null
    private var docdate: String? = null
    private var docnumber: String? = null
    private var nameitem: String? = null
    private var codeitem: Int? = null
    private var countitem: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typedoc = it.getString(ARG_TYPEDOC)
            uid_doc = it.getString(ARG_UIDDOC)
            docdate = it.getString(ARG_DOCDATE)
            nameitem = it.getString(ARG_NAMEITEM)
            docnumber = it.getString(ARG_DOCNUMBER)
            codeitem = it.getInt(ARG_CODEITEM)
            countitem = it.getFloat(ARG_COUNTITEM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_defect, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("MyLOG","uid_doc = $uid_doc")
        Log.d("MyLOG","codeitem = $codeitem")

        viewModelEditDefect.clearResult()

        //Инициализируем фабрику модели и передаем параметр
        val vmDefectViewModelFactory = DefectFragmentViewModelFactory(getURLConnection(view.context), uid_doc!!, codeitem!!)

        //создаем ViewModel на основании произвольной фабрики
        vmDefect = ViewModelProvider(this, vmDefectViewModelFactory)[DefectFragmentViewModel::class.java]


        val txtOtkDocType = view.findViewById<TextView>(R.id.txtOtkDocType)
        val txtOtkDocNumDate = view.findViewById<TextView>(R.id.txtOtkDocNumDate)
        val txtNameItem = view.findViewById<TextView>(R.id.txtNameItem)
        val txtItemCount = view.findViewById<TextView>(R.id.txtItemCount)
        val btnAdd = view.findViewById<FloatingActionButton>(R.id.btnAddDefect)
        val btnGetPhoto = view.findViewById<FloatingActionButton>(R.id.btnGetPhoto)
        val progressBarDefect = view.findViewById<ProgressBar>(R.id.progressBarDefect)

        val rcvDefect = view.findViewById<RecyclerView>(R.id.rcvDefect)
        rcvDefect?.hasFixedSize()
        rcvDefect?.layoutManager = LinearLayoutManager(view.context)


        val rcvFotoDefect = view.findViewById<RecyclerView>(R.id.rcvFotoDefect)
        rcvFotoDefect?.hasFixedSize()
        rcvFotoDefect.layoutManager = LinearLayoutManager(view.context, HORIZONTAL,false)

        val adapterFoto = DefectImageFragmentAdapter(view.context) {itemPosition ->
            Log.d("MYLOG", "Selected $itemPosition image photo")


        }


        val adapter = DefectFragmentAdapter(view.context) {itemPosition ->
            Log.d("MYLOG","Defect position $itemPosition is selected. UID Defect is ${vmDefect.itemsOtk.value[itemPosition!!].uid_defect}")

            val strImages = vmDefect.getImages(itemPosition)

            adapterFoto.setItems(strImages)

        }


        //SWIPE (удаление) строки
        //НАЧАЛО БЛОКА
        val mCallBack = object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder,  direction: Int) {

                // More code here
                Log.d("MYLOG", "Delete position ${viewHolder.adapterPosition}")

            }
        }

        val itemTouchHelper = ItemTouchHelper(mCallBack)
        itemTouchHelper.attachToRecyclerView(rcvDefect)



        rcvDefect?.adapter = adapter

        rcvFotoDefect?.adapter = adapterFoto


        btnGetPhoto.setOnClickListener {
            val dlgAddImege = AddDefectImageFragment()
            dlgAddImege.show(parentFragmentManager, "Фотография")
        }


        btnAdd.setOnClickListener {

            viewModelEditDefect.clearResult()

            val bndPar = bundleOf(
                "count" to countitem?.toInt()
            )

            val dlgEditDefect = EditDefectFragment()
            dlgEditDefect.arguments = bndPar
            //dlgEditDefect.setTargetFragment(this, 1) //Старый метод, так сейчас не используется
            dlgEditDefect.show(parentFragmentManager, "Дефект")
        }


        txtOtkDocType.text = typedoc
        txtOtkDocNumDate.text = "${docnumber?.toInt().toString()} от $docdate"
        txtNameItem.text = nameitem
        txtItemCount.text = "Необходимо проверить ${countitem.toString()} шт."

        //Подписка на результат события сохранения
        lifecycleScope.launch {
            viewModelEditDefect.requestResult.collect { resDlg->
                Log.d("MYLOG", "Result whith DefectFragment - $resDlg")
            }
        }

        //Слушатель для изменения содержания документа
        //при изменении передает данные в адаптер
        lifecycleScope.launch {
            vmDefect.itemsOtk.collect {list ->
                //Log.d("MYLOG", "Количество записей: ${list.size.toString()}")
                adapter.setDocOtk(list)
            }
        }

        //Слушатель переменной выполнения запросов к HTTP сервису
        //при ожидании вызвается ProgreeBar, после завершения ProgressBar скрываем
        lifecycleScope.launch {
            vmDefect.isLoading.collect {isLoading ->
                if(isLoading) {
                    progressBarDefect.visibility = View.VISIBLE
                    progressBarDefect.animate().start()

                    //Отключаем сенсор для блокировки управления
                    activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                } else {
                    progressBarDefect.animate().cancel()
                    progressBarDefect.visibility = View.GONE

                    //Включаем сенсор
                    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
            }
        }

    }

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.d("MYLOG", "Result Code = $resultCode")

        val typedefect = data?.extras?.getString("typedefect")

        Log.d("MYLOG", "typedefect = $typedefect")

    }

     */


    companion object {
        @JvmStatic
        fun newInstance(typedoc: String, uid_doc: String, docdate:String, docnumber: String, codeitem: Int, nameitem: String, countitem: Float) =
            DefectFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TYPEDOC, typedoc)
                    putString(ARG_UIDDOC, uid_doc)
                    putString(ARG_DOCDATE, docdate)
                    putString(ARG_DOCNUMBER, docnumber)
                    putInt(ARG_CODEITEM, codeitem)
                    putString(ARG_NAMEITEM, nameitem)
                    putFloat(ARG_COUNTITEM, countitem)
                }
            }
    }
}