package com.example.quiz6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.quiz6.databinding.ActivityMainBinding
import com.example.quiz6.databinding.NavHeaderMainBinding
import com.example.quiz6.ui.UbikeViewModel
import com.example.quiz6.ui.UbikeViewModelFactory
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var factory: UbikeViewModelFactory
    lateinit var viewModel: UbikeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,factory)[UbikeViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.customToolbar)
        // 移除 ActionBar 的標題
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val headerView = navView.getHeaderView(0)
        val headerMainBinding = NavHeaderMainBinding.bind(headerView)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_useGuide, R.id.nav_payway
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            // 不使用預設的箭頭
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            // 不使用預設的漢堡圖
            supportActionBar?.setHomeButtonEnabled(false)
        }

        binding.customHamburgerIcon.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)){
                drawerLayout.closeDrawer(GravityCompat.END)
            }else{
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }


        binding.customToolbar.setNavigationOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.END))
                drawerLayout.closeDrawer(GravityCompat.END);
            else
                drawerLayout.openDrawer(GravityCompat.END);
        }
        navView.setNavigationItemSelectedListener {  menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    navController.navigate(R.id.nav_home)
                }
                R.id.nav_useGuide -> {
                    navController.navigate(R.id.nav_useGuide)
                }
                R.id.nav_payway -> {
                    navController.navigate(R.id.nav_payway)
                }
            }

            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            }
            true
        }
        headerMainBinding.ivClose.setOnClickListener { drawerLayout.closeDrawer(GravityCompat.END) }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}