package com.example.studentaid.data.models
data class NotificationData(
    val title:String,
    val message:String
)
data class PushNotification(
    val to :String,
    val data: NotificationData)