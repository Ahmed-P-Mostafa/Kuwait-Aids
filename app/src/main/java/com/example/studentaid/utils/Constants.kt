package com.example.studentaid.utils

import android.content.SharedPreferences
import android.util.Log

object Constants {
    // SharedPreferences Constants
    const val PERSON_SHARED_PREFRENCES_FILE_NAME = "SharedPreferencesPerson"
    val USER_FIRST_NAME_KEY = "USER NAME KEY"
    val USER_LAST_NAME_KEY = "USER NAME KEY"
    val USER_GENDER_KEY = "USER GENDER KEY"
    val USER_TITLE_KEY = "USER GENDER KEY"
    val USER_BIRTHDATE_KEY = "USER BIRTHDATE KEY"
    val USER_iD_CARD_NUMBER_KEY = "USER ID CARD NUMBER KEY"
    val USER_PASSPORT_NUMBER_KEY = "USER PASSPORT NUMBER KEY"
    val USER_UNIVERSITY_NUMBER_KEY = "USER UNIVERSITY NUMBER KEY"
    val USER_NATIONALITY_KEY = "USER NATIONALITY KEY"
    val USER_MOTHER_NATIONALITY_KEY = "USER MOTHER NATIONALITY KEY"
    val USER_ID_KEY = "USER ID KEY"
    val USER_PHONE_KEY = "USER PHONE KEY"
    val USER_EMAIL_KEY = "USER EMAIL KEY"
    val USER_CONDITION = "USER CONDITION"
    val IS_USER_SIGNED_IN = "IS USER SIGNED IN"
    val NULL_VALUE= "NULL_VALUE"

    // Request Conditions Constants
    const val CONDITION_NULL = "NULL"
    const val CONDITION_PENDING = "PENDING"
    const val CONDITION_APPROVED = "APPROVED"
    const val CONDITION_REJECTED = "REJECTED"
    const val CONDITION_WITHDRAWN = "WITHDRAWN"
    const val CONDITION_DESERVED = "DESERVED"
    const val CONDITION_STOP = "STOP"
    const val CONDITION_INCREASE = "INCREASE"
    const val CONDITION_ACCESSED = "ACCESSED"

    const val NOTIFICATION_CHANNEL_ID = "KUWAIT AIDS APPLICATION NOTIFICATION CHANNEL ID"
    const val SENDER_ID = "1066263653496"
    const val BASE_URL = "https://fcm.googleapis.com"
    const val CONTENT_TYPE="application/json"

    const val University_Topic = "University"
    const val Ministry_Topic = "Ministry"

    const val Server_key = "AAAA-EJELHg:APA91bHZiVWhAxYSfC6XxyU1aQ0RHkiMn0WJ93evjDe-R3eElRr1DfWHbJIQ-C9bsaKqajCTgRrEBvRytd8jyu_B0TEVU_p0oDm3Ts2dqFotBPM5npS-jtKGvgi8U--XA0sL9Av3BSn9"
    object token{

        val sharedPref : SharedPreferences?=null

        var token :String?
            get() {
                Log.d("MyFirebaseMessagingServ", ": ${sharedPref?.getString("token","")}")
                return sharedPref?.getString("token","")
            }
            set(value) {
                Log.d("MyFirebaseMessagingServ", "$value: ")
                sharedPref?.edit()?.putString("token",value)?.apply()
            }
    }
}