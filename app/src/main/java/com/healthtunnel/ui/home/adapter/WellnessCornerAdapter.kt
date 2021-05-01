package com.healthtunnel.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.R
import com.healthtunnel.data.model.WellnessResult
import com.bumptech.glide.Glide

class WellnessCornerAdapter(
    private val responseList: ArrayList<WellnessResult>,
    private val activity: FragmentActivity?,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<WellnessCornerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_wellness_corner, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = responseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data: WellnessResult = responseList[position]

        holder.catName.text = data.name
        if (activity != null) {
            Glide.with(activity).load(data.articleTypeImage).into(holder.image)
        }


        holder.itemView.setOnClickListener {
            listener.onClick(position)
        }



    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val catName: TextView = itemView.findViewById(R.id.catName)
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }
}

