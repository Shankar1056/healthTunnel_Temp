package com.healthtunnel.ui.staticform.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.R
import com.healthtunnel.data.model.AvailableTests

class ServiceCategoryAdapter(
    private val responseList: ArrayList<AvailableTests>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<ServiceCategoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.activity_service_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = responseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.service.text = responseList[position].name


        holder.close.setOnClickListener {
            listener.onClick(position)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val service: TextView = itemView.findViewById(R.id.service)
        val close: ImageView = itemView.findViewById(R.id.close)
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }
}

