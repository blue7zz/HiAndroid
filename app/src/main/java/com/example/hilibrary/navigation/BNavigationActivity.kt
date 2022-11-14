package com.example.hilibrary.navigation

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.hilibrary.R
import com.example.hilibrary.databinding.ActivityBnavigationBinding

/**
 * 导航宿主是 Navigation 组件的核心部分之一。导航宿主是一个空容器，用户在您的应用中导航时，目的地会在该容器中交换进出。
 * 导航宿主必须派生于 NavHost。Navigation 组件的默认 NavHost 实现 (NavHostFragment) 负责处理 Fragment 目的地的交换。
 *
 * 注意：Navigation 组件旨在用于具有一个主 activity 和多个 fragment 目的地的应用。
 * 主 activity 与导航图相关联，且包含一个负责根据需要交换目的地的 NavHostFragment。
 * 在具有多个 activity 目的地的应用中，每个 activity 均拥有其自己的导航图。
 */
class BNavigationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityBnavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBnavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        val navController = findNavController(R.id.nav_host_fragment_content_bnavigation)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        //将App bar与NavController绑定
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_bnavigation)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}