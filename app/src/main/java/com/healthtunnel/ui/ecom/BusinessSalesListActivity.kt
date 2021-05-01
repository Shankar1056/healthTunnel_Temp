package com.healthtunnel.ui.ecom

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.databinding.ActivityBusinessSalesBinding
import com.healthtunnel.ui.caterorywithtab.adapter.BusinessSalesAdapter
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_business_sales.*
import kotlinx.android.synthetic.main.activity_business_sales.fullScreenImage
import kotlinx.android.synthetic.main.activity_business_sales.full_screen_dialog
import kotlinx.android.synthetic.main.activity_business_sales.location
import kotlinx.android.synthetic.main.activity_business_sales.toolbar
import kotlinx.android.synthetic.main.activity_service_category.*

class BusinessSalesListActivity : AppCompatActivity() {

    val viewModel: EcomViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityBusinessSalesBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_business_sales)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(toolbar)
        location.text = ClsGeneral.getPreferences(Constant.LOCATION)
        viewModel.getBusinessSalesList(intent.getStringExtra("id"), "")

        viewModel.businessSalesresponse.observe(this, Observer {
            businessRV.adapter =
                BusinessSalesAdapter(it, object : BusinessSalesAdapter.onItemClicked {
                    override fun onClick(pos: Int) {
                        startActivity(Intent(this@BusinessSalesListActivity,
                            BusinessListDetailsActivity::class.java)
                            .putExtra("businessId", intent.getStringExtra("id"))
                            .putExtra("businessCategory", it[pos].id)
                            .putExtra("title", it[pos].businessName)
                            .putExtra("distance", it[pos].distance)
                        )
                    }

                })

            //setDialogScreen()
        })

        filter.setOnClickListener {
            startActivityForResult(Intent(this@BusinessSalesListActivity,
                FilterActivity::class.java).putExtra("id", intent.getStringExtra("id")), 2)
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }

        full_screen_dialog.setOnClickListener {
            full_screen_dialog.visibility = View.GONE
            topbar.visibility = View.VISIBLE
        }
    }

    private fun setDialogScreen() {
        val explanatoryImage = intent.getStringExtra("explanatory_image")
        if (!explanatoryImage.isNullOrEmpty()) {
            Glide.with(this).load(explanatoryImage).into(fullScreenImage)
            topbar.visibility = View.GONE
            full_screen_dialog.visibility = View.VISIBLE
        } else {
            full_screen_dialog.visibility = View.GONE
            topbar.visibility = View.VISIBLE
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            val prodId: String? = data?.getStringExtra("prodId")
            val catId: ArrayList<String>? = data?.getStringArrayListExtra("list")

            if (catId != null) {
                viewModel.filterProd.value = Array(catId.size) { i -> catId[i] }
            }
                viewModel.getBusinessSalesList(intent.getStringExtra("id"), prodId)

        }
    }

}