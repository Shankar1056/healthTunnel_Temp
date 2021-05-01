package com.healthtunnel.ui.ecom

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.databinding.ActivityBusinessDetailsBinding
import com.healthtunnel.ui.ecom.fragment.AboutFragment
import com.healthtunnel.ui.ecom.fragment.ShopNowFragment
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant
import com.healthtunnel.ui.utility.Utility
import kotlinx.android.synthetic.main.activity_business_details.*

class BusinessListDetailsActivity : AppCompatActivity() {

    val viewModel: EcomViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityBusinessDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_business_details)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(toolbar)
        textLocation.text = intent.getStringExtra("title")

        viewModel.clickedPosition.observe(this, Observer {
            when (it) {
                1 -> {
                    viewModel.listParticularBusinessLead(intent.getStringExtra("businessCategory"))
                    val a = Utility.getDistanceInKM(intent.getDoubleExtra("distance", 0.0))
                    ClsGeneral.setPreferences(Constant.DISTANCE_IN_KM, a)
                    switchFragment(AboutFragment())
                    viewModel.distance.value = intent.getDoubleExtra("distance", 0.0)
                    viewModel.tite.value = intent.getStringExtra("title")
                }
                2 -> {
                    viewModel.getAllBusinessCategories(intent.getStringExtra("businessCategory"))
                    switchFragment(ShopNowFragment(intent.getStringExtra("businessCategory")))
                }
            }
        })


        toolbar.setNavigationOnClickListener {
            finish()
        }

        card.setOnClickListener {
            startActivity(Intent(this@BusinessListDetailsActivity, MyCartActivity::class.java))
        }
    }

    private fun switchFragment(aboutFragment: Fragment) {

        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, aboutFragment).commit()

    }
}