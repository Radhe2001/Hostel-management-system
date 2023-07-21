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
import com.radhe.hostelmanagement.dataClass.MessageClass
import com.radhe.hostelmanagement.R
import com.radhe.hostelmanagement.dataClass.LeaveApplicationRequest
import com.radhe.hostelmanagement.dataClass.PreviousLeaveRequest

class AdminDashboardAdapter(val context:Context,val leaveArray:ArrayList<LeaveApplicationRequest>):
    RecyclerView.Adapter<AdminDashboardAdapter.AdapterClass>() {
    class AdapterClass(view: View):RecyclerView.ViewHolder(view) {
        val idView :TextView = view.findViewById(R.id.txtEnterIdItem)
        val placeView:TextView = view.findViewById(R.id.txtEnterPlaceItem)
        val reasonView:TextView = view.findViewById(R.id.txtEnterReasonItem)
        val leaveDateView:TextView = view.findViewById(R.id.txtEnterLeaveItem)
        val returnDateView:TextView = view.findViewById(R.id.txtEnterReturnItem)
        val contactView:TextView = view.findViewById(R.id.txtEnterGuardianItem)
        val button: Button = view.findViewById(R.id.btnApprove)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.amin_dashboard_single_item,parent,false)
        return AdapterClass(view)
    }

    override fun onBindViewHolder(holder: AdapterClass, position: Int) {
        val obj = leaveArray[position]
        holder.idView.text = obj.id
        holder.placeView.text = obj.place
        holder.reasonView.text = obj.reason
        holder.leaveDateView.text = obj.leaveDate
        holder.returnDateView.text = obj.returnDate
        holder.contactView.text = obj.contact

        holder.button.setOnClickListener {
            var database:DatabaseReference = FirebaseDatabase.getInstance().getReference("message")
            val user = MessageClass(obj.id,"Leave approved")
            database.child(obj.id).setValue(user).addOnSuccessListener {
                database = FirebaseDatabase.getInstance().getReference("leaveId")
                var leaveId:Int
                database.get().addOnSuccessListener {
                    val sId = it.child("id").value as String
                    leaveId = sId.toInt()
                    var user : PreviousLeaveRequest
                    database = FirebaseDatabase.getInstance().getReference("leave_application")
                    database.child(obj.id).get().addOnSuccessListener {
                        val id = it.child("id").value as String
                        val place = it.child("place_of_visit").value as String
                        val reason = it.child("reason_of_leave").value as String
                        val leaveDate = it.child("leave_date").value as String
                        val returnDate = it.child("return_date").value as String
                        val contact = it.child("guardian_contact").value as String
                        user = PreviousLeaveRequest(id,place,reason,leaveDate,returnDate,contact)

                        database = FirebaseDatabase.getInstance().getReference("pastLeaveRequest")
                        database.child(sId).setValue(user).addOnSuccessListener {
                            Toast.makeText(context,"Successfully updated",Toast.LENGTH_SHORT).show()
                            leaveId += 1
                            val map = mapOf<String,String>(
                                "id" to leaveId.toString()
                            )
                            database = FirebaseDatabase.getInstance().getReference("leaveId")
                            database.updateChildren(map).addOnSuccessListener {
                                Toast.makeText(context,"id updated",Toast.LENGTH_SHORT).show()
                            }

                        }.addOnFailureListener {
                            Toast.makeText(context,"Failed to update",Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
                    }


                }
                database = FirebaseDatabase.getInstance().getReference("leave_application")
                database.child(obj.id).removeValue().addOnSuccessListener {
                    Toast.makeText(context,"Leave approved",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(context,"Failed to approve",Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(context,"Failed to approve",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun getItemCount(): Int {
        return leaveArray.size
    }
}