package ru.samsung.itschool.retrofitpredictor

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {
    @GET("api/v1/predict.json/complete")
    fun predict(@Query("key") key:String , @Query("q") q:String , @Query("lang") lang:String ): Call<Model>
}