package com.mathilde.foodvisor.network.api

import com.mathilde.foodvisor.db.model.Food
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


/**
 * @author mathilde
 * @version 11/03/2019
 */
interface Service {
    //https://foodvisor.io/itw/food/list/
    @Headers("authorization: Bearer iwn-31@!3pf(w]pmarewj236^in")
    @GET("food/list/{foo}")
    fun getFoodList(@Path("foo") foo: String): Call<Food>
}