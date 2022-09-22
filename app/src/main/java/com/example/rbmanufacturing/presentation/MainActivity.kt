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

        vmMain = ViewModelProvider(this)[MainViewModel::class.java]

        val txtOperation = findViewById<TextView>(R.id.txtOperation)

        val rcvFunOperation = findViewById<RecyclerView>(R.id.rcvFunOperation)
        val adapter = FunOperationAdapter(this)


        rcvFunOperation.hasFixedSize()
        rcvFunOperation.layoutManager = LinearLayoutManager(this)
        rcvFunOperation.adapter = adapter

        lifecycleScope.launch {
            vmMain.listFunOperation.collect {

                val listFunOperation = vmMain.GetListOperation()
                adapter.setOperationArray(listFunOperation)

            }
        }


    }

}

