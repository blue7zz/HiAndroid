package com.example.hilibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.hilibrary.demo.DelayQueueDemo
import com.example.mylibrary.log.HILogManager
import com.example.mylibrary.log.HiViewPrinter


/**
 * 导航宿主是 Navigation 组件的核心部分之一。导航宿主是一个空容器，用户在您的应用中导航时，目的地会在该容器中交换进出。
 * 导航宿主必须派生于 NavHost。Navigation 组件的默认 NavHost 实现 (NavHostFragment) 负责处理 Fragment 目的地的交换。
 *
 * 注意：Navigation 组件旨在用于具有一个主 activity 和多个 fragment 目的地的应用。
 * 主 activity 与导航图相关联，且包含一个负责根据需要交换目的地的 NavHostFragment。
 * 在具有多个 activity 目的地的应用中，每个 activity 均拥有其自己的导航图。
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        val navController = navHostFragment!!.findNavController()

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

    }


}
