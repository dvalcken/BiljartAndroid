package com.example.biljart

import android.app.Application
import com.example.biljart.data.AppContainer
import com.example.biljart.data.DefaultAppContainer

class BiljartApplication : Application() { // should implement the Application class (Les 8 21'30")
    lateinit var appContainer: AppContainer

    override fun onCreate() { // ctrl+o to override the onCreate method
        super.onCreate()
        appContainer = DefaultAppContainer() // call constructor of DefaultAppContainer and assign it to appContainer
    }
}
