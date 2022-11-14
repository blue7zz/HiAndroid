package com.example.hilibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Button
import com.example.hilibrary.demo.DelayQueueDemo
import com.example.mylibrary.log.HILogManager
import com.example.mylibrary.log.HiViewPrinter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

    }


}
