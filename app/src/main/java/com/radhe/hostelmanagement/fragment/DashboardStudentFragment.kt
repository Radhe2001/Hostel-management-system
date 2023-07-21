package com.radhe.hostelmanagement.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.radhe.hostelmanagement.adapterClass.DashboardRecyclerAdapterStudent
import com.radhe.hostelmanagement.R
import com.radhe.hostelmanagement.dataClass.RoomDetails


class DashboardStudentFragment : Fragment() {

    lateinit var database: DatabaseReference
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var roomArray:ArrayList<RoomDetails>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashnoard, container, false)


        roomArray = arrayListOf<RoomDetails>()
        recyclerView = view.findViewById(R.id.recyclerViewDashboardStudent)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        database = FirebaseDatabase.getInstance().getReference("rooms")

        database.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(reference in snapshot.children){
                        val room = reference.child("room_details").value as String
                        val type = reference.child("type").value as String
                        val price = reference.child("price").value as String
                        val user = RoomDetails(room,price,type)
                        roomArray.add(user)
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity,"Error occured",Toast.LENGTH_SHORT).show()
            }

        })

        recyclerView.adapter = DashboardRecyclerAdapterStudent(activity as Context, roomArray)

        return view
    }
}