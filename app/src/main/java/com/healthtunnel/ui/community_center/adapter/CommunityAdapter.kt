package com.healthtunnel.ui.community_center.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.healthtunnel.BR
import com.healthtunnel.R
import com.healthtunnel.data.model.CommunityResult
import com.healthtunnel.databinding.CommonItemComunityBinding

class CommunityAdapter(
    private val list: ArrayList<CommunityResult>,
    private val pos : Int,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {


        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.common_item_comunity, parent, false
        )

        return ViewHolder(binding as CommonItemComunityBinding)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val imageModelList = list[position]
        holder.bind(imageModelList, listener)

        holder.itemView.setOnClickListener {
             listener.onCallClick(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var itemRowBinding: CommonItemComunityBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {

        fun bind(obj: Any, listener: OnItemClickListener) {
            itemRowBinding.setVariable(BR.model, obj)
            itemRowBinding.executePendingBindings()
            /* itemRowBinding.callIcon.setOnClickListener {
                 listener.onCallClick(position)
             }*/
            if (pos == 1) {
                itemRowBinding.icon.setBackgroundResource(R.drawable.ic_comunity_red)
            } else if (pos ==2){
                itemRowBinding.icon.setBackgroundResource(R.drawable.ic_comunity_yellow)
            }else if (pos == 3){
                itemRowBinding.icon.setBackgroundResource(R.drawable.ic_comunity_green)
            }
        }

    }

    interface OnItemClickListener {
        // fun onItemClicked(list : PgWiseTenantResponse)
        fun onCallClick(pos: Int)
    }

}
