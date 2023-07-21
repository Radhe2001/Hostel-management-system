package com.radhe.hostelmanagement.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.radhe.hostelmanagement.dataClass.BookRoom
import com.radhe.hostelmanagement.R

class BookRoomActivity : AppCompatActivity() {
    lateinit var details:TextView
    lateinit var roomType:TextView
    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var contact: EditText
    lateinit var password: EditText
    lateinit var button: Button
    lateinit var database:DatabaseReference
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_room)

        details = findViewById(R.id.txtRoomDetails)
        roomType = findViewById(R.id.txtRoomType)
        name = findViewById(R.id.etName)
        email = findViewById(R.id.etEmail)
        contact = findViewById(R.id.etNumber)
        password = findViewById(R.id.etPassword)
        button = findViewById(R.id.btnButton)
        sharedPreferences = getSharedPreferences("LoginDetails", MODE_PRIVATE)

        val room_details:String= intent.getStringExtra("room_details")!!
        val type:String =intent.getStringExtra("type")!!

        details.text = room_details
        roomType.text = type

        button.setOnClickListener {
            val studentName = name.text.toString()
            val studentEmail = email.text.toString()
            val studentContact = contact.text.toString()
            val studentPassword = password.text.toString()
            val id:String =sharedPreferences.getString("id","")!!
            val pswd:String =sharedPreferences.getString("password","")!!

            if(pswd==studentPassword){
                database = FirebaseDatabase.getInstance().getReference("room_booking")
                val user = BookRoom(id,studentName,studentEmail,studentContact,room_details,type)
                database.child(id).setValue(user).addOnSuccessListener {
                    Toast.makeText(this@BookRoomActivity,"Application uploaded succesfully",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this@BookRoomActivity,"Failed to book room",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this@BookRoomActivity,"Enter the correct password",Toast.LENGTH_SHORT).show()
            }
        }
    }
}