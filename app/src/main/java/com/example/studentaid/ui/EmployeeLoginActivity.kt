package com.example.studentaid.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import com.example.studentaid.R
import com.example.studentaid.base.BaseActivity
import com.example.studentaid.data.models.Employee
import com.example.studentaid.data.models.Student
import com.example.studentaid.data.onlineDatabase.EmployeeDAO
import com.example.studentaid.ui.ministryEmployee.MinistryHomeActivity
import com.example.studentaid.ui.universityEmployee.UniversityMainActivity
import com.example.studentaid.utils.Utils
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_citizen_login.*
import kotlinx.android.synthetic.main.activity_citizen_login.etEmail
import kotlinx.android.synthetic.main.activity_citizen_login.etPassword
import kotlinx.android.synthetic.main.activity_employee_login.*
import kotlinx.android.synthetic.main.activity_register_student.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeLoginActivity : BaseActivity() {
    private  val TAG = "EmployeeLoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_login)

    }



    private fun isDataFilled():Boolean{
        return et_email.text.toString().isNotBlank() && et_password.text.toString().isNotBlank() &&Patterns.EMAIL_ADDRESS.matcher(et_email.text.toString()).matches()
    }
    private fun signInAsEmployee(){
        Log.d(TAG, "signInAsEmployee: ")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithEmailAndPassword(et_email.text.toString(),et_password.text.toString())
                    .addOnCompleteListener {task->
                        if (task.isSuccessful){
                            Log.d(TAG, "signInAsEmployee: authentication success")

                            EmployeeDAO.getEmployeeFromFirestore(
                                task.result?.user?.uid!!,
                                OnSuccessListener {
                                    Log.d(TAG, "signInAsEmployee: firestore success")
                                    val employee = it.toObject(Employee::class.java)
                                    Log.d(TAG, "signInAsEmployee: ${employee?.title}")
                                    Utils.saveUserToSharedPreferences(this@EmployeeLoginActivity,
                                        Student(title = employee?.title)
                                    )


                                    if (employee != null && employee.title == "University") {
                                        startActivity(
                                            Intent(
                                                this@EmployeeLoginActivity,
                                                UniversityMainActivity::class.java
                                            )
                                        )
                                    } else if (employee != null && employee.title == "Ministry") {
                                        startActivity(
                                            Intent(
                                                this@EmployeeLoginActivity,
                                                MinistryHomeActivity::class.java
                                            )
                                        )
                                    }
                                })
                        }
                        else{
                            showMessage("Authentication failed")

                        }
                    }

            }catch (e:Exception){
                Log.d(TAG, "signInAsEmployee: ${e.localizedMessage}")
            }

        }

    }

    fun empLogin(view: View) {

        Log.d(TAG, "login: fired")
        showLoader("Loading...")
        if (isDataFilled()){
            Log.d(TAG, "login: data valid ")
            signInAsEmployee()
        }else{
            hideLoader()
            Log.d(TAG, "login: data not valid")
            showMessage("missing data")

        }
    }
}