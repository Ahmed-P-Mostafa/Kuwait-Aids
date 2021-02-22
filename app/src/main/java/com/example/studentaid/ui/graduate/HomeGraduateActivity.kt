package com.example.studentaid.ui.graduate

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.studentaid.R
import com.example.studentaid.base.BaseActivity
import com.example.studentaid.data.models.Job
import com.example.studentaid.data.models.Student
import com.example.studentaid.data.onlineDatabase.StudentDao
import com.example.studentaid.ui.LandingActivity
import com.example.studentaid.ui.SplashActivity
import com.example.studentaid.utils.Utils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home_graduate.*

class HomeGraduateActivity : BaseActivity() {
    private val TAG = "HomeGraduateActivity"


    private lateinit var navController: NavController
    //  private lateinit var navHostFragment :NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var drawerLayout : DrawerLayout
    private lateinit var navigationView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_graduate)


        navController = findNavController(R.id.fragment_graduate_nav_host)
        drawerLayout = findViewById(R.id.graduateDrawer)
        navigationView = findViewById(R.id.graduate_nav_view)

        navigationView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)


    }



    fun notifications(item: MenuItem) {
        Toast.makeText(this,"Notifications",Toast.LENGTH_SHORT).show()

        drawerLayout.closeDrawer(GravityCompat.START)
    }
    fun logoutGraduate(item: MenuItem) {
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
        val navController = findNavController(R.id.fragment_graduate_nav_host)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }



}