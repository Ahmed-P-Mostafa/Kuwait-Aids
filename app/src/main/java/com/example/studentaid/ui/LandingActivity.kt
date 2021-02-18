package com.example.studentaid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.studentaid.R
import com.example.studentaid.ui.student.RegisterStudentActivity
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        btnStudent.setOnClickListener {
            startActivity(Intent(this,RegisterStudentActivity::class.java))
        }
        btnMinyEmployee.setOnClickListener {
            Toast.makeText(this,"navigate to register ministry employee",Toast.LENGTH_LONG).show()
        }
        btnUniEmployee.setOnClickListener {
            Toast.makeText(this,"navigate to register university employee",Toast.LENGTH_LONG).show()
        }
    }

    fun citizenLogin(view: View) {
        startActivity(Intent(this,CitizenLoginActivity::class.java))

    }
    fun employeeLogin(view: View) {}
}