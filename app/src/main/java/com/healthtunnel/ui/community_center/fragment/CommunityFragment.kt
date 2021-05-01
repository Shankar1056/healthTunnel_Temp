package com.healthtunnel.ui.community_center.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.databinding.FragmentComunityBinding
import com.healthtunnel.ui.community_center.CommunityDetailsActivity
import com.healthtunnel.ui.community_center.CommunityViewModel
import com.healthtunnel.ui.community_center.adapter.CommunityAdapter
import kotlinx.android.synthetic.main.fragment_comunity.*

class CommunityFragment(val position: Int) : Fragment() {

    val viewModel: CommunityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentComunityBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_comunity, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.responseModel.observe(viewLifecycleOwner, Observer {
            communityRV.adapter =
                CommunityAdapter(it, position, object : CommunityAdapter.OnItemClickListener {
                    override fun onCallClick(pos: Int) {
                        startActivity(Intent(activity,
                            CommunityDetailsActivity::class.java).putExtra("id", it[pos]._id)
                            .putExtra("position", position))
                    }

                })
        })
    }
}