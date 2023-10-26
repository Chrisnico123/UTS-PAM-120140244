package com.example.utspam

import com.example.utspam.model.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/users")
    fun getListUsers(@Query("page") page: String): Call<ResponseUser>

    //get list user by id using path
    @GET("api/users/{id}")
    fun getUser(@Path("id") id: String): Call<ResponseUser>
}