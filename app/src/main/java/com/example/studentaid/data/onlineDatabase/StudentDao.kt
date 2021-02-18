package com.example.studentaid.data.onlineDatabase

import com.example.studentaid.data.Document
import com.example.studentaid.data.Student
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot

object StudentDao {
    fun insertStudentInDatabase(student: Student, onCompleteListener: OnCompleteListener<Void>){

        val studentReference = OnlineDatabase.getStudentReference().document(student.id!!).set(student).addOnCompleteListener(onCompleteListener)
    }

    fun updateStudentRequest(collectionId:String,documentList: List<Document>,onCompleteListener: OnCompleteListener<Void>){
        val studentRef = OnlineDatabase.getStudentReference().document(collectionId)

        studentRef.update("documentList",documentList).addOnCompleteListener(onCompleteListener)
    }
    fun getStudentFromFireStore(studentId:String, onCompleteListener:OnSuccessListener<DocumentSnapshot>){
        val studentRef = OnlineDatabase.getStudentReference().document(studentId).get().addOnSuccessListener(onCompleteListener)

    }
    fun updateStudentCondition(collectionId:String,condition:String,onCompleteListener: OnCompleteListener<Void>){
        val studentRef = OnlineDatabase.getStudentReference().document(collectionId)
        studentRef.update("condition",condition).addOnCompleteListener(onCompleteListener)

    }
    fun updateGraduateDegree(collectionId: String,universityDegree:String,onCompleteListener: OnCompleteListener<Void>){
       val graduateRef= OnlineDatabase.getStudentReference().document(collectionId)
        graduateRef.update("universityDegree",universityDegree).addOnCompleteListener(onCompleteListener)
    }
}