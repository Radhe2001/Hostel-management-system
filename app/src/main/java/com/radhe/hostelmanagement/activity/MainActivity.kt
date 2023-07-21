package com.radhe.hostelmanagement.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.radhe.hostelmanagement.R

class MainActivity : AppCompatActivity() {

    lateinit var student:CardView
    lateinit var admin:CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        student = findViewById(R.id.studentCard)
        admin = findViewById(R.id.adminCard)

        student.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            intent.putExtra("name","student")
            startActivity(intent)
            finish()
        }

        admin.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            intent.putExtra("name","admin")
            startActivity(intent)
            finish()
        }

    }
}