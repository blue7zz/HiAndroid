package com.example.module_trade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter


@Route(path = "/trade/tradeactivity")
class TradeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trade)


        findViewById<Button>(R.id.button).setOnClickListener {
            ARouter.getInstance().build("/user/useractivity").navigation()
        }

    }
}