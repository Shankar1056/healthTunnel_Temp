package com.healthtunnel.ui.ecom

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.data.model.CartDetailsResult
import com.healthtunnel.databinding.ActivityMyCartBinding
import com.healthtunnel.ui.address.AddressListActivity
import com.healthtunnel.ui.ecom.adapter.CartDetailsAdapter
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant
import com.healthtunnel.ui.utility.Utility
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_my_cart.*
import java.util.*

class MyCartActivity : AppCompatActivity() {

    val viewModel: EcomViewModel by viewModels()
    private var totalItemAmount = 0
    private var totalItemSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMyCartBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_my_cart)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(toolbar)

        viewModel.getCartDetails()

        viewModel.cartDetailsResponse.observe(this, Observer {
            totalItemAmount = 0
            totalItemSize = it.size
            if (it.size > 0) {
                setData(it)
                businessRV.adapter = CartDetailsAdapter(it, object :
                    CartDetailsAdapter.OnItemClickListener {
                    override fun onPlus(pos: Int) {
                        viewModel.addTocard(it[pos].businessId.id,
                            it[pos].productId._id,
                            it[pos].planId._id,
                            (it[pos].quantity + 1))
                    }

                    override fun onMinus(pos: Int) {
                        if (it[pos].quantity == 1) {
                            viewModel.deleteCard(it[pos].productId._id,
                                it[pos].quantity,
                                totalItemSize)
                        } else {
                            viewModel.addTocard(it[pos].businessId.id,
                                it[pos].productId._id,
                                it[pos].planId._id,
                                (it[pos].quantity - 1))
                        }
                    }

                })

                calculatePrice(it)
            } else {
                mainCL.visibility = View.GONE
                noItemInCartText.visibility = View.VISIBLE
            }
        })

        viewModel.message.observe(this, Observer {
            if (it.equals("Added successfully")) {
                viewModel.getCartDetails()
            }
            Toast.makeText(this@MyCartActivity, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.successMessage.observe(this, Observer {
            Toast.makeText(this@MyCartActivity, "Deleted", Toast.LENGTH_SHORT).show()
            viewModel.getCartDetails()
        })

        toolbar.setNavigationOnClickListener {
            finish()
        }

        cartContinue.setOnClickListener {
            ClsGeneral.setPreferences("total", totalItemAmount.toString())
            startActivity(Intent(this@MyCartActivity, AddressListActivity::class.java))
        }

    }

    private fun calculatePrice(it: ArrayList<CartDetailsResult>?) {
        if (it?.size!! > 0) {
            for (i in 0 until it.size)

                totalItemAmount += Utility.getAmount(it[i].quantity, it[i].planId.price)
        }

        totalAmount.text = "Rs. $totalItemAmount"
        toPay.text = "Rs. $totalItemAmount"

    }

    private fun setData(it: ArrayList<CartDetailsResult>?) {

        Glide.with(this).load(it?.get(0)?.businessId?.profileImage).into(image)
        name.text = it?.get(0)?.businessId?.businessName
        distance.text = ClsGeneral.getPreferences(Constant.DISTANCE_IN_KM) + " Km"
        location.text = ClsGeneral.getPreferences(Constant.LOCATION_WITH_ADDRESS)
    }
}