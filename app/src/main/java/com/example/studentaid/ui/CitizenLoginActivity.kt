package com.example.studentaid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import com.example.studentaid.R
import com.example.studentaid.base.BaseActivity
import com.example.studentaid.data.Student
import com.example.studentaid.data.onlineDatabase.OnlineDatabase
import com.example.studentaid.data.onlineDatabase.StudentDao
import com.example.studentaid.ui.graduate.HomeGraduateActivity
import com.example.studentaid.ui.student.HomeStudentActivity
import com.example.studentaid.utils.Utils
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_citizen_login.*

class CitizenLoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citizen_login)
    }

    fun Login(view: View) {
        showLoader("Loading...")
        if (isDataFilled()){
           when(radioGroup.checkedRadioButtonId){
               R.id.rbStudent->{
                   signInAsStudent()
               }
               R.id.rbGraduate->{
                   signInAdGraduate()
               }
           }
        }
        hideLoader()
    }
    private fun isDataFilled():Boolean{
        return !(etEmail.text.toString().isNullOrBlank()||etPassword.text.toString().isNullOrBlank())
    }
    private fun signInAsStudent(){
        auth.signInWithEmailAndPassword(etEmail.text.toString(),etPassword.text.toString()).addOnCompleteListener {
            if (it.isSuccessful){
                val student = StudentDao.getStudentFromFireStore(it.result?.user?.uid!!,
                    OnSuccessListener {
                        val student = it.toObject(Student::class.java)
                        Utils.saveUserToSharedPreferences(this,student!!)
                        startActivity(Intent(this,HomeStudentActivity::class.java))
                    })
            }else{
                showMessage("Sign in failed")

            }
        }
    }
    private fun signInAdGraduate(){
       auth.signInWithEmailAndPassword(etEmail.text.toString(),etPassword.text.toString()).addOnCompleteListener {
           if (it.isSuccessful){
               val student = StudentDao.getStudentFromFireStore(it.result?.user?.uid!!,
                   OnSuccessListener {
                       val student = it.toObject(Student::class.java)
                       Utils.saveUserToSharedPreferences(this,student!!)
                       startActivity(Intent(this,HomeGraduateActivity::class.java))
                   })
           }else{
               showMessage("Sign in failed")
           }
       }

    }
}