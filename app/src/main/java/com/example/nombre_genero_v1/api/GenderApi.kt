package com.example.nombre_genero_v1.api


import com.example.nombre_genero_v1.models.GenderResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//import retrofit2.http.GET

interface GenderApi {
    @GET("/")
    fun getGender(@Query("name") name:String): Call<GenderResponse>
}