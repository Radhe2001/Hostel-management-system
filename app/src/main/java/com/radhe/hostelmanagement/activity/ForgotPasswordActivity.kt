package com.radhe.hostelmanagement.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.radhe.hostelmanagement.R

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = intent.getStringExtra("type")
        if(type == "admin"){
            setContentView(R.layout.admin_forgot_password)

            var regId = findViewById<EditText>(R.id.etAdminForgotRegId)
            var eml = findViewById<EditText>(R.id.etAdminForgotEmail)
            var pswd = findViewById<EditText>(R.id.etAdminForgotPassword)
            var varifyPswd = findViewById<EditText>(R.id.etAdminForgotVarifyPassword)
            var next = findViewById<Button>(R.id.btnAdminForgot)

            next.setOnClickListener {
                val id = regId.text.toString()
                val email = eml.text.toString()
                val password = pswd.text.toString()
                val varifyPassword = varifyPswd.text.toString()

                if(id.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && varifyPassword.isNotEmpty()){
                    if(password == varifyPassword){
                        database = FirebaseDatabase.getInstance().getReference("admin")
                        database.child(id).get().addOnSuccessListener {
                            val dbId:String = it.child("employeeId").value as String
                            val dbEmail:String = it.child("email").value as String
                            val dbName:String = it.child("name").value as String
                            val dbNumber:String = it.child("number").value as String

                            //creating hashmap to update the the child in the database
                            if(id == dbId && email == dbEmail){
                                val user = mapOf<String,String>(
                                    "employeeId" to id,
                                    "email" to email,
                                    "name" to dbName,
                                    "number" to dbNumber,
                                    "password" to password
                                )

                                //updating the child node in the database
                                database.child(id).updateChildren(user).addOnSuccessListener {
                                    Toast.makeText(this@ForgotPasswordActivity,"Password updated successfully",Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } .addOnFailureListener {
                                    Toast.makeText(this@ForgotPasswordActivity,"Failed to update password",Toast.LENGTH_SHORT).show()
                                }
                            } else{
                                Toast.makeText(this@ForgotPasswordActivity,"No such user exists",Toast.LENGTH_SHORT).show()
                            }
                        } .addOnFailureListener{
                            Toast.makeText(this@ForgotPasswordActivity,"Failed",Toast.LENGTH_SHORT).show()
                        }

                    }else{
                        Toast.makeText(this@ForgotPasswordActivity,"Password mismatch",Toast.LENGTH_SHORT).show()
                    }
                } else{
                    Toast.makeText(this@ForgotPasswordActivity,"Please enter the input first",Toast.LENGTH_SHORT).show()
                }
            }
        } else{
            setContentView(R.layout.student_forgot_password)


            var regId = findViewById<EditText>(R.id.etStudentForgotRegId)
            var eml = findViewById<EditText>(R.id.etStudentForgotEmail)
            var pswd = findViewById<EditText>(R.id.etStudentForgotPassword)
            var varifyPswd = findViewById<EditText>(R.id.etAdminForgotVarifyPassword)
            var next = findViewById<Button>(R.id.btnStudentForgot)

            next.setOnClickListener {
                val id = regId.text.toString()
                val email = eml.text.toString()
                val password = pswd.text.toString()
                val varifyPassword = varifyPswd.text.toString()

                if(id.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && varifyPassword.isNotEmpty()){
                    if(password == varifyPassword){
                        database = FirebaseDatabase.getInstance().getReference("student")
                        database.child(id).get().addOnSuccessListener {
                            val dbId:String = it.child("registrationId").value as String
                            val dbEmail:String = it.child("email").value as String
                            val dbName:String = it.child("name").value as String
                            val dbNumber:String = it.child("number").value as String

                            if(id == dbId && email == dbEmail){
                                val user = mapOf<String,String>(
                                    "registrationId" to id,
                                    "email" to email,
                                    "name" to dbName,
                                    "number" to dbNumber,
                                    "password" to password
                                )

                                database.child(id).updateChildren(user).addOnSuccessListener {
                                    Toast.makeText(this@ForgotPasswordActivity,"Password updated successfully",Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } .addOnFailureListener {
                                    Toast.makeText(this@ForgotPasswordActivity,"Failed to update password",Toast.LENGTH_SHORT).show()
                                }
                            } else{
                                Toast.makeText(this@ForgotPasswordActivity,"No such user exists",Toast.LENGTH_SHORT).show()
                            }
                        } .addOnFailureListener{
                            Toast.makeText(this@ForgotPasswordActivity,"Failed",Toast.LENGTH_SHORT).show()
                        }

                    }else{
                        Toast.makeText(this@ForgotPasswordActivity,"Password mismatch",Toast.LENGTH_SHORT).show()
                    }
                } else{
                    Toast.makeText(this@ForgotPasswordActivity,"Please enter the input first",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}