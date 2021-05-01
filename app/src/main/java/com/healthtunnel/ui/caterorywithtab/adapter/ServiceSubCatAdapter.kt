package com.healthtunnel.ui.caterorywithtab.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.R
import com.healthtunnel.data.model.SubCatResult
import com.bumptech.glide.Glide

class ServiceSubCatAdapter(
    private val responseList: ArrayList<SubCatResult>,
    private val activity: FragmentActivity?,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<ServiceSubCatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_home_sub_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = responseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data: SubCatResult = responseList[position]

        holder.serviceName.text = data.serviceName
        holder.serviceDesc.text = data.description
        if (activity != null) {
            Glide.with(activity).load(data.apiLogo).into(holder.image)
        }


        holder.itemView.setOnClickListener {
            listener.onClick(position)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val serviceName: TextView = itemView.findViewById(R.id.serviceName)
        val serviceDesc: TextView = itemView.findViewById(R.id.serviceDesc)
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }
}

