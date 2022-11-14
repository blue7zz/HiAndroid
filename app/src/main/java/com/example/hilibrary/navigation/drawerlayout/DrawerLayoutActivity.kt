package com.example.hilibrary.navigation.drawerlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hilibrary.databinding.ActivityBnavigationBinding
import com.example.hilibrary.databinding.ActivityDrawerLayoutBinding

class DrawerLayoutActivity:AppCompatActivity() {
    lateinit var dataBinding:ActivityDrawerLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityDrawerLayoutBinding.inflate(layoutInflater);
        setContentView(dataBinding.root)

    }
}