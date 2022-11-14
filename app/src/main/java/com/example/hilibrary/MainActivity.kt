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
    var viewPrinter: HiViewPrinter? = null

    var btn:Button? = null;
    var l = Looper.myLooper();
    var lm = Looper.getMainLooper();

    var handler: Handler = object : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Looper.getMainLooper()
            btn?.text = msg.obj.toString()
            Log.i("handlerMessage", "handlerMessage start");

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPrinter = HiViewPrinter(this)

        var myThread = Thread {
            while (true) {
                Log.i("1", "myThread start");
                var msg = Message.obtain();
                msg.obj = "1";
                handler.sendMessage(msg)

            }
        }
        btn = findViewById(R.id.button);
        btn?.setOnClickListener {
//            myThread.start();


        }




        HILogManager.getInstance().addPrinter(viewPrinter)
        viewPrinter!!.viewProvider.showFloatingView()


    }

    override fun onStart() {
        super.onStart()

        Log.d("view", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("view", "onResume")

    }


    class DData() {
        var hahah: String = "hahah";
        var heihie: String = "heihei";
    }


}
