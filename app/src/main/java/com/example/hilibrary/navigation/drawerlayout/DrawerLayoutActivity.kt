package com.example.hilibrary.navigation.drawerlayout

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.hilibrary.R
import com.example.hilibrary.databinding.ActivityDrawerLayoutBinding

class DrawerLayoutActivity:AppCompatActivity() {
    lateinit var dataBinding:ActivityDrawerLayoutBinding

    lateinit var appBarConfiguration:AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityDrawerLayoutBinding.inflate(layoutInflater);
        setContentView(dataBinding.root)

        val drawerLayout = dataBinding.drawerLayout
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        //绑定当前的ActionBar，除此之外NavigationUI还能绑定Toolbar和CollapsingToolbarLayout
        //绑定后，系统会默认处理ActionBar左上角区域，为你添加返回按钮，将所切换到的Fragment在导航图里的name属性中的内容显示到Title
        //.setDrawerLayout(drawerLayout)后才会出现菜单按钮
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).setDrawerLayout(drawerLayout).build()
        setupActionBarWithNavController(navController,appBarConfiguration)

        //设置左侧菜单
        var navigationView = dataBinding.navigationView
        NavigationUI.setupWithNavController(navigationView,navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Toast.makeText(
                this@DrawerLayoutActivity,
                "onDestinationChanged() called",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(this, R.id.nav_host_fragment)
        Toast.makeText(
            this@DrawerLayoutActivity,
            "onSupportNavigateUp() called",
            Toast.LENGTH_SHORT
        ).show()
        return navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }
}