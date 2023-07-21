package com.radhe.hostelmanagement.fragment

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.radhe.hostelmanagement.R

class ProfileStudentFragment : Fragment() {

    lateinit var nameView :TextView
    lateinit var idView :TextView
    lateinit var contactView :TextView
    lateinit var emailView :TextView
    lateinit var database : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_student, container, false)

        nameView = view.findViewById(R.id.txtEnterName)
        idView = view.findViewById(R.id.txtEnterId)
        contactView = view.findViewById(R.id.txtEnterContact)
        emailView = view.findViewById(R.id.txtEnterEmail)
        lateinit var name:String
        lateinit var contact:String
        lateinit var email:String

        val sharedPreferences = context?.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)!!
        val id = sharedPreferences.getString("sId","")!!
            database = FirebaseDatabase.getInstance().getReference("student")
            database.child(id).get().addOnSuccessListener {
                if(it.exists()){
                    name = it.child("name").value as String
                    contact = it.child("number").value as String
                    email = it.child("email").value as String

                    nameView.text = name
                    contactView.text = contact
                    emailView.text = email
                    idView.text = id
                } else{
                    Toast.makeText(activity,"It does not exists",Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener{
                Toast.makeText(activity,"Failed to fetch data",Toast.LENGTH_SHORT).show()
            }


        return view
    }
}