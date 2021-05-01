package com.healthtunnel.ui.caterorywithtab.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.BR
import com.healthtunnel.R
import com.healthtunnel.data.model.BusinessSalesResult
import com.healthtunnel.databinding.ItemBusinessSalesBinding

class BusinessSalesAdapter(
    private val list: ArrayList<BusinessSalesResult>,
    private val listener: onItemClicked
) :
    RecyclerView.Adapter<BusinessSalesAdapter.ViewHolder>() {
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {


        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_business_sales, parent, false
        )

        return ViewHolder(binding as ItemBusinessSalesBinding)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val imageModelList = list[position]
        holder.bind(imageModelList)

        holder.itemView.setOnClickListener {
            listener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var itemRowBinding: ItemBusinessSalesBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {

        fun bind(obj: Any) {
            itemRowBinding.setVariable(BR.model, obj)
            itemRowBinding.executePendingBindings()
        }
    }

    interface onItemClicked {
        fun onClick(pos: Int)
    }

}
