package com.kseyko.veriimkb.ui

import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.kseyko.veriimkb.R
import com.kseyko.veriimkb.R.id.nav_home
import com.kseyko.veriimkb.databinding.ActivityMainBinding
import com.kseyko.veriimkb.ui.base.BaseActivity
import com.kseyko.veriimkb.ui.detail.DetailFragment
import com.kseyko.veriimkb.ui.detail.DetailFragmentDirections
import com.kseyko.veriimkb.ui.home.HomeFragment
import com.kseyko.veriimkb.ui.home.HomeFragmentDirections
import com.kseyko.veriimkb.ui.list.ListFragment
import com.kseyko.veriimkb.ui.list.ListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onInitView() {
        super.onInitView()
        setSupportActionBar(binding.appBarMain.toolbar)

        navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(nav_home, R.id.nav_list, R.id.nav_detail), binding.drawerLayout
        )

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        binding.navView.apply {
            setupWithNavController(navController)
            setNavigationItemSelectedListener(this@MainActivity)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var period = ""

        when (item.itemId) {
            R.id.all -> period = "all"
            R.id.increasing -> period = "increasing"
            R.id.decreasing -> period = "decreasing"
            R.id.volume30 -> period = "volume30"
            R.id.volume50 -> period = "volume50"
            R.id.volume100 -> period = "volume100"
        }
        when ((navController.currentDestination as FragmentNavigator.Destination).className) {
            HomeFragment::class.java.name -> navController.navigate(
                HomeFragmentDirections.actionNavHomeToNavList(
                    period
                )
            )
            ListFragment::class.java.name -> navController.navigate(
                ListFragmentDirections.actionNavListSelf(
                    period
                )
            )
            DetailFragment::class.java.name -> navController.navigate(
                DetailFragmentDirections.actionNavDetailToNavList(
                    period
                )
            )
        }
        binding.drawerLayout.closeDrawers()
        return true
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()

    }


    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_content_main).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}