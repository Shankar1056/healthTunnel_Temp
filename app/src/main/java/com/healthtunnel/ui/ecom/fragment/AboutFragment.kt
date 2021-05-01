package com.healthtunnel.ui.ecom.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.data.model.BusinessAboutDescImages
import com.healthtunnel.databinding.FragmentAboutBinding
import com.healthtunnel.ui.auth.adapter.IntroductionAdapter
import com.healthtunnel.ui.ecom.EcomViewModel
import com.healthtunnel.ui.ecom.adapter.AboutViewPagerAdapter
import com.healthtunnel.ui.ecom.adapter.BusinessCategoryAdapter
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_about.dotsIndicator
import kotlinx.android.synthetic.main.fragment_about.imageViewPager

class AboutFragment : Fragment() {

    val viewModel: EcomViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAboutBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.businessAboutResponse.observe(viewLifecycleOwner, Observer {
            location.text =
                it[0].address.address + "," + it[0].address.address2 + "," + it[0].address.city + "," + it[0].address.state + "," + it[0].address.country + "," + it[0].address.pincode

            about.text = it[0].description
            ClsGeneral.setPreferences(Constant.LOCATION_WITH_ADDRESS, location.text.toString())

            categoryCV.adapter = BusinessCategoryAdapter(it[0].businessCategories,
                object : BusinessCategoryAdapter.OnItemClickListener {
                    override fun onClick(pos: Int) {

                    }

                })

            setImageToViewPager(it[0].descImages)

        })


    }

    private fun setImageToViewPager(descImages: ArrayList<BusinessAboutDescImages>) {
        imageViewPager.adapter = AboutViewPagerAdapter(activity, descImages)
        dotsIndicator.setViewPager(imageViewPager)
        imageViewPager.adapter?.registerDataSetObserver(dotsIndicator.dataSetObserver)
    }
}