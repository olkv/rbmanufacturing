package com.example.rbmanufacturing.presentation.config

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.SharedValues
import com.example.rbmanufacturing.R
import com.google.android.material.textfield.TextInputEditText


class ConfigFragment : Fragment() {

    var userName: String = ""
    var userPassword: String = ""
    var urlConnect: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_config, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = view.context.getSharedPreferences("RbManConfig",Context.MODE_PRIVATE)

        urlConnect = sharedPreferences.getString("urlConnect","http://192.168.1.1/")!!
        userPassword = sharedPreferences.getString("userPassword","")!!
        userName = sharedPreferences.getString("userName","noname")!!

        val txtURLConnect = view.findViewById<TextInputEditText>(R.id.editURLConnect)
        txtURLConnect.setText(urlConnect)

        val txtUserName = view.findViewById<EditText>(R.id.editUserName)
        txtUserName.setText(userName)

        val txtUserPassword = view.findViewById<EditText>(R.id.editUserPassword)
        txtUserPassword.setText(userPassword)

        val btnSaveConfig = view.findViewById<Button>(R.id.btnSaveConfig)
        btnSaveConfig.setOnClickListener {
            val editConfig = sharedPreferences.edit()
            editConfig.putString("urlConnect", txtURLConnect.text.toString())
            editConfig.putString("userPassword",txtUserPassword.text.toString())
            editConfig.putString("userName",txtUserName.text.toString())
            Toast.makeText(it.context,"Настройки сохранены",Toast.LENGTH_SHORT).show()
            editConfig.commit()
        }


    }

    companion object {
        @JvmStatic
        fun newInstance() =  ConfigFragment()
    }
}