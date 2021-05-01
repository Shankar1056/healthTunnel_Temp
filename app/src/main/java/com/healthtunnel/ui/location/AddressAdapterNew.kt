package com.healthtunnel.ui.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.R

class AddressAdapterNew(
    private val responseList: ArrayList<String>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<AddressAdapterNew.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_address_adapter, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = responseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.location_name.text = responseList[position]


        holder.itemView.setOnClickListener {
            listener.onClick(position)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val location_name: TextView = itemView.findViewById(R.id.location_name)
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }
}

