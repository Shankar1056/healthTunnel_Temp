package com.healthtunnel.ui.wellnesscorner

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.healthtunnel.R
import com.healthtunnel.data.model.WellnessResult
import com.healthtunnel.databinding.ActivityServiceCategoryBinding
import com.healthtunnel.ui.caterorywithtab.CategoryListingViewModel
import com.healthtunnel.ui.caterorywithtab.adapter.ServicesViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_service_category.*

class WellnessCornerActivity : AppCompatActivity() {

    private var viewPagerAdapter: ServicesViewPagerAdapter? = null
    private lateinit var viewpager: ViewPager
    val viewModel: CategoryListingViewModel by viewModels()
    private var wellnessResult = ArrayList<WellnessResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityServiceCategoryBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_service_category)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        setSupportActionBar(toolbar)

        viewpager = findViewById(R.id.viewpager)

        val pos = intent.getIntExtra("pos", 0)
        try {
            wellnessResult = intent.getParcelableArrayListExtra<WellnessResult>("data")
        } catch (e : Exception){
            e.printStackTrace()
        }




        textLocation.visibility = View.GONE
        viewModel.wellnessTitle.observe(this, Observer {
            location.text = it
        })
        viewModel.getWellnessArticle()
        viewModel.wellnessCornerResponse.observe(this, Observer {

            if (it.size > 3) {
                tabs.tabMode = TabLayout.MODE_SCROLLABLE
            } else {
                tabs.tabMode = TabLayout.MODE_FIXED
            }
            setupViewPager(it)
            managePageChange()
        })

        setViewPagerPositionAndTitle(pos)

        toolbar.setNavigationOnClickListener {
            finish()
        }

    }

    private fun setViewPagerPositionAndTitle(pos: Int) {
        when (pos) {
            0 -> {
                viewModel.wellnessTitle.value = wellnessResult[pos].name
                viewpager.setCurrentItem(pos)
            }
            1 -> {
                viewModel.wellnessTitle.value = wellnessResult[pos].name
                viewpager.setCurrentItem(pos)
            }
            2 -> {
                viewModel.wellnessTitle.value = wellnessResult[pos].name
                viewpager.setCurrentItem(pos)
            }
        }

    }

    private fun setupViewPager(result: java.util.ArrayList<WellnessResult>) {
        if (result.size == 1) {
            appbar.visibility = View.GONE
        }
        tabs.setupWithViewPager(viewpager)
        viewpager.offscreenPageLimit = 0
        viewPagerAdapter =
            ServicesViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter!!.initWellnessTabsAndPages(result)
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
                    onPageSelected(0)
                    first = false;
                }
            }

            override fun onPageSelected(position: Int) {
                setViewPagerPositionAndTitle(position)
                val mCurrentFragment: WellnessListFragment =
                    viewPagerAdapter?.getItem(position) as WellnessListFragment
                mCurrentFragment.getServiceList(viewPagerAdapter!!.getId(position))
            }

        })
    }
}