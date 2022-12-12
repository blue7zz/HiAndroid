package com.example.hilibrary.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hilibrary.R
import gson.GsonConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit

class RetrofitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

    }


    fun requestGet() {
        var url = "http://www.baidu.com"
        var retrofit =
            Retrofit.Builder().baseUrl(url).client(getClient()).addConverterFactory(
                GsonConverterFactory.create()
            ).build()
        var baiduService = retrofit.create(BaiDuService::class.java)
        baiduService.getBaiDuIndex();
    }


    companion object {
        /**
         * 设置OKHTTP 相关的东西
         */
        fun getClient(): OkHttpClient {
            var client = OkHttpClient.Builder();
            client.interceptors().add(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain?): Response? {
                    var resp = chain?.proceed(chain.request())
                    //拦截器 处理内容
                    return resp
                }
            })

            return client.build()
        }

    }


}


