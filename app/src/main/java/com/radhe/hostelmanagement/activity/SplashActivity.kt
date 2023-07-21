package com.radhe.hostelmanagement.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.radhe.hostelmanagement.R

class SplashActivity : AppCompatActivity(){
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharedPreferences = getSharedPreferences("LoginDetails", MODE_PRIVATE)
        if(sharedPreferences.getBoolean("student",false)){
            if(sharedPreferences.getBoolean("isLoggedIn",false)){
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@SplashActivity,HomePageActivity::class.java)
                    startActivity(intent)
                    finish()
                },2000)
            }else{
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@SplashActivity,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                },2000)
            }
        }else{
            if(sharedPreferences.getBoolean("isLoggedIn",false)){
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@SplashActivity,AdminHomePageActivity::class.java)
                    startActivity(intent)
                    finish()
                },2000)
            }else{
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@SplashActivity,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                },2000)
            }
        }
    }
}