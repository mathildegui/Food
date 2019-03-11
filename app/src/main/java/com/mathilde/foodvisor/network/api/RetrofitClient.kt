package com.mathilde.foodvisor.network.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author mathilde
 * @version 11/03/2019
 */
class RetrofitClient {
    companion object {
        fun getAPIService() : Service = Retrofit.Builder()
            .baseUrl("https://foodvisor.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Service::class.java)
    }
}