package com.healthtunnel.ui.wellnesscorner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.R
import com.healthtunnel.data.model.SubCatResult
import com.healthtunnel.data.model.WellnessCornerListResult
import com.healthtunnel.data.model.WellnessResult
import com.bumptech.glide.Glide

class WellnessListAdapter(
    private val responseList: ArrayList<WellnessCornerListResult>,
    private val activity: FragmentActivity?,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<WellnessListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_wellness_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = responseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data: WellnessCornerListResult = responseList[position]

        holder.text.text = data.articleSubject
        if (activity != null) {
            Glide.with(activity).load(data.articleBannerLogo).into(holder.image)
        }


        holder.itemView.setOnClickListener {
            listener.onClick(position)
        }

        /*holder.image.setOnClickListener {
            listener.onCallClick(position)
        }*/

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val text: TextView = itemView.findViewById(R.id.text)
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }
}

