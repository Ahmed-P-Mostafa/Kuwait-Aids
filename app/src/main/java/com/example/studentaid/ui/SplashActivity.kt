package com.example.studentaid.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat.postDelayed
import com.example.studentaid.R
import com.example.studentaid.ui.graduate.HomeGraduateActivity
import com.example.studentaid.ui.ministryEmployee.MinistryHomeActivity
import com.example.studentaid.ui.student.HomeStudentActivity
import com.example.studentaid.ui.universityEmployee.UniversityMainActivity
import com.example.studentaid.utils.Constants

class SplashActivity : AppCompatActivity() {
    private  val TAG = "SplashActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler().postDelayed(Runnable {
            isUserLoggedIn()
            finish()
        },3000)


    }
    fun isUserLoggedIn(){
        val sp = getSharedPreferences(Constants.PERSON_SHARED_PREFRENCES_FILE_NAME, Context.MODE_PRIVATE)

        if (!sp.getBoolean(Constants.IS_USER_SIGNED_IN,false)){
            Log.d(TAG, "isUserLoggedIn: ${sp.getBoolean(Constants.IS_USER_SIGNED_IN,false)}")
            Log.d(TAG, "isUserLoggedIn: ${sp.getString(Constants.USER_CONDITION,Constants.NULL_VALUE)}")
            startActivity(Intent(this,LandingActivity::class.java))
        }else{
            when(sp.getString(Constants.USER_TITLE_KEY,Constants.NULL_VALUE)){
                "Student"->{
                    startActivity(Intent(this, HomeStudentActivity::class.java))
                }
                "Graduate"->{
                    startActivity(Intent(this, HomeGraduateActivity::class.java))
                }
                "University"->{
                    startActivity(Intent(this, UniversityMainActivity::class.java))

                }
                "Ministry"->{
                    startActivity(Intent(this, MinistryHomeActivity::class.java))


                }
            }

        }
    }
}