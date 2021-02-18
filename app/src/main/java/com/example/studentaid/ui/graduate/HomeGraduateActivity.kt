package com.example.studentaid.ui.graduate

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.studentaid.R
import com.example.studentaid.base.BaseActivity
import com.example.studentaid.data.Student
import com.example.studentaid.data.onlineDatabase.StudentDao
import com.example.studentaid.utils.Constants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener

class HomeGraduateActivity : BaseActivity() {
    private var student: Student?=null
    private val TAG = "HomeGraduateActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_graduate)
    }

    private fun checkForRequest(){
        Log.d(TAG, "checkForRequest: get userFrom shared preferences")
        //    val person = Utils.getUserFromSharedPreferences(this)
        StudentDao.getStudentFromFireStore(auth.uid!!, OnSuccessListener {
            Log.d(TAG, "onCreate: succed")
            student = it.toObject(Student::class.java)!!
            Log.d(TAG, "onCreate: ${student?.condition}")
            if (student?.condition.equals(Constants.CONDITION_NULL)){
                Log.d(TAG, "checkForRequest: ${student?.condition}")

            }

        })


    }

    private fun showSpinnerDialog(){
        val builder = AlertDialog.Builder(this)
        val dialog :AlertDialog = builder.create()
        val view : View = layoutInflater.inflate(R.layout.spinner_dialog_layout,null)
        val spinner :Spinner = view.findViewById(R.id.spDegrees)
        val sendButton:Button = view.findViewById(R.id.btnSendDegree)
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        sendButton.setOnClickListener {
            if (spinner.selectedItem.toString()!= "Choose your degree…"){
                StudentDao.updateGraduateDegree(auth.uid!!,spinner.selectedItem.toString(),
                    OnCompleteListener {
                        dialog.dismiss()
                    })


            }else{
                Toast.makeText(this,"Please choose your college degree",Toast.LENGTH_LONG).show()
            }
        }

        builder.setTitle("Determine your university degree")
        builder.setView(view)

        dialog.show()

    }

}