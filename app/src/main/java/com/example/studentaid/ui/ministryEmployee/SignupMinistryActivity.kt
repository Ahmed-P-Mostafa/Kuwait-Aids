package com.example.studentaid.ui.ministryEmployee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import com.example.studentaid.R
import com.example.studentaid.base.BaseActivity
import com.example.studentaid.data.models.Employee
import com.example.studentaid.data.models.Student
import com.example.studentaid.data.onlineDatabase.EmployeeDAO
import com.example.studentaid.ui.universityEmployee.UniversityMainActivity
import com.example.studentaid.utils.Utils
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_university_signup.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupMinistryActivity : BaseActivity() {
    private val TAG = "SignupMinistryActivity"
    var employee =Employee()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_ministry)
    }
    fun SignUp(view: View) {
        Log.d(TAG, "SignUp: ")
        if (isDataValid()){
            showLoader("Registering...")
            CoroutineScope(Dispatchers.IO).launch {
                authenticate()
            }

        }else{
            showMessage("incorrect data")
        }
    }

    private suspend fun authenticate() {
        CoroutineScope(Dispatchers.Default).launch {

            Log.d(TAG, "authenticate: ")
            auth.createUserWithEmailAndPassword(et_email.text.toString(),et_password.text.toString()).addOnCompleteListener {
                Log.d(TAG, "authenticate: ${it.result.toString()}")
                if (it.isSuccessful){

                    employee = Employee(email =et_email.text.toString(),id = it.result?.user?.uid,title = "Ministry" )

                    EmployeeDAO.insertEmployeeInDatabase(employee, OnCompleteListener {
                        if (it.isSuccessful){
                            Utils.saveUserToSharedPreferences(this@SignupMinistryActivity, Student(title = employee.title))
                            startActivity(
                                Intent(this@SignupMinistryActivity,
                                    MinistryHomeActivity::class.java)
                            )
                        }

                    })
                }else{
                    showMessage(it.exception?.localizedMessage)
                }
                hideLoader()
            }
        }
    }


    private fun isDataValid(): Boolean {
        Log.d(TAG, "isDataValid: ")
        return !et_email.text.isNullOrBlank()&& Patterns.EMAIL_ADDRESS.matcher(et_email.text.toString()).matches()&&!et_password.text.isNullOrBlank()
    }
}