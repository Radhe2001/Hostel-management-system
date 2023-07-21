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
import com.radhe.hostelmanagement.adapterClass.AdapterBookingClass
import com.radhe.hostelmanagement.dataClass.BookingDetailsClass
import com.radhe.hostelmanagement.R


class AdminBookingApplicationFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var database: DatabaseReference
    lateinit var roomDetailsArray:ArrayList<BookingDetailsClass>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_booking_application, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewBookingAdmin)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        roomDetailsArray = arrayListOf<BookingDetailsClass>()
        database = FirebaseDatabase.getInstance().getReference("room_booking")

        database.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(reference in snapshot.children){
                    val id = reference.child("id").value as String
                    val name = reference.child("name").value as String
                    val email = reference.child("email").value as String
                    val roomDetails = reference.child("room_details").value as String
                    val roomType = reference.child("type").value as String
                    val contact = reference.child("contact").value as String
                    val user = BookingDetailsClass(id,name,email,roomDetails,roomType,contact)

                    roomDetailsArray.add(user)
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Failed to fetch data",Toast.LENGTH_SHORT).show()
            }

        })

        recyclerView.adapter = AdapterBookingClass(activity as Context,roomDetailsArray)

        return view
    }
}