package com.healthtunnel.ui.ecom.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.R
import com.healthtunnel.data.model.BusinessAboutCategories

class BusinessShopCatAdapter(
    private val responseList: ArrayList<BusinessAboutCategories>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<BusinessShopCatAdapter.ViewHolder>() {
    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_shop_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = responseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.text.text = responseList[position].name

        if (selectedPosition == position) {
            holder.text.setTextColor(Color.parseColor("#1539B0"))
            holder.text.setBackgroundResource(R.drawable.semi_circular_corner)
        } else {
            holder.text.setTextColor(Color.parseColor("#636060"))
            holder.text.setBackgroundResource(R.drawable.semi_circular_corner_border)
        }
        holder.itemView.setOnClickListener {
            if (selectedPosition != position) {
                notifyDataSetChanged()
                selectedPosition = position
            }
            listener.onClick(position)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.text)
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }
}

