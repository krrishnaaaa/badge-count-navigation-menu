package com.pcsalt.example.badgecountonnavigationmenu

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.pcsalt.example.badgecountonnavigationmenu.badgeview.BadgeDrawerArrowDrawable


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var badgeDrawerArrowDrawable: BadgeDrawerArrowDrawable

    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            count += 1
            setBadgeCount(count)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        mDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setBadgeNavigationIcon() {
        supportActionBar?.themedContext?.let {
            if (!::badgeDrawerArrowDrawable.isInitialized) {
                badgeDrawerArrowDrawable = BadgeDrawerArrowDrawable(it)
            }
            mDrawerToggle.drawerArrowDrawable = badgeDrawerArrowDrawable
            badgeDrawerArrowDrawable.backgroundColor =
                ContextCompat.getColor(this, R.color.badge_color)
        }
    }

    private fun setBadgeCount(count: Int) {
        setBadgeNavigationIcon()
        if (count % 7 == 0) {
            badgeDrawerArrowDrawable.setEnabled(false)
            badgeDrawerArrowDrawable.setText(null)
        } else {
            badgeDrawerArrowDrawable.setEnabled(true)
            badgeDrawerArrowDrawable.setText("$count")
        }
    }
}