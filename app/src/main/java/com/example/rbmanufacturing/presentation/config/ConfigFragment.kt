package com.example.rbmanufacturing.presentation.config

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.rbmanufacturing.R
import com.google.android.material.textfield.TextInputEditText


class ConfigFragment : Fragment() {

    var userName: String = ""
    var userPassword: String = ""
    var urlConnect: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userName = "oleg"
        userPassword = "12345"
        urlConnect = "http://192.168.1.1/"

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

        val txtURLConnect = view.findViewById<TextInputEditText>(R.id.editURLConnect)
        txtURLConnect.setText(urlConnect)

        val txtUserName = view.findViewById<EditText>(R.id.editUserName)
        txtUserName.setText(userName)

        val txtUserPassword = view.findViewById<EditText>(R.id.editUserPassword)
        txtUserPassword.setText(userPassword)

    }

    companion object {
        @JvmStatic
        fun newInstance() =  ConfigFragment()
    }
}