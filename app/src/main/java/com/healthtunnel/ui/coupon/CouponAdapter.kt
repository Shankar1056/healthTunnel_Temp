package com.healthtunnel.ui.coupon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.R
import com.healthtunnel.data.model.CouponResult

class CouponAdapter(
    private val responseList: ArrayList<CouponResult>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<CouponAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_coupon_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = responseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.couponCode.text = responseList[position].couponCode
        holder.description.text = responseList[position].description


        holder.couponCodeButton.setOnClickListener {
            listener.onClick(position)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val couponCode: TextView = itemView.findViewById(R.id.couponCode)
        val description: TextView = itemView.findViewById(R.id.description)
        val couponCodeButton: TextView = itemView.findViewById(R.id.couponCodeButton)
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }
}

