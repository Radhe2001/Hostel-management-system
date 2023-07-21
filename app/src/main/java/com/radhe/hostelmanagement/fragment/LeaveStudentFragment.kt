package com.radhe.hostelmanagement.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.radhe.hostelmanagement.R
import com.radhe.hostelmanagement.dataClass.StudentLeave

class LeaveStudentFragment : Fragment() {

    lateinit var placeView: EditText
    lateinit var reasonView: EditText
    lateinit var leaveView: EditText
    lateinit var returnView: EditText
    lateinit var contactView: EditText
    lateinit var button: Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var database:DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_leave_student, container, false)


        sharedPreferences = requireContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        val id = sharedPreferences.getString("id","")!!
        placeView = view.findViewById(R.id.etPlaceOfVisit)
        leaveView = view.findViewById(R.id.etLeaveDate)
        reasonView = view.findViewById(R.id.etReason)
        returnView = view.findViewById(R.id.etReturn)
        contactView = view.findViewById(R.id.etContact)
        button = view.findViewById(R.id.btnButton)
        database = FirebaseDatabase.getInstance().getReference("leave_application")

        button.setOnClickListener {

            val place = placeView.text.toString()
            val reason = reasonView.text.toString()
            val returnV = returnView.text.toString()
            val leave = leaveView.text.toString()
            val contact = contactView.text.toString()

            if(place.isNotEmpty() && reason.isNotEmpty() && returnV.isNotEmpty() && leave.isNotEmpty() && contact.isNotEmpty()){

                val user = StudentLeave(id,reason,place,leave,returnV,contact)
                database.child(id).setValue(user).addOnSuccessListener {
                    Toast.makeText(context,"Leave applied successfully",Toast.LENGTH_SHORT).show()
                    placeView.text.clear()
                    reasonView.text.clear()
                    returnView.text.clear()
                    leaveView.text.clear()
                    contactView.text.clear()
                }.addOnFailureListener{
                    Toast.makeText(context,"Failed to apply the leave",Toast.LENGTH_SHORT).show()
                }

            } else{
                Toast.makeText(context,"Fill the text fields first",Toast.LENGTH_SHORT).show()
            }

        }


        return view
    }
}