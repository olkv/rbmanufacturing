package com.example.rbmanufacturing.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R

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
        val btnGetOperation = findViewById<Button>(R.id.btnGetOperation)
        val rcvFunOperation = findViewById<RecyclerView>(R.id.rcvFunOperation)

        rcvFunOperation.hasFixedSize()
        rcvFunOperation.layoutManager = LinearLayoutManager(this)

        val listFunOperation = vmMain.GetListOperation()
        rcvFunOperation.adapter = FunOperationAdapter(listFunOperation, this)


        btnGetOperation.setOnClickListener {
            if(listFunOperation.size>0) {
                txtOperation.text = listFunOperation[listFunOperation.size-1].name
            }


        }

    }

}

