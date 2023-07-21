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
import com.radhe.hostelmanagement.fragment.AdminBookingApplicationFragment
import com.radhe.hostelmanagement.fragment.AdminDashboardFragment
import com.radhe.hostelmanagement.R
import com.radhe.hostelmanagement.fragment.AdminProfileFragment

class AdminHomePageActivity : AppCompatActivity() {


    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout : FrameLayout
    lateinit var navigationView: NavigationView
    var previousItem : MenuItem? = null
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home_page)



        drawerLayout = findViewById(R.id.drawerLayoutHomePageAdmin)
        coordinatorLayout = findViewById(R.id.coordinatorLayoutHomePageAdmin)
        toolbar = findViewById(R.id.toolbarHomePageAdmin)
        frameLayout = findViewById(R.id.frameLayoutHomePageAdmin)
        navigationView = findViewById(R.id.navigationLayoutHomePageAdmin)
        sharedPreferences = getSharedPreferences("LoginDetails", MODE_PRIVATE)
        val rId = sharedPreferences.getString("id","")


        setUpToolbar()

        openDashboard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@AdminHomePageActivity,
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

                R.id.itemDashboardAdmin -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayoutHomePageAdmin,
                        AdminDashboardFragment()
                    ).commit()

                    supportActionBar?.title = "Dashboard"
                    drawerLayout.closeDrawers()
                }

                R.id.itemProfileAdmin -> {

                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayoutHomePageAdmin,
                        AdminProfileFragment()
                    ).commit()

                    supportActionBar?.title = "Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.itemAdminLogout -> {
                    val intent = Intent(this@AdminHomePageActivity, MainActivity::class.java)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn",false).apply()
                    startActivity(intent)
                    finish()
                }

                R.id.itemRoomBookingRequestAdmin -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayoutHomePageAdmin,
                        AdminBookingApplicationFragment()
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
            R.id.frameLayoutHomePageAdmin,
            AdminDashboardFragment()
        ).commit()
        supportActionBar?.title = "Dashboard"
        navigationView.setCheckedItem(R.id.itemDashboardAdmin)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if(id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
}