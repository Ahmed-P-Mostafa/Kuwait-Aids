package com.example.studentaid.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.studentaid.data.models.Document
import com.example.studentaid.data.models.Student

object Utils {
    private const val TAG = "Utils"
    fun saveUserToSharedPreferences(context: Context, user: Student){
        // add Firebase user to shared preferences to validate on it if the user is signed in or logged out
        val sp = context.getSharedPreferences(Constants.PERSON_SHARED_PREFRENCES_FILE_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        val editor : SharedPreferences.Editor = sp.edit()
        editor.putString(Constants.USER_FIRST_NAME_KEY,user.firstName)
        editor.putString(Constants.USER_ID_KEY,user.id)
        editor.putString(Constants.USER_EMAIL_KEY,user.emailAddress)
        editor.putString(Constants.USER_TITLE_KEY,user.title)
        editor.putBoolean(Constants.IS_USER_SIGNED_IN,true)
        editor.putString(Constants.USER_CONDITION,Constants.CONDITION_NULL)
        editor.apply()

        Log.d(TAG, "saveUserToSharedPreferences:${sp.getString(Constants.USER_CONDITION,"error")} ")
        Log.d(TAG, "saveUserToSharedPreferences:${sp.getString(Constants.USER_ID_KEY,"error")} ")
        Log.d(TAG, "saveUserToSharedPreferences:${sp.getBoolean(Constants.IS_USER_SIGNED_IN,false)} ")
    }
    fun getUserFromSharedPreferences(context: Context): Student {
        val sp = context.getSharedPreferences(Constants.PERSON_SHARED_PREFRENCES_FILE_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        val firstName = sp.getString(Constants.USER_FIRST_NAME_KEY,Constants.NULL_VALUE)
        val title = sp.getString(Constants.USER_TITLE_KEY,Constants.NULL_VALUE)
        val id = sp.getString(Constants.USER_ID_KEY,Constants.NULL_VALUE)
        val email = sp.getString(Constants.USER_EMAIL_KEY,Constants.NULL_VALUE)
        val condition = sp.getString(Constants.USER_CONDITION,Constants.NULL_VALUE)
        Log.d(TAG, "getUserFromSharedPreferences: student condition: $condition")

        return Student(firstName = firstName!!,
            emailAddress = email!!,title = title!!,condition = condition!!,id = id,documentList = listOf<Document>())
    }
    fun changeUserRequestCondition(updatedCondition:String,context: Context){
        val sp = context.getSharedPreferences(Constants.PERSON_SHARED_PREFRENCES_FILE_NAME,
            MODE_PRIVATE
        )
        val editor : SharedPreferences.Editor = sp.edit()
        editor.putString(Constants.USER_CONDITION,updatedCondition).apply()
    }

    fun logOutUserFromSharedPreefrences(context: Context){
        val sh = context.getSharedPreferences(Constants.PERSON_SHARED_PREFRENCES_FILE_NAME,MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sh.edit()
        editor.putBoolean(Constants.IS_USER_SIGNED_IN,false).apply()
    }
}