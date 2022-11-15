package com.example.hilibrary.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.hilibrary.R
import com.example.hilibrary.databinding.ActivityNavigationBinding


/**
 * Navigation 四部分
 *
 * 1. NavHost 容器，存放哪些页面可以进来，哪些页面可以出去
 * 2. Fragment  最初为了适应大屏幕，将大屏幕分割成小部分，每个小部分就是Fragment
 * 3. NavController 控制导航逻辑，按下ð要切换哪个页面，具体切换到哪个页面由导航路线决定
 * 4. NavGraph  页面之间到逻辑关系，页面之间切换到关系由箭头组成，每个箭头是一个Action,由NavController来驱动这些Action.
 */
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