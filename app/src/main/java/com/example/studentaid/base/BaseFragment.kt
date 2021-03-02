package com.example.studentaid.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

open class BaseFragment :Fragment() {

    private var dialog : AlertDialog? = null
    private var loader : ProgressDialog? = null
    private var message: AlertDialog? = null

    open fun showLoader(message:String,context: Context){
        loader = ProgressDialog(context)
        loader?.setMessage(message)
        loader?.setCancelable(false)
        loader?.progress = 1
        loader?.show()

    }
    open fun hideLoader(){
        loader?.dismiss()
    }
    fun showDialog(title :String? = null, message :String? = null, posButton :String? = null,
                   negButton :String? = null, posAction: DialogInterface.OnClickListener?= null
                   , negAction: DialogInterface.OnClickListener?= null, cancelable:Boolean=false){

        dialog = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(posButton,posAction)
            .setNegativeButton(negButton,negAction)
            .setCancelable(cancelable)
            .show()
    }
    fun showMessage(textt: String?,context: Context){
        message = AlertDialog.Builder(context)
            .setMessage(textt)
            .setPositiveButton("ok") { dialogInterface, i ->
                dialogInterface.dismiss()
            }.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}