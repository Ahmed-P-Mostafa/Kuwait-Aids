package com.example.studentaid.ui.student

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.studentaid.R
import com.example.studentaid.base.BaseActivity
import com.example.studentaid.data.Student
import com.example.studentaid.data.onlineDatabase.StudentDao
import com.example.studentaid.ui.LandingActivity
import com.example.studentaid.ui.SplashActivity
import com.example.studentaid.utils.Constants
import com.example.studentaid.utils.Utils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.internal.NavigationMenuView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home_student.*
import kotlinx.android.synthetic.main.activity_home_student.view.*
import kotlinx.android.synthetic.main.header_layout.*

class HomeStudentActivity : BaseActivity() {
    private val TAG = "HomeStudentActivity"
    private var student: Student?=null
    private lateinit var navController: NavController
  //  private lateinit var navHostFragment :NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var drawerLayout :DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_student)


        checkForRequest()
        navController = findNavController(R.id.fragment)
        drawerLayout = findViewById(R.id.studentDrawer)
        navigationView = findViewById(R.id.nav_view)

        navigationView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)

      /*  Log.d(TAG, "onCreate: ${student.condition?:"null"}")
        Log.d(TAG, "onCreate: ${student!!.id?:"null"}")*/

    }
    private fun checkForRequest(){
        Log.d(TAG, "checkForRequest: get userFrom shared preferences")
    //    val person = Utils.getUserFromSharedPreferences(this)
        StudentDao.getStudentFromFireStore(auth.uid!!, OnSuccessListener {
            Log.d(TAG, "onCreate: succed")
            student = it.toObject(Student::class.java)!!
            Log.d(TAG, "onCreate: ${student?.condition}")
            if (student?.condition.equals(Constants.CONDITION_NULL)){
                Log.d(TAG, "checkForRequest: ${student?.condition}")
                startActivity(Intent(this,SendRequestActivity::class.java))
            }

        })


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun logout(item: MenuItem) {
        showDialog(title = "Confirmation pop-up",message = "Are you sure want to Log out ?",posButton = "Yes",posAction = { dialogInterface: DialogInterface, i: Int ->
            Utils.logOutUserFromSharedPreefrences(this)
            auth.signOut()
            startActivity(Intent(this, LandingActivity::class.java))

        },negButton = "No",negAction = { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        })
    }
    fun about(item: MenuItem) {
        Toast.makeText(this,"About fragment",Toast.LENGTH_SHORT).show()
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    fun settings(item: MenuItem) {
        Toast.makeText(this,"Settings fragment",Toast.LENGTH_SHORT).show()
        drawerLayout.closeDrawer(GravityCompat.START)
    }

}