package com.example.studentaid.data.onlineDatabase

import com.example.studentaid.utils.Constants.BASE_URL
import com.google.gson.Gson
import com.google.rpc.context.AttributeContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {

    companion object{
        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val api by lazy {
            retrofit.create(ApiService::class.java)
        }

    }
}