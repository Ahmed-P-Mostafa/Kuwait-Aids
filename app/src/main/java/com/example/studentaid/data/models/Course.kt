package com.example.studentaid.data.models

import com.example.studentaid.R

data class Course(
    val imageSrc:Int?= R.drawable.course,
    val name: String? = "Course Name",
    val desc: String?= "For Law graduates",
    val usefulness: String ?= "Usefulness of this course",
    val time: String? = "6 hours",
    val price: String? = "720 EG",
) {
}