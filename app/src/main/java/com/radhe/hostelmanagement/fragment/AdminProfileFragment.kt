package com.radhe.hostelmanagement.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.radhe.hostelmanagement.R


class AdminProfileFragment : Fragment() {

    private lateinit var nameView:TextView
    private lateinit var emailView:TextView
    private lateinit var contactView:TextView
    private lateinit var idView:TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var database:DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_profile, container, false)

        nameView = view.findViewById(R.id.txtEnterNameAdmin)
        emailView = view.findViewById(R.id.txtEnterEmailAdmin)
        contactView = view.findViewById(R.id.txtEnterContactAdmin)
        idView = view.findViewById(R.id.txtEnterIdAdmin)

        sharedPreferences = context?.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)!!
        val savedId = sharedPreferences.getString("aId","")!!
        database = FirebaseDatabase.getInstance().getReference("admin")
        database.child(savedId).get().addOnSuccessListener {
            val name = it.child("name").value as String
            val employeeId = it.child("employeeId").value as String
            val email= it.child("email").value as String
            val number = it.child("number").value as String

            nameView.text = name
            emailView.text = email
            contactView.text = number
            idView.text = employeeId

        }.addOnFailureListener {
            Toast.makeText(context,"Failed to fetch the data",Toast.LENGTH_SHORT).show()
        }

        return view
    }

}