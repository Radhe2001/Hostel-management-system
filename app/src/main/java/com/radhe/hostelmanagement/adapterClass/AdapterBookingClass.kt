package com.radhe.hostelmanagement.adapterClass

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.radhe.hostelmanagement.R
import com.radhe.hostelmanagement.dataClass.BookingDetailsClass
import com.radhe.hostelmanagement.dataClass.MessageDetailsBooking
import com.radhe.hostelmanagement.dataClass.PreviousBookingDetails

class AdapterBookingClass(val context:Context,val bookingArray:ArrayList<BookingDetailsClass>) :
    RecyclerView.Adapter<AdapterBookingClass.AdapterClass>(){

    lateinit var database:DatabaseReference

    class AdapterClass(view: View): RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.txtEnterIdItemBooking)
        val nameView: TextView = view.findViewById(R.id.txtEnterNameItemBooking)
        val emailView: TextView = view.findViewById(R.id.txtEnterEmailItemBooking)
        val detailsView: TextView = view.findViewById(R.id.txtEnterRoomItemBooking)
        val typeView: TextView = view.findViewById(R.id.txtEnterTypeItemBooking)
        val contactView: TextView = view.findViewById(R.id.txtEnterContactItemBooking)
        val button:Button = view.findViewById(R.id.btnApproveBooking)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_booking_single_item,parent,false)
        return AdapterClass(view)
    }

    override fun getItemCount(): Int {
        return bookingArray.size
    }

    override fun onBindViewHolder(holder: AdapterClass, position: Int) {
        holder.idView.text = bookingArray[position].id
        holder.nameView.text = bookingArray[position].name
        holder.emailView.text = bookingArray[position].email
        holder.detailsView.text = bookingArray[position].roomDetails
        holder.typeView.text = bookingArray[position].roomType
        holder.contactView.text = bookingArray[position].contact

        holder.button.setOnClickListener {

            lateinit var idString:String
             var id : Int
            database = FirebaseDatabase.getInstance().getReference("roomId")
            database.get().addOnSuccessListener {
                idString = it.child("id").value as String
                id = idString.toInt()
                id += 1
                database = FirebaseDatabase.getInstance().getReference("roomId")
                val map = mapOf<String,String>(
                    "id" to id.toString()
                )
                database.updateChildren(map).addOnSuccessListener {
                    val user = PreviousBookingDetails(bookingArray[position].id,bookingArray[position].name,bookingArray[position].email,
                        bookingArray[position].roomDetails,bookingArray[position].roomType,bookingArray[position].contact)
                    database = FirebaseDatabase.getInstance().getReference("previous_room_booking")

                    database.child(idString).setValue(user).addOnSuccessListener {
                        database = FirebaseDatabase.getInstance().getReference("room_booking")
                        database.child(bookingArray[position].id).removeValue().addOnSuccessListener {
                            database = FirebaseDatabase.getInstance().getReference("message")
                            val message = MessageDetailsBooking(bookingArray[position].id,"Room booked")
                            database.child(bookingArray[position].id).setValue(message).addOnSuccessListener {
                                Toast.makeText(context,"message updated",Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener {
                                Toast.makeText(context,"Failed to update message",Toast.LENGTH_SHORT).show()
                            }
                        }.addOnFailureListener {
                            Toast.makeText(context,"Failed to delete",Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(context,"Failed to update the details",Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(context,"Failed to update id",Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(context,"Failed to get the id",Toast.LENGTH_SHORT).show()
            }
        }
    }
}