package com.healthtunnel.ui.donatemoney.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.R
import com.healthtunnel.data.model.DonateCancerMoney

class DonateCancerAdapter(
    private val responseList: ArrayList<DonateCancerMoney>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<DonateCancerAdapter.ViewHolder>() {

    var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_donate_cancer, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = responseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {




        holder.amount.text = responseList[position].amount
        holder.desc.text = responseList[position].details
        holder.image.setBackgroundResource(responseList[position].image!!)

        holder.amount.isChecked = selectedPosition == position

        holder.amount.setOnClickListener {
            if (selectedPosition != position){
                notifyItemChanged(selectedPosition)
                selectedPosition = position
            }


            listener.onClick(selectedPosition)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val amount: CheckBox = itemView.findViewById(R.id.amount)
        val desc: TextView = itemView.findViewById(R.id.desc)
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }
}

