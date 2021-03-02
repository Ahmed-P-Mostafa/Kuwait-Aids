package com.example.studentaid.ui.student

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.studentaid.R
import com.example.studentaid.base.BaseActivity
import com.example.studentaid.data.models.Document
import com.example.studentaid.data.models.Student
import com.example.studentaid.data.onlineDatabase.StudentDao
import com.example.studentaid.utils.Constants
import com.example.studentaid.utils.Utils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_send_request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*

class SendRequestActivity : BaseActivity(), View.OnClickListener {
    private val TAG = "SendRequestActivity"

    private lateinit var filePath: Uri
    private lateinit var fileName :String
    private val documentList  = arrayListOf<Document>()
    private val pathList = arrayListOf<Document>()



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_request)
        getStudentInfo()
        Log.d(TAG, "onCreate: ")

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId.equals(R.id.rbMarried)) {
                btnMartialContract.visibility = View.VISIBLE
                tvMartialContract.visibility = View.VISIBLE
            } else {
                btnMartialContract.visibility = View.GONE
                tvMartialContract.visibility = View.GONE
            }
        }

    }
    private fun getStudentInfo(){
        StudentDao.getStudentFromFireStore(auth.uid!!, OnSuccessListener{
            val student = it.toObject(Student::class.java)
            if (student?.nationality=="Kuwaiti"){
                btn_face_civil_Id.text = "Your Civil Id (Face copy)"
                btn_back_civil_Id.text = "Your Civil Id (Back copy)"
            }else{
                btn_face_civil_Id.text = "Your mother Civil Id (Face copy)"
                btn_back_civil_Id.text = "Your mother Civil Id (Back copy)"

            }

        })
    }


    private fun pickImage() {

        Log.d(TAG, "pickImage: ")

        val pickImageIntent = Intent(Intent.ACTION_PICK)
        pickImageIntent.type = "image/*"

        startActivityForResult(pickImageIntent, 3001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: request code = $requestCode  and result code = $resultCode")

        if (requestCode == 3001 && resultCode == RESULT_OK && data != null) {
            filePath = data.data!!
            val extra = data.extras
            val tag = extra?.get("tag").toString()
            Log.d(TAG, "onActivityResult: $tag")
            pathList.add(Document(name = fileName,path = filePath))


        }
    }

    private fun uploadImages(pathList:List<Document>) {
       CoroutineScope(Dispatchers.IO).launch {


            val student = Utils.getUserFromSharedPreferences(this@SendRequestActivity)
            val individualStudentRef :StorageReference = studentDocumentsReference.child(student.id!!)
            pathList.forEach { path ->
                Log.d(TAG, "uploadImage: ${path.name}")


                val ref: StorageReference =
                    individualStudentRef.child(path.name + Calendar.getInstance().time.toString())

                Log.d(TAG, "uploadImage: path ${ref.path}")
                Log.d(TAG, "uploadImage: name ${ref.name}")


                ref.putFile(path.path!!).addOnSuccessListener { it ->


                    ref.downloadUrl.addOnSuccessListener { uri ->

                        documentList.add(Document(name = path.name,url = uri.toString()))
                        Log.d(TAG, "uploadImage: downloaded uri = $uri")
                    }


                }.addOnFailureListener {
                    Toast.makeText(this@SendRequestActivity,"Error occurred",Toast.LENGTH_SHORT).show()

                }
            }
        }.invokeOnCompletion {
            updateCondition()
        }


    }


    fun sendDocuments(view: View) {

        if (isMarried()) {
            if (pathList.size >= 7) {
                showLoader("Please be patient until uploading...")

                CoroutineScope(Dispatchers.IO).launch {
                    uploadImages(pathList)

                }

            } else {
                showMessage("please complete documents")
            }
        } else {

            if (pathList.size >= 6) {
                showLoader("Please be patient until uploading...")

                CoroutineScope(Dispatchers.IO).launch {
                   uploadImages(pathList)
                }

            } else {
                showMessage("please complete documents")
            }
        }
    }
    private fun isMarried() = radioGroup.checkedRadioButtonId==R.id.rbMarried

    private fun updateCondition(){
        val userId = auth.uid
        Log.d(TAG, "sendDocuments: $userId")
        CoroutineScope(IO).launch {

            StudentDao.updateStudentRequest(userId!!, documentList, OnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this@SendRequestActivity, "Request sent", Toast.LENGTH_SHORT).show()
                    StudentDao.updateStudentCondition(auth.uid!!,Constants.CONDITION_PENDING,
                        OnCompleteListener {
                            if (it.isSuccessful){
                                Utils.changeUserRequestCondition(Constants.CONDITION_PENDING, this@SendRequestActivity)
                                startActivity(Intent(this@SendRequestActivity, HomeStudentActivity::class.java))
                            }else{
                                showMessage("Error occurred while updating condition")
                            }

                        })

                } else {
                    Log.d(TAG, "sendDocuments: ${it.exception?.localizedMessage}")
                    Toast.makeText(this@SendRequestActivity, "Some error occurred", Toast.LENGTH_SHORT).show()

                }
            })
        }.invokeOnCompletion {
            hideLoader()
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {

        when(v?.id){

             R.id.btn_face_civil_Id-> {
                 fileName = v.tag.toString()

                 pickImage()
                 tv_face_civil_id.setText("1/1")
             }
            R.id.btn_back_civil_Id-> {
                fileName = v.tag.toString()

                pickImage()
                tv_back_civil_id.setText("1/1")
            }
            R.id.btnUniRegistration->{
                fileName = v.tag.toString()
                pickImage()
                tvUniRegistration.setText("1/1")
            }
            R.id.btnInsurance->{
                fileName = v.tag.toString()
                tvInsurance.setText("1/1")
                pickImage()
            }
            R.id.btnAffairsCertificate->{
                fileName = v.tag.toString()
                pickImage()
                tvAffairsCertificate.setText("1/1")
            }
            R.id.btnBankIdCard->{
                fileName = v.tag.toString()
                pickImage()
                tvBankIdCard.setText("1/1")
            }
            R.id.btnMartialContract->{
                fileName = v.tag.toString()
                pickImage()
                tvMartialContract.setText("1/1")
            }
            else -> Toast.makeText(this,"error",Toast.LENGTH_SHORT).show()
        }
    }

}