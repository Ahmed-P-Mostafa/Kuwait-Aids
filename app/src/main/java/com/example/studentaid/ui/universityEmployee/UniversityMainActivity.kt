package com.example.studentaid.ui.universityEmployee

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.studentaid.R
import com.example.studentaid.base.BaseActivity
import com.example.studentaid.ui.LandingActivity
import com.example.studentaid.utils.Utils
import com.google.android.material.navigation.NavigationView

class UniversityMainActivity : BaseActivity() {

    private lateinit var navController: NavController
    private lateinit var navHostFragment : NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var drawerLayout : DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_university_main)
        navController = findNavController(R.id.university_nav_host)
        drawerLayout = findViewById(R.id.drawer_university)
        navigationView = findViewById(R.id.university_nav_view)

        navigationView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    fun logout_University(item: MenuItem) {

        drawerLayout.closeDrawer(GravityCompat.START)
        showDialog(title = "Confirmation pop-up",message = "Are you sure want to Log out ?",posButton = "Yes",posAction = { dialogInterface: DialogInterface, i: Int ->
            Utils.logOutUserFromSharedPreefrences(this)
            auth.signOut()
            startActivity(Intent(this, LandingActivity::class.java))

        },negButton = "No",negAction = { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.university_nav_host)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}