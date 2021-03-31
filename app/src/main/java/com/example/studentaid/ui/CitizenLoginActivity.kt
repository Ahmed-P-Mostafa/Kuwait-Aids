package com.example.studentaid.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.studentaid.R
import com.example.studentaid.base.BaseActivity
import com.example.studentaid.data.models.Student
import com.example.studentaid.data.onlineDatabase.StudentDao
import com.example.studentaid.ui.graduate.HomeGraduateActivity
import com.example.studentaid.ui.student.HomeStudentActivity
import com.example.studentaid.utils.Constants
import com.example.studentaid.utils.Utils
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.android.synthetic.main.activity_citizen_login.*

class CitizenLoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citizen_login)
    }

    fun Login(view: View) {
        if (isDataFilled()){
            showLoader("Loading...")
            when(radioGroup.checkedRadioButtonId){
               R.id.rbStudent->{
                   signInAsStudent()
               }
               R.id.rbGraduate->{
                   signInAdGraduate()
               }
           }
        }else{
            showMessage("Invalid username or password")
        }
    }
    private fun isDataFilled():Boolean{
        return !(etEmail.text.toString().isNullOrBlank()||etPassword.text.toString().isNullOrBlank())
    }
    private fun signInAsStudent(){
        auth.signInWithEmailAndPassword(etEmail.text.toString(),etPassword.text.toString()).addOnSuccessListener {result->


                val student = StudentDao.getStudentFromFireStore(result.user?.uid!!,
                    OnSuccessListener {document->
                        val student = document.toObject(Student::class.java)
                        if (student?.title =="Student"){
                            if (student.nationality=="Kuwaiti"||student.motherNationality=="Kuwaiti"){
                                if (student.condition!=Constants.CONDITION_NULL){
                                    Utils.saveUserToSharedPreferences(this,student)
                                    startActivity(Intent(this,HomeStudentActivity::class.java))
                                    finish()
                                }else{
                                    hideLoader()
                                    showMessage("Your account not activated yet")
                                    auth.signOut()
                                }
                            }else{
                                hideLoader()
                                auth.signOut()
                                Toast.makeText(this,"Sorry you're not eligible to proceed",Toast.LENGTH_SHORT).show()
                            }


                        }else{
                            hideLoader()
                            auth.signOut()
                            Toast.makeText(this,"Not Student Account",Toast.LENGTH_SHORT).show()
                        }

                    })

        }.addOnFailureListener {
            hideLoader()
            showMessage("Invalid username or password")
        }
    }
    // TODO handle loader
    private fun signInAdGraduate(){
        auth.signInWithEmailAndPassword(etEmail.text.toString(),etPassword.text.toString()).addOnCompleteListener {result->
            if (result.isSuccessful){
                StudentDao.getStudentFromFireStore(result.result?.user?.uid!!,
                    OnSuccessListener {document->
                       val student = document.toObject(Student::class.java)
                       if (student?.title== "Graduate"){
                           if(student.nationality=="Kuwaiti"){
                               Utils.saveUserToSharedPreferences(this,student)
                               startActivity(Intent(this,HomeGraduateActivity::class.java))
                               finish()
                           }else{
                               hideLoader()
                               FirebaseAuth.getInstance().signOut()
                               Toast.makeText(this,"Sorry you're not eligible to proceed",Toast.LENGTH_SHORT).show()
                           }
                       }else{
                           hideLoader()
                           FirebaseAuth.getInstance().signOut()
                           Toast.makeText(this,"Not Graduate Account",Toast.LENGTH_SHORT).show()

                       }
                   })
           }else{
               hideLoader()

                FirebaseAuth.getInstance().signOut()
                showMessage("Invalid username or password")
           }
       }

    }
}