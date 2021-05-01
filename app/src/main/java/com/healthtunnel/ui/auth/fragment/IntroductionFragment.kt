package com.healthtunnel.ui.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.healthtunnel.data.model.InteroductionModel
import com.healthtunnel.R
import com.healthtunnel.databinding.FragmentIntroductionBinding
import com.healthtunnel.ui.auth.AuthViewModel
import com.healthtunnel.ui.auth.adapter.IntroductionAdapter
import kotlinx.android.synthetic.main.fragment_introduction.*
import kotlin.collections.ArrayList


class IntroductionFragment : Fragment() {
    val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentIntroductionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_introduction, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imagesList: ArrayList<InteroductionModel> = ArrayList()
        imagesList.add(
            InteroductionModel(
                R.mipmap.intro_1,
                resources.getString(R.string.title_intro_1),
                resources.getString(R.string.desc_intro_1)
            )
        )
        imagesList.add(
            InteroductionModel(
                R.mipmap.intro_2,
                resources.getString(R.string.title_intro_2),
                resources.getString(R.string.desc_intro_2)
            )
        )
        imagesList.add(
            InteroductionModel(
                R.mipmap.intro_3,
                resources.getString(R.string.title_intro_3),
                resources.getString(R.string.desc_intro_3)
            )
        )
        imagesList.add(
            InteroductionModel(
                R.mipmap.intro_4,
                resources.getString(R.string.title_intro_4),
                resources.getString(R.string.desc_intro_4)
            )
        )

        imageViewPager.adapter = IntroductionAdapter(activity, imagesList)
        dotsIndicator.setViewPager(imageViewPager)
        imageViewPager.adapter?.registerDataSetObserver(dotsIndicator.dataSetObserver)


    }
}
