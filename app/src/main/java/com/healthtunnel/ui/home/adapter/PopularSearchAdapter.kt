package com.healthtunnel.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.R
import com.healthtunnel.data.model.DataResult
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class PopularSearchAdapter(
    private val responseList: ArrayList<DataResult>,
    private val activity: FragmentActivity?,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<PopularSearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_popularsearch, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
           return responseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data: DataResult = responseList[position]

        if (activity != null && data.bannerImage.length > 0) {
            Glide.with(activity).load(data.bannerImage).into(holder.pop_search_image)
        }


        holder.itemView.setOnClickListener {
            listener.onClick(position)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pop_search_image: ShapeableImageView = itemView.findViewById(R.id.pop_search_image)
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
        fun onCallClick(pos: Int)
    }
}

