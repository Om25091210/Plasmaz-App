package com.aryomtech.plasmaz

import com.aryomtech.plasmaz.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofirInstance {

    companion object{
        private val retrofit by lazy{
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        val api by lazy {
            retrofit.create(NotificationAPI::class.java)
        }
    }
}