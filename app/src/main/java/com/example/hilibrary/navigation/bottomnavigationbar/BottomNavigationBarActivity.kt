package com.example.hilibrary.navigation.bottomnavigationbar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hilibrary.databinding.ActivityBnavigationBinding

class BottomNavigationBarActivity:AppCompatActivity() {


    lateinit var dataBinding:ActivityBnavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityBnavigationBinding.inflate(layoutInflater);
        setContentView(dataBinding.root)

    }
}