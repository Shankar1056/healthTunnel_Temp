package com.healthtunnel.ui.coupon

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.ui.staticform.StaticFormViewModel
import kotlinx.android.synthetic.main.activity_coupon.*


class CouponListActivity : AppCompatActivity() {

    val viewModel: StaticFormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)


        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        viewModel.getCouponCode(intent.getStringExtra("id"))

        viewModel.couponResultResponse.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                emptyOffer.visibility = View.VISIBLE
            } else {
                emptyOffer.visibility = View.GONE
                couponRV.adapter = CouponAdapter(it, object : CouponAdapter.OnItemClickListener {
                    override fun onClick(pos: Int) {
                        val clipboard: ClipboardManager =
                            getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        val clip: ClipData = ClipData.newPlainText("label", it[pos].couponCode)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(this@CouponListActivity, "Coupon copied", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }

                })
            }
        })


        viewModel.showProgressDialog.observe(this, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })

    }
}