package com.example.studentaid

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class Application: MultiDexApplication()  {
    override fun onCreate() {
        super.onCreate()
    }


    fun isUserLogidIn():Boolean{
        return false
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}