package com.example.hilibrary.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
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
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)


        //dataBinding.btnBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_bottomNavigationBarActivity))

        navController = findNavController(R.id.nav_host_fragment)
        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment);

        //配置导航图
        NavUtil.builderNavGraph(
            this,
            hostFragment!!.childFragmentManager,
            navController,
            R.id.nav_host_fragment
        );
        NavUtil.builderBottomBar(dataBinding.navView);

        dataBinding.navView.setOnNavigationItemSelectedListener { item ->
            navController.navigate(item.itemId)
            true
        }



//        FragmentNavigator
        //将navController和BottomNavigationView绑定，形成联动效果
        //navView.setupWithNavController(navController)
        //navController!!.navigate(R.id.navigation_notifications)
        //navController!!.navigate(R.id.navigation_notifications, Bundle.EMPTY)
        //navController!!.navigate(Uri.parse("www.imooc.com"))
        //
        //navController!!.navigateUp()
        //navController!!.popBackStack(R.id.navigation_dashboard,false)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
//        navController.popBackStack()
        finish()

    }
}