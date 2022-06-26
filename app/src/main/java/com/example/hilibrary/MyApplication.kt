package com.example.hilibrary

import android.app.Application
import com.example.mylibrary.log.HILogManager
import com.example.mylibrary.log.HiConsolePrinter
import com.example.mylibrary.log.HiLogConfig
import com.google.gson.Gson

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        var config = object : HiLogConfig() {
            override fun getGlobalTag(): String {
                return "我的tag"
            }

            override fun enable(): Boolean {
                return true
            }

            override fun injectJsonParser(): JsonParser {
                return JsonParser { src -> Gson().toJson(src) }
            }
        }

        HILogManager.init(config);
        var hiManger = HILogManager.getInstance()
        hiManger.addPrinter(HiConsolePrinter())

    }
}