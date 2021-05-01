package com.healthtunnel.ui.blood

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.healthtunnel.R
import com.healthtunnel.databinding.ActivityBloodDonationBinding
import kotlinx.android.synthetic.main.activity_blood_donation.*
import kotlinx.android.synthetic.main.activity_blood_donation.fullScreenImage
import kotlinx.android.synthetic.main.activity_blood_donation.full_screen_dialog
import kotlinx.android.synthetic.main.activity_blood_donation.toolbar

class BloodDonationActivity : AppCompatActivity() {
    val viewModel: BloodViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityBloodDonationBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_blood_donation)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(toolbar)
        viewModel.setContext(this@BloodDonationActivity)

        clickListener()
        //showFullScreenImage()

        termsConditionCB.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.termsCondition(
                isChecked
            )
        }

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.successMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            finish()
        })

        toolbar.setNavigationOnClickListener {
            finish()
        }

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
            appBar.visibility = View.GONE
            full_screen_dialog.visibility = View.VISIBLE
        } else {
            full_screen_dialog.visibility = View.GONE
            appBar.visibility = View.VISIBLE
        }
    }
}