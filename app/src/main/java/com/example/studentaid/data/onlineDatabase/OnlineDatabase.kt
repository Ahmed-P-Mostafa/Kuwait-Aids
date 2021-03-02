package com.example.studentaid.data.onlineDatabase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object OnlineDatabase {
    private val PERSON_REF ="Student"
    private val REQUEST_REF = "REQUEST"
    private val EMPLOYEE_REF = "Employee"

    val fireStore = Firebase.firestore


    fun getStudentReference():CollectionReference{
        return fireStore.collection(PERSON_REF)
    }

    fun getRequestReference():CollectionReference{
        return fireStore.collection(REQUEST_REF)
    }
    fun getEmployeeReference():CollectionReference{
        return fireStore.collection(EMPLOYEE_REF)
    }

}