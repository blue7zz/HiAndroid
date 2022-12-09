package com.example.hilibrary.okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.hilibrary.R
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

@Route(path = "/okhttp/okhttpActivity", group = "okhttp")
class OKHTTPActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttpactivity)
        useOKHttpEnqueue()
    }


    private fun useOKHttpEnqueue() {
        var cacheSize: Long = 10 * 1024 * 1024;

        var formBody = FormBody.Builder().add("id", "59.108.54.37").build();
        var formBody1 = MultipartBody.Builder().build(); //文件请求

        //使用Builder创建request
        var request = Request.Builder().url("http://www.baidu.com").method("GET", null).build()
        //创建OkHttpClient
        var sdCache = externalCacheDir;

        var mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .cache(Cache(sdCache?.absoluteFile, cacheSize)).addInterceptor(object :Interceptor{
                override fun intercept(chain: Interceptor.Chain?): Response {
                    return chain!!.proceed(chain.request())
                }
            })
            .build();

        var mCall = mOkHttpClient.newCall(request);

        //调用异步回调用
        mCall.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                println("请求失败111")

            }

            override fun onResponse(call: Call?, response: Response?) {
                //非UI线程
                println("网络请求,当前线程" + Thread.currentThread().name)
            }
        })
    }
}