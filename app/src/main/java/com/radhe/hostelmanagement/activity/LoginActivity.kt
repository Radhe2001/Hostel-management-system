package com.radhe.hostelmanagement.activity

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.radhe.hostelmanagement.R

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = intent.getStringExtra("name")
        sharedPreferences = getSharedPreferences("LoginDetails", MODE_PRIVATE)
        val editor:Editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn",false)

        if(name == "admin"){
            setContentView(R.layout.admin_login)

            val empId = findViewById<EditText>(R.id.etAdminLoginRegId)
            val passwd = findViewById<EditText>(R.id.etAdminLoginPassword)
            val login = findViewById<Button>(R.id.btnAdminLogin)
            val register = findViewById<TextView>(R.id.txtAdminLoginRegister)
            val forgot = findViewById<TextView>(R.id.txtAdminLoginForgotPassword)
            val database:DatabaseReference = FirebaseDatabase.getInstance().getReference("admin")

            login.setOnClickListener {
                val id:String = empId.text.toString()
                val password:String = passwd.text.toString()
                if(id.isNotEmpty() && password.isNotEmpty()){
                    database.child(id).get().addOnSuccessListener {
                        if(it.exists()){
                            val dbId :String = it.child("employeeId").value as String
                            val dbPassword:String = it.child("password").value as String

                            if(id == dbId && password == dbPassword){
                                editor.putString("aId",id).apply()
                                editor.putString("aPassword",password).apply()
                                editor.putBoolean("isLoggedIn",true).apply()
                                editor.putBoolean("admin",true).apply()
                                editor.putBoolean("student",false).apply()
                                val intent = Intent(this@LoginActivity, AdminHomePageActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else{
                                Toast.makeText(this@LoginActivity,"No such user exists",Toast.LENGTH_SHORT).show()
                            }

                        } else{
                            Toast.makeText(this@LoginActivity,"User does not exists",Toast.LENGTH_SHORT).show()
                        }
                    }
                } else{
                    Toast.makeText(this@LoginActivity,"Please enter the required field first",Toast.LENGTH_SHORT).show()
                }
            }

            register.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
                intent.putExtra("type","admin")
                startActivity(intent)
                finish()
            }

            forgot.setOnClickListener {
                val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                intent.putExtra("type","admin")
                startActivity(intent)
                finish()
            }



        } else{
            setContentView(R.layout.student_login)

            val regId = findViewById<EditText>(R.id.etStudentLoginRegId)
            val passwd = findViewById<EditText>(R.id.etStudentLoginPassword)
            val login = findViewById<Button>(R.id.btnStudentLogin)
            val register = findViewById<TextView>(R.id.txtStudentLoginRegister)
            val forgot = findViewById<TextView>(R.id.txtStudentLoginForgotPassword)
            val database:DatabaseReference = FirebaseDatabase.getInstance().getReference("student")


            login.setOnClickListener {
                val id:String = regId.text.toString()
                val password:String = passwd.text.toString()

                if(id.isNotEmpty() && password.isNotEmpty()){
                    database.child(id).get().addOnSuccessListener {
                        if(it.exists()){
                            val dbId :String = it.child("registrationId").value as String
                            val dbPassword:String = it.child("password").value as String

                            if(id == dbId && password == dbPassword){
                                editor.putString("sId",id).apply()
                                editor.putString("sPassword",password).apply()
                                editor.putBoolean("isLoggedIn",true).apply()
                                editor.putBoolean("admin",false).apply()
                                editor.putBoolean("student",true).apply()
                                val intent = Intent(this@LoginActivity, HomePageActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else{
                                Toast.makeText(this@LoginActivity,"No such user exists",Toast.LENGTH_SHORT).show()
                            }

                        } else{
                            Toast.makeText(this@LoginActivity,"User does not exists",Toast.LENGTH_SHORT).show()
                        }
                    }
                } else{
                    Toast.makeText(this@LoginActivity,"Please enter the required field first",Toast.LENGTH_SHORT).show()
                }
            }

            register.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
                intent.putExtra("type","student")
                startActivity(intent)
                finish()
            }

            forgot.setOnClickListener {
                val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                intent.putExtra("type","student")
                startActivity(intent)
                finish()
            }

        }

    }
}