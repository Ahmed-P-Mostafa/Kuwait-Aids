package com.example.studentaid.data.onlineDatabase

import com.example.studentaid.data.models.MyResponse
import com.example.studentaid.data.models.PushNotification
import com.example.studentaid.utils.Constants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Authorization: key=${Constants.Server_key}","Content-Type:${Constants.CONTENT_TYPE}")
    @POST("fcm/send")
    suspend fun sendNotification(@Body notification: PushNotification):Response<ResponseBody>
}