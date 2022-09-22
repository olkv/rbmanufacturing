package com.example.rbmanufacturing.presentation

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var vmMain: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

    }

    private fun init() {

        val m_Context = this

        vmMain = ViewModelProvider(this)[MainViewModel::class.java]

        val txtOperation = findViewById<TextView>(R.id.txtOperation)
        //val btnGetOperation = findViewById<Button>(R.id.btnGetOperation)
        val rcvFunOperation = findViewById<RecyclerView>(R.id.rcvFunOperation)

        rcvFunOperation.hasFixedSize()
        rcvFunOperation.layoutManager = LinearLayoutManager(m_Context)

        lifecycleScope.launch {
            vmMain.listFunOperation.collect {

                val listFunOperation = vmMain.GetListOperation()
                rcvFunOperation.adapter = FunOperationAdapter(listFunOperation, m_Context)

            }
        }

        /*
        btnGetOperation.setOnClickListener {

            /*
            if(listFunOperation.size>0) {
                txtOperation.text = listFunOperation[listFunOperation.size-1].name
            }
            */

        }

         */

    }

}

