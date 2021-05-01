package com.healthtunnel.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healthtunnel.R
import com.healthtunnel.data.model.CatResult

class HomeCategoryAdapterHor(
    private val responseList: ArrayList<CatResult>,
    private val activity: FragmentActivity?,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<HomeCategoryAdapterHor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_home_category_hor, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return responseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data: CatResult = responseList[position]

        if (activity != null && data.real_image != null) {
            Glide.with(activity).load(data.real_image).into(holder.image)
        }


        holder.itemView.setOnClickListener {
            listener.onClick(position)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageBorder)
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }
}

