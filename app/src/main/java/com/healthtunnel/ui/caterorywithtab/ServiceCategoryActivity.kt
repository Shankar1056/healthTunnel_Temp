package com.healthtunnel.ui.caterorywithtab

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.healthtunnel.R
import com.healthtunnel.data.model.CatResult
import com.healthtunnel.ui.caterorywithtab.adapter.ServicesViewPagerAdapter
import com.healthtunnel.ui.caterorywithtab.fragment.ServiceListFragment
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_service_category.*
import kotlinx.android.synthetic.main.fragment_categorylisting.tabs

class ServiceCategoryActivity : AppCompatActivity() {

    private val viewModel: CategoryListingViewModel by viewModels()
    private var viewPagerAdapter: ServicesViewPagerAdapter? = null
    private lateinit var viewpager: ViewPager
    private var totalSize: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_category)
        setSupportActionBar(toolbar)
        location.text = ClsGeneral.getPreferences(Constant.LOCATION)


        viewpager = findViewById(R.id.viewpager)
        viewModel.getServiceList(intent.getStringExtra("parentId"))
        viewModel.homeCategoryResponse.observe(this, Observer {

            // displayFullScreenImage()
            setupViewPager(it)
            managePageChange()
        })

        toolbar.setNavigationOnClickListener {
            finish()
        }

        full_screen_dialog.setOnClickListener {
            full_screen_dialog.visibility = View.GONE
            appBar.visibility = View.VISIBLE
            if (totalSize == 1) {
                appbar.visibility = View.GONE
            } else {
                appbar.visibility = View.VISIBLE
            }
        }
    }

    private fun displayFullScreenImage() {
        val explanatoryImage = intent.getStringExtra("explanatory_image")
        if (!explanatoryImage.isNullOrEmpty()) {
            Glide.with(this).load(explanatoryImage).into(fullScreenImage)
            appBar.visibility = View.GONE
            appbar.visibility = View.GONE
            full_screen_dialog.visibility = View.VISIBLE
        } else {
            full_screen_dialog.visibility = View.GONE
            appBar.visibility = View.VISIBLE
            appbar.visibility = View.VISIBLE
        }
    }

    private fun setupViewPager(result: java.util.ArrayList<CatResult>) {
        totalSize = result.size
        if (totalSize == 1) {
            appbar.visibility = View.GONE
        }
        tabs.setupWithViewPager(viewpager)
        tabs.tabMode = TabLayout.MODE_SCROLLABLE
        viewpager.offscreenPageLimit = 0
        viewPagerAdapter =
            ServicesViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter!!.initTabsAndPages(result)
        viewpager.adapter = viewPagerAdapter
        tabs.setupWithViewPager(viewpager)

    }

    private fun managePageChange() {
        var first = true
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (first && positionOffset == 0f && positionOffsetPixels == 0) {
                    onPageSelected(0);
                    first = false;
                }
            }

            override fun onPageSelected(position: Int) {
                val mCurrentFragment: ServiceListFragment =
                    viewPagerAdapter?.getItem(position) as ServiceListFragment
                viewPagerAdapter?.getId(position)?.let {
                    mCurrentFragment.getServiceList(
                        it, viewPagerAdapter!!.getHeaders(position),
                        intent.getStringExtra("parentId"),
                        viewPagerAdapter?.getExplanatoryImage(position).toString()
                    )
                }
            }

        })
    }


}