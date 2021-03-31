package com.example.studentaid.data.onlineDatabase

import com.example.studentaid.data.models.Employee
import com.example.studentaid.data.models.Student
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

object EmployeeDAO {

    fun insertEmployeeInDatabase(employee: Employee, onCompleteListener: OnCompleteListener<Void>){

        OnlineDatabase.getEmployeeReference().document(employee.id!!).set(employee).addOnCompleteListener(onCompleteListener)
    }
    fun getEmployeeFromFirestore(studentId:String, onSuccessListener: OnCompleteListener<DocumentSnapshot>){
        val studentRef = OnlineDatabase.getEmployeeReference().document(studentId).get().addOnCompleteListener(onSuccessListener)

    }
    fun getAllStudentsRequests(onSuccessListener: OnSuccessListener<QuerySnapshot>,onFailureListener: OnFailureListener){
        OnlineDatabase.getStudentReference().get().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)
    }
}