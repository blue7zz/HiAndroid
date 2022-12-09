package com.example.hilibrary

import android.app.Activity
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.alibaba.android.arouter.launcher.ARouter
import com.example.hilibrary.annotation.SourceAnnotation
import com.example.hilibrary.databinding.ActivityMainBinding

import com.example.hilibrary.navigation.NavigationActivity
import com.example.hilibrary.processor.AnnotationActivity



class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        activityMainBinding.btnNavigation.setOnClickListener {
            startActivity(Intent(this, NavigationActivity::class.java))
        }
        activityMainBinding.btnAnnotation.setOnClickListener {
            startActivity(Intent(this, AnnotationActivity::class.java))
        }

        activityMainBinding.btnARouter.setOnClickListener {
            ARouter.getInstance().build("/main/main/arouteractivity")
                .withString("saleId", "setSaleId")
                .withString("shopId", "setShopId")
                .navigation()
        }

        activityMainBinding.btnGray.post {
            activityMainBinding.btnGray.width;
        }

        activityMainBinding.btnGray.setOnClickListener {
            runGrayStatus()
        }

        activityMainBinding.btnRecyclerview.setOnClickListener {
            ARouter.getInstance().build("/recycler/recyclerViewActivity").navigation()
        }

        activityMainBinding.btnView.setOnClickListener {
            ARouter.getInstance().build("/view/viewactivity").navigation()
        }


        activityMainBinding.btnOkhttp.setOnClickListener {
            ARouter.getInstance().build("/okhttp/okhttpActivity").navigation()
        }


        //Error:
        // Must be one of: SourceAnnotation.STATUS_OPEN, SourceAnnotation.STATUS_CLOSE
        //SourceAnnotation.setStatus(1);
        SourceAnnotation.setStatus(SourceAnnotation.STATUS_OPEN)
    }


    /**
     * 运行灰色状态
     */
    private fun runGrayStatus() {
        var paint = Paint()
        //创建色彩矩阵
        var cm = ColorMatrix()
        //设置
        cm.setSaturation(0F)
        //设置颜色过滤器
        paint.colorFilter = ColorMatrixColorFilter(cm)
        //硬件加速
        window.decorView.setLayerType(View.LAYER_TYPE_HARDWARE,paint)
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }


}
