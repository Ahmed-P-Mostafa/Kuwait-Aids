package com.example.studentaid.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

open class BaseViewModel<N> :ViewModel() {
    val navigator :N?=null

    val auth = FirebaseAuth.getInstance()

    var dialog = MutableLiveData<CustomMessage>()
    var loader = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
}