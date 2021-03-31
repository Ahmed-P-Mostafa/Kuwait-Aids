package com.example.studentaid.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studentaid.R
import com.example.studentaid.ui.LandingActivity
import com.example.studentaid.utils.Constants
import com.example.studentaid.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

open class BaseActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    val fireStore = Firebase.firestore
   private val storage  = Firebase.storage
   private val storageReference :StorageReference = storage.reference
    val studentDocumentsReference :StorageReference = storageReference.child("StudentsFiles")
  //  val graduateReference :StorageReference = storage.reference
    lateinit var sharedPreerencesPerson :SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        sharedPreerencesPerson = getSharedPreferences(Constants.PERSON_SHARED_PREFRENCES_FILE_NAME, MODE_PRIVATE)
    }
    private var dialog : AlertDialog? = null
    private var loader : ProgressDialog? = null
    private var message: AlertDialog? = null

    fun showDialog(title :String? = null, message :String? = null, posButton :String? = null,
                   negButton :String? = null, posAction: DialogInterface.OnClickListener?= null
                   , negAction: DialogInterface.OnClickListener?= null, cancelable:Boolean=false){

        dialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(posButton,posAction)
            .setNegativeButton(negButton,negAction)
            .setCancelable(cancelable)
            .show()
    }
    fun showMessage(textt: String?){
        message = AlertDialog.Builder(this)
            .setMessage(textt)
            .setPositiveButton("ok") { dialogInterface, i ->
                dialogInterface.dismiss()
            }.setCancelable(true)
            .show()
    }
    fun hideMessage(){
        dialog?.dismiss()
    }
    fun showLoader(message:String){
        loader = ProgressDialog(this)
        loader?.setMessage(message)
        loader?.setCancelable(false)
        loader?.progress = 1
        loader?.show()

    }
    fun hideLoader(){
        loader?.dismiss()
    }
    fun setLoaderProgress(progress:Long){
        loader?.progress = progress.toInt()
    }
    fun logOut(context: Context){

        Utils.logOutUserFromSharedPreefrences(context)
        auth.signOut()
        startActivity(Intent(context, LandingActivity::class.java))

    }




}