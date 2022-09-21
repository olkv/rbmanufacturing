package com.example.rbmanufacturing.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.data.repository.OperationRepositoryImpl
import com.example.rbmanufacturing.domain.usecase.GetListFunOperationUseCase

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

        btnGetOperation.setOnClickListener {

            val listFunOperation = vmMain.GetListOperation()

            if(listFunOperation.size>0) {
                txtOperation.text = listFunOperation[1].name
            }


        }

    }

}