package com.radhe.hostelmanagement.adapterClass

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.radhe.hostelmanagement.R
import com.radhe.hostelmanagement.activity.BookRoomActivity
import com.radhe.hostelmanagement.dataClass.RoomDetails

class DashboardRecyclerAdapterStudent(val context:Context,val roomArray:ArrayList<RoomDetails>) :
    RecyclerView.Adapter<DashboardRecyclerAdapterStudent.DashboardViewHolder>(){


    class DashboardViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val room:TextView = view.findViewById(R.id.txtRoomDetails)
        val price:TextView = view.findViewById(R.id.txtRoomPrice)
        val type:TextView = view.findViewById(R.id.txtRoomType)
        val button: Button = view.findViewById(R.id.btnBookNow)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_dashboard_view_holder_student,parent,false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DashboardViewHolder,
        position: Int
    ) {
        holder.room.text = roomArray[position].room_details
        holder.type.text = roomArray[position].type
        holder.price.text = roomArray[position].price
        holder.button.setOnClickListener {

            val intent = Intent(context, BookRoomActivity::class.java)
            intent.putExtra("room_details",roomArray[position].room_details)
            intent.putExtra("type",roomArray[position].type)
            context.startActivity(intent)


        }
    }

    override fun getItemCount(): Int {
        return roomArray.size
    }
}