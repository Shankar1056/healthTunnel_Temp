package com.healthtunnel.ui.ecom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.R
import com.healthtunnel.data.model.FilterCatResult

class FilterAdapter(
    private val responseList: ArrayList<FilterCatResult>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<FilterAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_filter_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = responseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.name.text = responseList[position].name

        if (responseList[position].isChecked) {
            holder.iv.setBackgroundResource(R.drawable.ic_tick)
        } else {
            holder.iv.setBackgroundResource(R.drawable.ic_check_grey)
        }


        holder.itemView.setOnClickListener {
            for (item in responseList) {
                item.isChecked = false
            }
            responseList[position].isChecked = true
            notifyDataSetChanged()
            listener.onClick(position)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val iv: ImageView = itemView.findViewById(R.id.iv)
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }
}

