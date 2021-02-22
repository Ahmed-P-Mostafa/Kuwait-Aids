package com.example.studentaid.ui.student

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.studentaid.R
import com.example.studentaid.base.BaseActivity
import com.example.studentaid.data.models.Document
import com.example.studentaid.data.models.Student
import com.example.studentaid.data.onlineDatabase.StudentDao
import com.example.studentaid.databinding.ActivityRegisterStudentBinding
import com.example.studentaid.ui.graduate.HomeGraduateActivity
import com.example.studentaid.utils.Constants
import com.example.studentaid.utils.Utils
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_register_student.*

class RegisterStudentActivity : BaseActivity() {
    lateinit var binding : ActivityRegisterStudentBinding
    private val TAG = "RegisterActivity"
    private lateinit var student: Student

    val person = Student(
        "ahmed",
        "ghorab",
        "female",
        "20-6-1995",
        "123456789",
        "1234567890",
        "1230",
        "ahmed@gmail.com",
        "010123456",
        "egyptian",
        "Kuwaiti",
        "Graduate",
        Constants.CONDITION_NULL
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_student)
        val genderItems = listOf("Female","Male")
        val titleItems = listOf("Student","Graduate")
        val genderAdapter = ArrayAdapter(this,R.layout.dropdown_menu,genderItems)
        val titleAdapter = ArrayAdapter(this,R.layout.dropdown_menu,titleItems)

        etGender.setAdapter(genderAdapter)
        etTitle.setAdapter(titleAdapter)



        tilBirthDay.setEndIconOnClickListener {
            showDatePicker()
        }


        btnProceed.setOnClickListener {
            signUp()
        }
    }

    fun signUp(){
        //TODO modify this dummy method
        showLoader("Signing Up...")
        Log.d(TAG, "signUp: ${isDataValid()}")
        if (true){
            Log.d(TAG, "signUp: ")
            auth.createUserWithEmailAndPassword(person.emailAddress.toString(), "1234567890").addOnCompleteListener {
                if (it.isSuccessful){

                    Log.d(TAG, "signUp: successful")
                    Toast.makeText(this, "Authentication Successful", Toast.LENGTH_SHORT).show()
                    student = fetchStudentData()
                    student.id = it.result?.user?.uid
                    //todo modify student instead of person
                    person.id = it.result?.user?.uid
                    validateStudent(person)
                }else{
                    Log.d(TAG, "signUp: ${it.exception?.localizedMessage}")
                    Toast.makeText(this,"sign up failed",Toast.LENGTH_SHORT).show()
                    hideLoader()
                }
            }
        }else{
            Toast.makeText(this,"not true",Toast.LENGTH_SHORT).show()

        }

    }
    fun isDataValid():Boolean{
        Log.d(TAG, "isDataValid: ${etFirstName.text.toString()}")
        Log.d(TAG, "isDataValid: ${etLastName.text.toString()}")
        Log.d(TAG, "isDataValid: ${etGender.text.toString()}")
        Log.d(TAG, "isDataValid: ${etBirthday.text.toString()}")
        Log.d(TAG, "isDataValid: ${etIdNumber.text.toString()}")
        Log.d(TAG, "isDataValid: ${etPhone.text.toString()}")
        Log.d(TAG, "isDataValid: ${etUniversityNumber.text.toString()}")
        Log.d(TAG, "isDataValid: ${etPassport.text.toString()}")
        Log.d(TAG, "isDataValid: ${etEmail.text.toString()}")
        Log.d(TAG, "isDataValid: ${etPhone.text.toString()}")
        Log.d(TAG, "isDataValid: ${etNationality.text.toString()}")
        Log.d(TAG, "isDataValid: ${etMotherNationality.text.toString()}")
        Log.d(TAG, "isDataValid: ${etTitle.text.toString()}")
        return !(etFirstName.text.toString().isNullOrBlank()|| etLastName.text.toString().isNullOrBlank()||etBirthday.text.toString().isNullOrBlank()||
                etGender.text.toString().isNullOrBlank()||etIdNumber.text.toString().isNullOrBlank()||etUniversityNumber.text.toString().isNullOrBlank()||
                etPassport.text.toString().isNullOrBlank()||etEmail.text.toString().isNullOrBlank()||etPhone.text.toString().isNullOrBlank()||
               etNationality.text.toString().isNullOrBlank()||etMotherNationality.text.toString().isNullOrBlank()||etTitle.text.toString().isNullOrBlank()||!Patterns.EMAIL_ADDRESS.matcher(
            etEmail.text.toString()
        ).matches())

    }
    private fun validateStudent(student: Student){
        Log.d(TAG, "validateStudent: ")
        Log.d(TAG, "validateStudent: id: ${student.id}")
        Log.d(TAG, "validateStudent: condition : ${student.condition}")
        Log.d(TAG, "validateStudent: title :  ${student.title}")
        StudentDao.insertStudentInDatabase(student, onCompleteListener = OnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "validateStudent: successful")
                Toast.makeText(this, "Request sent", Toast.LENGTH_LONG).show()
                hideLoader()
                Utils.saveUserToSharedPreferences(this, student)

                if (student.title.equals("Student")){
                    startActivity(Intent(this, HomeStudentActivity::class.java))
                }else{
                    startActivity(Intent(this, HomeGraduateActivity::class.java))
                }

            } else {
                Log.d(TAG, "validateStudent: ${it.exception?.localizedMessage}")
                Toast.makeText(this,"validate Student failed",Toast.LENGTH_SHORT).show()
                hideLoader()
                showMessage(it.exception?.localizedMessage)

            }

        })

    }
    private fun fetchStudentData() : Student {
        Log.d(TAG, "fetchPersonData: ")
        return Student(
            firstName = etFirstName.text.toString(),
            lastName = etLastName.text.toString(),
            gender = etGender.text.toString(),
            birthDate = etBirthday.text.toString(),
            idCardNumber = etIdNumber.text.toString(),
            universityNumber = etUniversityNumber.text.toString(),
            passportNumber = etPassport.text.toString(),
            emailAddress = etEmail.text.toString(),
            phoneNumber = etPhone.text.toString(),
            nationality = etNationality.text.toString(),
            documentList = listOf<Document>(),
            motherNationality = etMotherNationality.text.toString(),
            title = etTitle.text.toString(),
            condition = Constants.CONDITION_NULL
        )

    }
    private fun showDatePicker(){
        Log.e(TAG, "showDatePicker: ")
        val dialogFragment :DialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, getString(R.string.datePicker))
    }
    fun processDatePickerResult(day: Int, month: Int, year: Int){
        val month_string = (month + 1).toString()
        val day_string = day.toString()
        val year_string = year.toString()
        val dateMessage = month_string +
                "/" + day_string + "/" + year_string
        etBirthday.setText(dateMessage)
    }

}
