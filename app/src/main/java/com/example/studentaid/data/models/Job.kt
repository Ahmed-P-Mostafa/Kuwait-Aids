package com.example.studentaid.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class Job(
    val imageSrcId :Int,
    val occupation: String,
    val location: String,
    val workingHours: String,
    val title: String,
    val salary: String,
    val placesAvailable: String,
):Serializable