package com.healthtunnel.ui.community_center

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.HorizontalScrollView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.healthtunnel.R
import com.healthtunnel.databinding.ActivityComunityCenterBinding
import com.healthtunnel.ui.blood.BloodRequestAcrivity
import com.healthtunnel.ui.community_center.fragment.CommunityFragment
import kotlinx.android.synthetic.main.activity_blood_donation.*
import kotlinx.android.synthetic.main.activity_comunity_center.*
import kotlinx.android.synthetic.main.activity_comunity_center.appBar
import kotlinx.android.synthetic.main.activity_comunity_center.fullScreenImage
import kotlinx.android.synthetic.main.activity_comunity_center.full_screen_dialog
import kotlinx.android.synthetic.main.activity_comunity_center.toolbar

class CommunityCenteractivity : AppCompatActivity() {

    val viewModel: CommunityViewModel by viewModels()
    private var clickedPosition : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityComunityCenterBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_comunity_center)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        setSupportActionBar(toolbar)

        switchFragment(1)
        viewModel.clickedPosition.observe(this, Observer {
            clickedPosition = it
            when (it) {
                1 -> {
                    switchFragment(1)
                    hsv.fullScroll(HorizontalScrollView.FOCUS_LEFT)
                }
                2 -> switchFragment(2)

                3 -> {
                    switchFragment(3)
                    hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
                }
            }
        })

        toolbar.setNavigationOnClickListener {
            finish()
        }

        addNewReq.setOnClickListener {
            startActivity(Intent(this@CommunityCenteractivity, BloodRequestAcrivity::class.java)
                .putExtra("from", "comm"))
        }

        clickListener()
       // showFullScreenImage()
    }

    private fun switchFragment(position: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, CommunityFragment(position)).commit()
    }

    override fun onResume() {
        super.onResume()
        viewModel.clickedPosition.value = clickedPosition
        viewModel.CommunityRequest(clickedPosition)
    }
    private fun clickListener() {
        full_screen_dialog.setOnClickListener {
            full_screen_dialog.visibility = View.GONE
            appBar.visibility = View.VISIBLE
        }
    }

    private fun showFullScreenImage() {
        val explanatoryImage = intent.getStringExtra("explanatory_image")
        if (!explanatoryImage.isNullOrEmpty()) {
            Glide.with(this).load(explanatoryImage).into(fullScreenImage)
            full_screen_dialog.visibility = View.VISIBLE
            appBar.visibility = View.GONE
        } else {
            full_screen_dialog.visibility = View.GONE
            appBar.visibility = View.VISIBLE
        }
    }
}