package com.example.hilibrary

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.hilibrary.databinding.ActivityViewBinding


@Route(path = "/view/viewactivity", group = "view")
class ViewActivity : AppCompatActivity() {


    lateinit var dataBind: ActivityViewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = ActivityViewBinding.inflate(layoutInflater)

        var lv_one = dataBind.lvOne
        var lv_two = dataBind.lvTwo
        val strs1 =
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")
        val adapter1: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, strs1)
        lv_one.setAdapter(adapter1)

        val strs2 =
            arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O")
        val adapter2: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, strs2)
        lv_two.setAdapter(adapter2)
//        dataBind.btnMoveView.setOnClickListener {
////            dataBind.vMy.requestLayout()
////            dataBind.vMy.animation = AnimationUtils.loadAnimation(this,R.anim.translate);
////            dataBind.vMy.animation.start()
////            ObjectAnimator.ofFloat(dataBind.vMy,"translationX",300)
//
//
//            (dataBind.vMy as View).scrollTo(20, 20)
////            ((View)dataBind.vMy.parent).scrollBy(500,500); //移动100 100
//
//
//            println("点击了vMy")
////            dataBind.vMy.scrollTo(300,300); //移动100 100
//        }
//
//        dataBind.vMy.setOnClickListener{
//        }

        setContentView(dataBind.root)
    }
}