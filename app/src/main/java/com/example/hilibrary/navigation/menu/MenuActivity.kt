package com.example.hilibrary.navigation.menu

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.navigateUp
import com.example.hilibrary.R
import com.example.hilibrary.databinding.ActivityMenuBinding

class MenuActivity:AppCompatActivity() {
    lateinit var dataBinding:ActivityMenuBinding
    lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMenuBinding.inflate(layoutInflater);
        setContentView(dataBinding.root)
        //找到controller


        var navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //绑定当前的ActionBar，除此之外NavigationUI还能绑定Toolbar和CollapsingToolbarLayout
        //绑定后，系统会默认处理ActionBar左上角区域，为你添加返回按钮，将所切换到的Fragment在导航图里的name属性中的内容显示到Title
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()

        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);

        navController.addOnDestinationChangedListener { _, _, _ ->
            Toast.makeText(
                this@MenuActivity,
                "onDestinationChanged() called",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    /**
     * 加载菜单
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings,menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * ActionBar中的按钮被点击时，根据菜单中的Id，自动跳转到相应的页面
     * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var navController = findNavController(this,R.id.nav_host_fragment)
        return NavigationUI.onNavDestinationSelected(item,navController) || super.onOptionsItemSelected(item);
    }

    /**
     * 左上角的返回按钮被点击时调用到
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(this, R.id.nav_host_fragment)
        Toast.makeText(this@MenuActivity, "onSupportNavigateUp() called", Toast.LENGTH_SHORT).show()
        return navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()    }
}