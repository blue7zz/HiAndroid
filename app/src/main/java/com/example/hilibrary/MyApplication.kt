package com.example.hilibrary

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
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

        if (BuildConfig.DEBUG) {         // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();          // 打印日志
            ARouter.openDebug();        // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }
}