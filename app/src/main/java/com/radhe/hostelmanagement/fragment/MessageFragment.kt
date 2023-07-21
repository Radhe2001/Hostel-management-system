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
import org.w3c.dom.CharacterData
import org.w3c.dom.Text


class MessageFragment : Fragment() {

    lateinit var idView:TextView
    lateinit var messageView:TextView
    lateinit var database:DatabaseReference
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_message, container, false)

        idView = view.findViewById(R.id.txtEnterIdItem)
        messageView = view.findViewById(R.id.txtEnterMessageItem)
        sharedPreferences = context?.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)!!
        val savedId = sharedPreferences.getString("sId","")!!

        database = FirebaseDatabase.getInstance().getReference("message")
        database.child(savedId).get().addOnSuccessListener {
            idView.text = it.child("id").value as String
            messageView.text = it.child("message").value as String
        }.addOnSuccessListener {
            Toast.makeText(context,"Unable to fetch the data",Toast.LENGTH_SHORT).show()
        }


        return view
    }

}