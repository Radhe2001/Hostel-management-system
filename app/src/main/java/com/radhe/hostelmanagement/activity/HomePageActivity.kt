package com.radhe.hostelmanagement.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.radhe.hostelmanagement.fragment.MessageFragment
import com.radhe.hostelmanagement.R
import com.radhe.hostelmanagement.fragment.DashboardStudentFragment
import com.radhe.hostelmanagement.fragment.LeaveStudentFragment
import com.radhe.hostelmanagement.fragment.ProfileStudentFragment

class HomePageActivity : AppCompatActivity() {


    lateinit var drawerLayout:DrawerLayout
    lateinit var coordinatorLayout:CoordinatorLayout
    lateinit var toolbar:Toolbar
    lateinit var frameLayout : FrameLayout
    lateinit var navigationView:NavigationView
    var previousItem : MenuItem? = null
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home_page_student)


        drawerLayout = findViewById(R.id.drawerLayoutHomePageStudent)
        coordinatorLayout = findViewById(R.id.coordinatorLayoutHomePageStudent)
        toolbar = findViewById(R.id.toolbarHomePageStudent)
        frameLayout = findViewById(R.id.frameLayoutHomePageStudent)
        navigationView = findViewById(R.id.navigationLayoutHomePageStudent)
        sharedPreferences = getSharedPreferences("LoginDetails", MODE_PRIVATE)
        val rId = sharedPreferences.getString("id","")


        setUpToolbar()

        openDashboard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@HomePageActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )


        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            if(previousItem != null){
                previousItem?.isChecked = false
            }
            previousItem?.isChecked = false
            it.isCheckable = true
            it.isChecked = true
            previousItem = it

            when(it.itemId){

                R.id.itemDashboard -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayoutHomePageStudent,
                        DashboardStudentFragment()
                    ).commit()

                    supportActionBar?.title = "Dashboard"
                    drawerLayout.closeDrawers()
                }

                R.id.itemProfile -> {

                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayoutHomePageStudent,
                        ProfileStudentFragment()
                    ).commit()

                    supportActionBar?.title = "Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.itemLogout -> {
                    val intent = Intent(this@HomePageActivity,MainActivity::class.java)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn",false).apply()
                    startActivity(intent)
                    finish()
                }
                R.id.itemMessage -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.frameLayoutHomePageStudent,
                    MessageFragment()
                ).commit()

                supportActionBar?.title = "Messages"
                drawerLayout.closeDrawers()
                }

                R.id.itemLeave -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayoutHomePageStudent,
                        LeaveStudentFragment()
                    ).commit()

                    supportActionBar?.title = "Apply Leave"
                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }

    }

    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Dashboard"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun openDashboard(){
        supportFragmentManager.beginTransaction().replace(
            R.id.frameLayoutHomePageStudent,
            DashboardStudentFragment()
        ).commit()
        supportActionBar?.title = "Dashboard"
        navigationView.setCheckedItem(R.id.itemDashboard)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if(id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
}