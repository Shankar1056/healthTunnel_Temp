package com.healthtunnel.ui.wellnesscorner

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
import com.healthtunnel.databinding.FragmentServiceListBinding
import com.healthtunnel.ui.caterorywithtab.CategoryListingViewModel
import kotlinx.android.synthetic.main.fragment_service_list.*

class WellnessListFragment : Fragment() {
    val viewModel: CategoryListingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentServiceListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_service_list, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        full_screen_dialog.visibility = View.GONE

        viewModel.wellnessCornerListResponse.observe(viewLifecycleOwner, Observer {
            subCatRV.adapter = WellnessListAdapter(it,
                activity,
                object : WellnessListAdapter.OnItemClickListener {
                    override fun onClick(pos: Int) {
                        startActivity(Intent(activity, WellnessCornerDetailsActivity::class.java)
                            .putExtra("id", it[pos]._id))
                    }

                })
        })
    }

    fun getServiceList(id: String) {
        try {
            viewModel.getAllWellnessArticles(id)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

    }


}