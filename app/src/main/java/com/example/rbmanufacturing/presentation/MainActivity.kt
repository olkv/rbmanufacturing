package com.example.rbmanufacturing.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rbmanufacturing.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

    }

    fun init() {

        //val txtOperation = findViewById<TextView>(R.id.txtOperation)

        //Находим основной нафигационный контролер (область куда помещаем фрагменты)


        val navController = findNavController(R.id.fragmentContainerView)


        //Находим нижнее меню
        val navBottom: BottomNavigationView = findViewById(R.id.navBottom)

        //Заполняем настройки для стыковки пунктов в нижнем меню и навигационным контролером
        //Выбираем только нужные нам фрагменты
        //Не заметил чтобы количество фрагментов оказывало влияние на поведение, но без переменной конфигурации
        //не отображается стрелка Назад
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.operationFragment, R.id.configFragment))

        //Переносим настройки в навигационный контролер
        setupActionBarWithNavController(navController, appBarConfiguration)

        //Связваем навигационный контролер с нижнем меню
        navBottom.setupWithNavController(navController)


        /*
        supportFragmentManager.beginTransaction()
            .replace(R.id.viewMain, OperationFragment.newInstance())
            .commit()

         */

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}

