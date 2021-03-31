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
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.util.*
import kotlin.coroutines.CoroutineContext

class SendRequestActivity : BaseActivity(), View.OnClickListener {
    private val TAG = "SendRequestActivity"

    private lateinit var filePath: Uri
    private lateinit var fileName :String
    private val documentList  = arrayListOf<Document>()
    private val pathList = arrayListOf<Document?>(null,null,null,null,null,null)



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_request)
        getStudentInfo()
        Log.d(TAG, "onCreate: ${pathList.size}")
        Log.d(TAG, "onCreate: ${isListNotNullable()}")
        Log.d(TAG, "onCreate: ")

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId.equals(R.id.rbMarried)) {
                btnMartialContract.visibility = View.VISIBLE
                tvMartialContract.visibility = View.VISIBLE
                pathList.add(6,null)
            } else {
                btnMartialContract.visibility = View.GONE
                tvMartialContract.visibility = View.GONE
                tvMartialContract.setText("0/1")
                if (pathList.size==7){
                    pathList.removeAt(6)

                }
            }
            Log.d(TAG, "onCreate: ${pathList.size}")
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


    private fun pickImage(code:Int) {

        Log.d(TAG, "pickImage: ")

        val pickImageIntent = Intent(Intent.ACTION_PICK)
        pickImageIntent.type = "image/*"

        startActivityForResult(pickImageIntent, code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: request code = $requestCode  and result code = $resultCode")

        if (resultCode == RESULT_OK && data != null) {

            filePath = data.data!!
            val extra = data.extras
            val tag = extra?.get("tag").toString()
            Log.d(TAG, "onActivityResult: $tag")
            when(requestCode){
                1->  {tv_face_civil_id.setText("1/1")
                    pathList[0] = Document(name = fileName,path = filePath)}

                2->  {tv_back_civil_id.setText("1/1")
                pathList[1] =Document(name = fileName,path = filePath)}

                3->  {tvUniRegistration.setText("1/1")
                    pathList[2] =Document(name = fileName,path = filePath)}

                4->  {tvInsurance.setText("1/1")
                    pathList[3] =Document(name = fileName,path = filePath)}

                5->  {tvAffairsCertificate.setText("1/1")
                    pathList[4] =Document(name = fileName,path = filePath)}

                6->  {tvBankIdCard.setText("1/1")
                    pathList[5] =Document(name = fileName,path = filePath)}

                7->  {tvMartialContract.setText("1/1")
                    pathList[6]=Document(name = fileName,path = filePath)}

            }
            //pathList.add(Document(name = fileName,path = filePath))
            Log.d(TAG, "onActivityResult: pathList size = ${pathList.size}")
            Log.d(TAG, "onActivityResult: ${pathList.get(1)}")


        }
    }

    private fun uploadImages(pathList:List<Document?>) {
        Log.d(TAG, "uploadImages: ")
        Log.d(TAG, "uploadImages: ${pathList.size}")
        showLoader("Please be patient until uploading...")


        CoroutineScope(IO).launch {



        val student = Utils.getUserFromSharedPreferences(this@SendRequestActivity)
        val individualStudentRef: StorageReference = studentDocumentsReference.child(student.id!!)
        pathList.forEachIndexed { index, path ->

            Log.d(TAG, "uploadImages: index is $index")
            Log.d(TAG, "uploadImage: foreach ${path?.name}")


            val ref: StorageReference =
                individualStudentRef.child(path?.name + Calendar.getInstance().time.toString())

            Log.d(TAG, "uploadImage: path ${ref.path}")
            Log.d(TAG, "uploadImage: name ${ref.name}")


            ref.putFile(path?.path!!).addOnSuccessListener { it ->

                ref.downloadUrl.addOnSuccessListener { uri ->
                    Log.d(TAG, "download url: ${uri.toString()} ")

                    documentList.add(Document(name = path.name, url = uri.toString()))
                    Log.d(TAG, "uploadImage: downloaded uri = $uri")
                    Log.d(TAG, "uploadImages: document list size ${documentList.size}")
                    when (index) {

                        pathList.size-1 -> {updateCondition()
                            Log.d(TAG, "uploadImages: $index")
                            Log.d(TAG, "uploadImages: document list size ${documentList.size}")
                        }
                    }

                }



            }.addOnFailureListener {
               // Toast.makeText(this@SendRequestActivity, "Error occurred", Toast.LENGTH_SHORT).show()

            }
        }
          //  Toast.makeText(this@SendRequestActivity,"Coroutine finish",Toast.LENGTH_SHORT).show()

    }


    }


    fun sendDocuments(view: View) {
        if (isIBanCorrect()){
            if (isMarried()) {
                if (pathList.size == 7&&isListNotNullable()) {
                    uploadImages(pathList)


                } else {
                    showMessage("please complete documents")
                }
            } else {

                if (pathList.size == 6&&isListNotNullable()) {
                    uploadImages(pathList)


                } else {
                    showMessage("please complete documents")
                }
            }

        }else{
            showMessage("IBAN number incorrect")
        }



    }
    private fun isMarried() = radioGroup.checkedRadioButtonId==R.id.rbMarried
    private fun isIBanCorrect()= et_iban.text.toString().length==22

    private fun updateCondition(){
        val userId = auth.uid
        Log.d(TAG, "sendDocuments: $userId")
        CoroutineScope(IO).launch {
            Log.d(TAG, "updateCondition: document list =  ${documentList.size}")

            StudentDao.updateStudentRequest(userId!!, documentList, OnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "updateCondition: request Updated")

                   /* Toast.makeText(this@SendRequestActivity, "Request sent", Toast.LENGTH_SHORT)
                        .show()*/
                    StudentDao.updateStudentCondition(auth.uid!!, Constants.CONDITION_PENDING,
                        OnCompleteListener {
                            if (it.isSuccessful) {
                                this@SendRequestActivity.hideLoader()
                                Utils.changeUserRequestCondition(
                                    Constants.CONDITION_PENDING,
                                    this@SendRequestActivity
                                )
                                startActivity(
                                    Intent(
                                        this@SendRequestActivity,
                                        HomeStudentActivity::class.java
                                    )
                                )
                                this@SendRequestActivity.finish()



                            } else {
                                showMessage("Error occurred while updating condition")
                            }

                        })

                } else {
                    Log.d(TAG, "sendDocuments: ${it.exception?.localizedMessage}")
                    Toast.makeText(
                        this@SendRequestActivity,
                        "Some error occurred",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            })
        }


    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {

        when(v?.id){

             R.id.btn_face_civil_Id-> {
                 fileName = v.tag.toString()

                 pickImage(1)

             }
            R.id.btn_back_civil_Id-> {
                fileName = v.tag.toString()

                pickImage(2)

            }
            R.id.btnUniRegistration->{
                fileName = v.tag.toString()
                pickImage(3)

            }
            R.id.btnInsurance->{
                fileName = v.tag.toString()

                pickImage(4)
            }
            R.id.btnAffairsCertificate->{
                fileName = v.tag.toString()
                pickImage(5)

            }
            R.id.btnBankIdCard->{
                fileName = v.tag.toString()
                pickImage(6)

            }
            R.id.btnMartialContract->{
                fileName = v.tag.toString()
                pickImage(7)

            }
            else -> Toast.makeText(this,"error",Toast.LENGTH_SHORT).show()
        }
    }
    private fun isListNotNullable() = !pathList.contains(null)


}