package com.radhe.hostelmanagement.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.radhe.hostelmanagement.dataClass.AdminDetails
import com.radhe.hostelmanagement.R
import com.radhe.hostelmanagement.dataClass.StudentDetails


class RegistrationActivity : AppCompatActivity() {
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = intent.getStringExtra("type")
        if(type == "admin"){
            setContentView(R.layout.admin_registration)

            //getting reference to the all the widgets in the layout file
            var empId = findViewById<EditText>(R.id.etAdminRegisterRegId)
            var fullName = findViewById<EditText>(R.id.etAdminRegisterName)
            var email = findViewById<EditText>(R.id.etAdminRegisterEmail)
            var contactNumber = findViewById<EditText>(R.id.etAdminRegisterPhone)
            var password = findViewById<EditText>(R.id.etAdminRegisterPassword)
            var varifyPassword = findViewById<EditText>(R.id.etAdminRegisterVarifyPassword)
            var register = findViewById<Button>(R.id.btnAdminRegister)

            register.setOnClickListener {
                if(empId.text.toString().isNotEmpty() && fullName.text.toString().isNotEmpty() && email.text.toString().isNotEmpty() &&
                    contactNumber.text.toString().isNotEmpty() && password.text.toString().isNotEmpty() &&
                    varifyPassword.text.toString().isNotEmpty() ){

                    if(password.text.toString() == varifyPassword.text.toString()){
                        //this will initialize the local variable with the value entered in the edit text
                        var id = empId.text.toString()
                        var name = fullName.text.toString()
                        var eml = email.text.toString()
                        var number = contactNumber.text.toString()
                        var pswd = password.text.toString()
                        var varifyPswd = varifyPassword.text.toString()
                        database = FirebaseDatabase.getInstance().getReference("admin")

                        val user = AdminDetails(id,name,eml,number,pswd)
                        database.child(id).setValue(user).addOnSuccessListener {
                            //this will clear the text field
                            empId.text.clear()
                            fullName.text.clear()
                            email.text.clear()
//                number.text.clear()
                            password.text.clear()
                            varifyPassword.text.clear()

                            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                            Toast.makeText(this@RegistrationActivity,"Registered successfully",Toast.LENGTH_LONG).show()
                            startActivity(intent)
                            finish()
                        } .addOnFailureListener{
                            Toast.makeText(this@RegistrationActivity,"Failed to update",Toast.LENGTH_LONG).show()
                        }

                    } else{
                        Toast.makeText(this@RegistrationActivity,"Password mismatch",Toast.LENGTH_LONG).show()
                    }
                } else{
                    Toast.makeText(this@RegistrationActivity,"Text fields are empty",Toast.LENGTH_LONG).show()
                }
            }


        }
        else{
            setContentView(R.layout.student_registration)

            var regId = findViewById<EditText>(R.id.etStudentRegisterRegId)
            var fullName = findViewById<EditText>(R.id.etStudentRegisterName)
            var email = findViewById<EditText>(R.id.etStudentRegisterEmail)
            var contactNumber = findViewById<EditText>(R.id.etStudentRegisterPhone)
            var password = findViewById<EditText>(R.id.etStudentRegisterPassword)
            var varifyPassword = findViewById<EditText>(R.id.etStudentRegisterVarifyPassword)
            var register = findViewById<Button>(R.id.btnStudentRegister)

            register.setOnClickListener {
                if(regId.text.toString().isNotEmpty() && fullName.text.toString().isNotEmpty() && email.text.toString().isNotEmpty() &&
                    contactNumber.text.toString().isNotEmpty() && password.text.toString().isNotEmpty() &&
                    varifyPassword.text.toString().isNotEmpty() ){
                    if(password.text.toString() == varifyPassword.text.toString()){
                        var id = regId.text.toString()
                        var name = fullName.text.toString()
                        var eml = email.text.toString()
                        var number = contactNumber.text.toString()
                        var pswd = password.text.toString()
                        var varifyPswd = varifyPassword.text.toString()

                        database = FirebaseDatabase.getInstance().getReference("student")
                        val user = StudentDetails(id,name,eml,number,pswd)
                        database.child(id).setValue(user).addOnSuccessListener {
                            regId.text.clear()
                            fullName.text.clear()
                            email.text.clear()
//                number.text.clear()
                            password.text.clear()
                            varifyPassword.text.clear()

                            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)

                            Toast.makeText(this@RegistrationActivity,"Registered successfully",Toast.LENGTH_LONG).show()
                            startActivity(intent)
                            finish()
                        } . addOnFailureListener {
                            Toast.makeText(this@RegistrationActivity,"Failed to update",Toast.LENGTH_LONG).show()
                        }

                    } else{
                        Toast.makeText(this@RegistrationActivity,"Password mismatch",Toast.LENGTH_LONG).show()
                    }
                } else{
                    Toast.makeText(this@RegistrationActivity,"Text fields are empty",Toast.LENGTH_LONG).show()
                }
            }



        }
    }
}