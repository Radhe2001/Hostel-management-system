package com.radhe.hostelmanagement.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.radhe.hostelmanagement.adapterClass.AdminDashboardAdapter
import com.radhe.hostelmanagement.dataClass.LeaveApplicationRequest
import com.radhe.hostelmanagement.R

class AdminDashboardFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var database: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewDashboardAdmin)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        val leaveArray = arrayListOf<LeaveApplicationRequest>()

        database = FirebaseDatabase.getInstance().getReference("leave_application")
        database.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (reference in snapshot.children){
                        val id = reference.child("id").value as String
                        val place = reference.child("place_of_visit").value as String
                        val reason = reference.child("reason_of_leave").value as String
                        val leaveDate = reference.child("leave_date").value as String
                        val returnDate = reference.child("return_date").value as String
                        val contact = reference.child("guardian_contact").value as String
//
//                        Toast.makeText(context,returnDate,Toast.LENGTH_SHORT).show()
                        val user = LeaveApplicationRequest(id,place,reason,leaveDate,returnDate,contact)
                        leaveArray.add(user)
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                } else{
                    Toast.makeText(context,"No pending Leaves",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Failed to fetch the data",Toast.LENGTH_SHORT).show()
            }

        })

        recyclerView.adapter = AdminDashboardAdapter(activity as Context,leaveArray)

        return view
    }

}