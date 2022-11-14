package com.example.hilibrary.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.hilibrary.R
import com.example.hilibrary.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {

    lateinit var dataBinding: ActivityNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        // does not have a NavController set
        dataBinding.btnBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_bottomNavigationBarActivity))
    }
}