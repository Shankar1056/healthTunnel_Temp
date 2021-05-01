package com.healthtunnel.ui.caterorywithtab.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.healthtunnel.R
import com.healthtunnel.databinding.FragmentServiceListBinding
import com.healthtunnel.ui.caterorywithtab.CategoryListingViewModel
import com.healthtunnel.ui.caterorywithtab.adapter.HeaderImageAdapter
import com.healthtunnel.ui.caterorywithtab.adapter.ServiceSubCatAdapter
import com.healthtunnel.ui.utility.Constant
import com.healthtunnel.ui.webview.WebViewActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_service_list.*
import kotlinx.android.synthetic.main.fragment_service_list.featureViewPager
import java.util.*

class ServiceListFragment : Fragment() {

    private var currentPage = 0
    private val DELAY_MS: Long = 500
    private val PERIOD_MS: Long = 3000
    private lateinit var timer: Timer
    private var NUM_PAGES: Int = 0
    val viewModel: CategoryListingViewModel by activityViewModels()
    private var serviceName = ""
    private var switch = true
    private var offerId: String? = null
    private var explanatory_image : String = ""


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

        viewModel.subCatService.observe(viewLifecycleOwner, Observer {
            subCatRV.adapter = ServiceSubCatAdapter(it,
                activity,
                object : ServiceSubCatAdapter.OnItemClickListener {
                    override fun onClick(pos: Int) {

                        if (it[pos].apiKey.equals(Constant.FORM)) {
                            if (it[pos].serviceName.equals(resources.getString(R.string.medlife_labs))) {
                                /*startActivity(Intent(activity, FormTypeActivity::class.java)
                                    .putExtra("title", it[pos].serviceName)
                                    .putExtra("id", it[pos]._id)
                                )*/
                                serviceName = it[pos].serviceName
                                switch = true
                                offerId = it[pos]._id
                                viewModel.listParticularServiceAPI(it[pos]._id)

                            } else {
                                Toast.makeText(activity, "coming soon", Toast.LENGTH_SHORT).show()
                            }

                        } else if (it[pos].url.trim().isNotEmpty()) {
                            serviceName = it[pos].serviceName
                            switch = true
                            offerId = it[pos]._id
                            viewModel.listParticularServiceAPI(it[pos]._id)
                        }

                    }
                })
        })

        viewModel.websiteLink.observe(viewLifecycleOwner, Observer {
            if (switch) {
                switch = false
                Thread.sleep(200)
                startActivity(
                    Intent(activity, WebViewActivity::class.java).putExtra("url", it)
                        .putExtra("title", serviceName)
                        .putExtra("id", offerId)
                        .putExtra("explanatory_image", explanatory_image)
                )
            }

        })

        activity?.let {
            viewModel.header.observe(it, Observer {
                try {
                    header.text = it
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
        }

        activity?.let {
            viewModel.featureProductResponse.observe(it, Observer {
                try {
                    if (it != null && it.size > 0) {
                        NUM_PAGES = it.size
                        bannerImage.visibility = View.GONE
                        featureViewPager.visibility = View.VISIBLE
                        featureViewPager.adapter = HeaderImageAdapter(activity, it,
                            object : HeaderImageAdapter.onItemClicked {
                                override fun onClicked(pos: Int) {
                                    if (it[pos].hyperLink.isNullOrEmpty()) {
                                        Toast.makeText(
                                            activity,
                                            resources.getString(R.string.title_no_url_found),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        startActivity(
                                            Intent(
                                                activity,
                                                WebViewActivity::class.java
                                            ).putExtra(
                                                "url",
                                                it[pos].hyperLink
                                            )
                                                .putExtra("title", "Banner")
                                                .putExtra("id", it[pos].id)
                                                .putExtra("explanatory_image", explanatory_image)
                                        )
                                    }
                                }

                            })
                        enableAutoSlide()
                        dotsIndicator.setViewPager(featureViewPager)

                    } else {
                        bannerImage.visibility = View.VISIBLE
                        featureViewPager.visibility = View.INVISIBLE
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
        }

        val mSuppressInterceptListener: View.OnTouchListener =
            View.OnTouchListener { v, event ->
                if (event.getAction() === MotionEvent.ACTION_DOWN &&
                    v is ViewGroup
                ) {
                    v.requestDisallowInterceptTouchEvent(true)
                }
                false
            }

        full_screen_dialog.setOnClickListener {
            full_screen_dialog.visibility = View.GONE
        }
    }

    private fun enableAutoSlide() {
        var handler = Handler()
        val update = Runnable {
            if (currentPage === NUM_PAGES - 1) {
                currentPage = 0
            }
            try {
                featureViewPager.setCurrentItem(currentPage++, true)
            } catch (e: NullPointerException) {
            }
        }


        timer = Timer() // This will create a new Thread

        timer.schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(update)
            }
        }, DELAY_MS, PERIOD_MS)
    }

    fun getServiceList(id: String, header: String, parentId: String, explanatory_image: String) {
        viewModel.header.value = header
       // this.explanatory_image = explanatory_image
        try {
            viewModel.getBanners(id, parentId)
            viewModel.getSubCat(id, parentId)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

       //displayFullScreenImage(explanatory_image)

    }

    private fun displayFullScreenImage(explanatory_image: String) {
        val explanatoryImage = explanatory_image
        if (!explanatoryImage.isNullOrEmpty()) {
            activity?.let { Glide.with(it).load(explanatoryImage).into(fullScreenImage) }
            full_screen_dialog.visibility = View.VISIBLE
        } else {
            full_screen_dialog.visibility = View.GONE
        }
    }
}