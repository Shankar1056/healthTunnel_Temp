package com.healthtunnel.ui.ecom.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.healthtunnel.R
import com.healthtunnel.ui.ecom.BusinessShopDetails
import com.healthtunnel.ui.ecom.EcomViewModel
import com.healthtunnel.ui.ecom.adapter.BusinessShopCatAdapter
import com.healthtunnel.ui.ecom.adapter.BusinessShopListAdapter
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopNowFragment(private val businessCategory: String?) : Fragment() {

    val viewModel: EcomViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catRV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        viewModel.shopCategoryResponse.observe(viewLifecycleOwner, Observer {
            catRV.adapter =
                BusinessShopCatAdapter(it, object : BusinessShopCatAdapter.OnItemClickListener {
                    override fun onClick(pos: Int) {
                        if (businessCategory != null) {
                            viewModel.getAllBusinessProducts(businessCategory, it[pos]._id)
                        }
                    }


                })
            if (businessCategory != null) {
                viewModel.getAllBusinessProducts(businessCategory, it[0]._id)
            }
        })

        viewModel.shopCategoryListResponse.observe(viewLifecycleOwner, Observer {
            listRV.adapter =
                BusinessShopListAdapter(it, object : BusinessShopListAdapter.onItemClicked {
                    override fun onClick(pos: Int) {

                        startActivity(Intent(activity,
                            BusinessShopDetails::class.java)
                            .putExtra("id", it[pos]._id)
                            .putExtra("title", it[pos].name))
                    }

                })
        })

    }

}