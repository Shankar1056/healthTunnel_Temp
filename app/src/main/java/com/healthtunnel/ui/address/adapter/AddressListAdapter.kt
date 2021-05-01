package com.healthtunnel.ui.address.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.R
import com.healthtunnel.data.model.AddressResult

class AddressListAdapter(
    private val responseList: ArrayList<AddressResult>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<AddressListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_address, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = responseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = responseList[position]

//        holder.userName.text = responseList[position].name
        holder.flatNo.text =
            "${data.flat}, ${data.address}, ${data.address}, ${data.city}, ${data.pincode}"

        holder.flatNo.setOnClickListener {
            listener.onClick(position)
        }

        holder.itemView.setOnClickListener {
            listener.onClick(position)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.userName)
        val flatNo: RadioButton = itemView.findViewById(R.id.flatNo)
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }
}

