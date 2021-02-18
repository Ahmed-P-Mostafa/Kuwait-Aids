package com.example.studentaid.base

import android.content.DialogInterface

data class CustomMessage(val title:String?=null, var message:String, var posButton: String, var posAction: DialogInterface.OnClickListener, val negButton:String?=null, val negAction:DialogInterface.OnClickListener?=null, val cancelable:Boolean=false)
