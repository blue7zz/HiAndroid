package com.example.hilibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mylibrary.log.HILogManager
import com.example.mylibrary.log.HiLog
import com.example.mylibrary.log.HiViewPrinter

class MainActivity : AppCompatActivity() {
    var viewPrinter: HiViewPrinter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPrinter = HiViewPrinter(this)

        findViewById<Button>(R.id.button).setOnClickListener {
            HiLog.e("打印EEEE", "哈哈哈", "哈哈哈哈哈啊1");
            HiLog.e("打印EEEE", DData());
        }
        HILogManager.getInstance().addPrinter(viewPrinter)
        viewPrinter!!.viewProvider.showFloatingView()


    }


    class DData() {
        var hahah: String = "hahah";
        var heihie: String = "heihei";
    }


}
