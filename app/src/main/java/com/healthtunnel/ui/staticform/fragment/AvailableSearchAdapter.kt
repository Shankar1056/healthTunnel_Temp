package com.healthtunnel.ui.staticform.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.R
import com.healthtunnel.data.model.AvailableTests

class AvailableSearchAdapter(
    private val responseList: ArrayList<AvailableTests>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<AvailableSearchAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_available_test_search, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = responseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.labName.text = responseList[position].name
        holder.labAddress.text = HtmlCompat.fromHtml(responseList[position].description.toString(),
            HtmlCompat.FROM_HTML_MODE_LEGACY)

        holder.labAddressDetails.text =
            HtmlCompat.fromHtml(responseList[position].description.toString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY)

        holder.checkBox.isChecked = responseList[position].isSelected

        if (responseList[position].isView) {
            holder.labAddressDetails.visibility = View.VISIBLE
            holder.labAddress.visibility = View.GONE
            holder.viewDetails.text = "Hide Details"
        } else {
            holder.labAddressDetails.visibility = View.GONE
            holder.labAddress.visibility = View.VISIBLE
            holder.viewDetails.text = "View Details"
        }


        holder.checkBox.setOnClickListener {
            responseList[position].isSelected = holder.checkBox.isChecked
            notifyDataSetChanged()
        }

        holder.viewDetails.setOnClickListener {
            responseList[position].isView = !responseList[position].isView
            notifyDataSetChanged()
        }

        /*holder.itemView.setOnClickListener {
            listener.onClick(position)
        }*/


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labName: TextView = itemView.findViewById(R.id.labName)
        val labAddress: TextView = itemView.findViewById(R.id.labAddress)
        val viewDetails: TextView = itemView.findViewById(R.id.viewDetails)
        val labAddressDetails: TextView = itemView.findViewById(R.id.labAddressDetails)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }
}

