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
import com.example.studentaid.data.Document
import com.example.studentaid.data.onlineDatabase.OnlineDatabase
import com.example.studentaid.data.onlineDatabase.StudentDao
import com.example.studentaid.ui.LandingActivity
import com.example.studentaid.ui.SplashActivity
import com.example.studentaid.utils.Constants
import com.example.studentaid.utils.Utils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_send_request.*
import java.util.*

class SendRequestActivity : BaseActivity(), View.OnClickListener {
    private val TAG = "SendRequestActivity"
    private lateinit var filePath: Uri
    private lateinit var fileName :String
    private val documentList  = arrayListOf<Document>()



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_request)
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
            uploadImage(filePath, fileName)

        }
    }

    fun uploadImage(imageURI: Uri, imageName: String) {
        Log.d(TAG, "uploadImage: $imageName")
        val student = Utils.getUserFromSharedPreferences(this)
        val individualStudentRef :StorageReference = studentDocumentsReference.child(student.id!!)
        val ref: StorageReference =
            individualStudentRef.child(imageName + Calendar.getInstance().time.toString())

        Log.d(TAG, "uploadImage: path ${ref.path}")
        Log.d(TAG, "uploadImage: name ${ref.name}")
        showLoader("Uploading...")

        ref.putFile(imageURI).addOnSuccessListener { it ->

            ref.downloadUrl.addOnSuccessListener { uri ->
                val uri = uri
                documentList.add(Document(imageName,uri.toString()))
                Log.d(TAG, "uploadImage: downloaded uri = $uri")
            }

            hideLoader()
            Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show();
        }.addOnFailureListener {
            hideLoader()
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }.addOnProgressListener {
            val progree = (100*it.bytesTransferred)/it.totalByteCount
            setLoaderProgress(progree)
        }

    }


    fun sendDocuments(view: View) {
  /*      val x = false
        if (x){
            auth.signOut()
            Utils.logOutUserFromSharedPreefrences(this)
            startActivity(Intent(this,LandingActivity::class.java))
        }
*/
        if (isMarried()) {

            if (documentList.size == 6) {

            } else {
                showMessage("please complete documents")
            }
        } else {

            if (documentList.size == 5) {
                val userId = auth.uid
                Log.d(TAG, "sendDocuments: $userId")
                StudentDao.updateStudentRequest(userId!!, documentList, OnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Request sent", Toast.LENGTH_SHORT).show()
                        StudentDao.updateStudentCondition(auth.uid!!,Constants.CONDITION_PENDING,
                            OnCompleteListener {
                                if (it.isSuccessful){
                                    Utils.changeUserRequestCondition(Constants.CONDITION_PENDING, this)
                                    startActivity(Intent(this, HomeStudentActivity::class.java))
                                }else{
                                    showMessage("Error occurred while updating condition")
                                }

                            })


                    } else {
                        Log.d(TAG, "sendDocuments: ${it.exception?.localizedMessage}")
                        Toast.makeText(this, "Some error occurred", Toast.LENGTH_SHORT).show()

                    }
                })

            } else {
                showMessage("please complete documents")
            }
        }
    }
    private fun isMarried() = radioGroup.checkedRadioButtonId==R.id.rbMarried

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {

        when(v?.id){

             R.id.btnCivilId-> {
                 fileName = v.tag.toString()

                 pickImage()
                 tvCivilId.setText("1/1")
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