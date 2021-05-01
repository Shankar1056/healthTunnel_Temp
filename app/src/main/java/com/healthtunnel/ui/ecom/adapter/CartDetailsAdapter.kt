package com.healthtunnel.ui.ecom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.R
import com.healthtunnel.data.model.CartDetailsResult

class CartDetailsAdapter(
    private val responseList: ArrayList<CartDetailsResult>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<CartDetailsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_cart, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = responseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.pame.text = responseList[position].productId.name
        holder.amout.text =
            "Rs. ${responseList[position].planId.price}/${responseList[position].planId.name}"
        holder.quantity.text = responseList[position].quantity.toString()


        holder.plus.setOnClickListener {
            listener.onPlus(position)
        }

        holder.minus.setOnClickListener {
            listener.onMinus(position)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pame: TextView = itemView.findViewById(R.id.pame)
        val amout: TextView = itemView.findViewById(R.id.amout)
        val quantity: TextView = itemView.findViewById(R.id.quantity)
        val minus: ImageView = itemView.findViewById(R.id.minus)
        val plus: ImageView = itemView.findViewById(R.id.plus)
    }

    interface OnItemClickListener {
        fun onPlus(pos: Int)
        fun onMinus(pos: Int)
    }
}

