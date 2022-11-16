package com.example.hilibrary.arouter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.hilibrary.databinding.ActivityArouterBinding


@Route(path = "/main/main/arouteractivity", group = "main")
class ARouterActivity : AppCompatActivity() {


    @JvmField
    @Autowired
    var saleId: String? = null


    @JvmField
    @Autowired
    var shopId: String? = null

    lateinit var dataBinding: ActivityArouterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)

        dataBinding = ActivityArouterBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        dataBinding.tvText.text = "saleId:$saleId- shopId:$shopId"

    }
}