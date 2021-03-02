package com.example.studentaid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.studentaid.R
import com.example.studentaid.ui.ministryEmployee.SignupMinistryActivity
import com.example.studentaid.ui.student.RegisterStudentActivity
import com.example.studentaid.ui.universityEmployee.UniversitySignupActivity
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        btnStudent.setOnClickListener {
            startActivity(Intent(this,RegisterStudentActivity::class.java))
        }
        btnMinyEmployee.setOnClickListener {
            startActivity(Intent(this,SignupMinistryActivity::class.java))

        }
        btnUniEmployee.setOnClickListener {
            startActivity(Intent(this,UniversitySignupActivity::class.java))
        }
    }

    fun citizenLogin(view: View) {
        startActivity(Intent(this,CitizenLoginActivity::class.java))

    }
    fun employeeLogin(view: View) {
        startActivity(Intent(this,EmployeeLoginActivity::class.java))

    }
}