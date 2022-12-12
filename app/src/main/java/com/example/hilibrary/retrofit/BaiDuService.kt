package com.example.hilibrary.retrofit

import retrofit2.Call
import retrofit2.http.GET


public interface BaiDuService {

    @GET("/")
    fun getBaiDuIndex():Call<Any>
}