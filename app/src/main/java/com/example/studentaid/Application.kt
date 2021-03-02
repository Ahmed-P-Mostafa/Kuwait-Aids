package com.example.studentaid

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.studentaid.base.MyFirebaseMessagingService
import com.example.studentaid.utils.Constants
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class Application: MultiDexApplication()  {
    private val TAG = "Application"
    override fun onCreate() {
        super.onCreate()

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
       token = it.token
            Log.d(TAG, "onCreate: ${it.token}")
        }

    }


    fun isUserLogidIn():Boolean{
        return false
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    companion object{

        val sharedPref : SharedPreferences?=null

        var token :String?
            get() {
                Log.d("MyFirebaseMessagingServ", ": ${sharedPref?.getString("token","")}")
                return sharedPref?.getString("token","")
            }
            set(value) {
                Log.d("MyFirebaseMessagingServ", "$value: ")
                sharedPref?.edit()?.putString("token",value)?.apply()
            }
    }

}