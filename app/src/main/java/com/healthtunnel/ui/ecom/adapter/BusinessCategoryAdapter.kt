package com.healthtunnel.ui.ecom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.R
import com.healthtunnel.data.model.BusinessAboutCategories

class BusinessCategoryAdapter(
    private val responseList: ArrayList<BusinessAboutCategories>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<BusinessCategoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_business_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = responseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.name.text = responseList[position].name


        holder.itemView.setOnClickListener {
            listener.onClick(position)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }
}

