package com.example.studentaid.data

import com.example.studentaid.ui.student.HomeStudentActivity
import com.example.studentaid.utils.ICitizen

data class Student(val firstName:String?=null,
                   val lastName:String?=null,
                   val gender:String?=null,
                   val birthDate:String?=null
                   , val idCardNumber:String?=null
                   , val passportNumber:String?=null
                   , val universityNumber:String?=null
                   , val emailAddress:String?=null
                   , val phoneNumber:String?=null
                   , val nationality:String?=null
                   , val motherNationality:String?=null
                   , val title:String?=null
                   , val condition:String?=null
                   , var documentList :List<Document>?= mutableListOf()
                   , var universityDegree :String?=null
                   , var id :String?=null) : ICitizen() {

    override fun getCitizenType():Class<*> {
        return HomeStudentActivity::class.java

    }
}