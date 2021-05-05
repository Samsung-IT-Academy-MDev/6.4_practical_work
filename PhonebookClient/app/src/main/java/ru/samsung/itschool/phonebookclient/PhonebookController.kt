package ru.samsung.itschool.phonebookclient

import retrofit2.Call
import retrofit2.http.*

interface PhonebookController {
    @GET("/read")
    fun read(): Call<List<PhonebookEntry>>
    @PUT("/create")
    fun create(@Body entry:PhonebookEntry): Call<Boolean>
    @POST("/update")
    fun update(@Body entry:PhonebookEntry): Call<Boolean>
//    @DELETE(value = "/delete")
    @HTTP(method = "DELETE", path = "/delete", hasBody = true)
    fun delete(@Body entry:PhonebookEntry): Call<Boolean>
}