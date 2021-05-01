package com.healthtunnel.ui.ecom

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.data.model.usinessShopAssociatePlan
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_business_shop.*

class BusinessShopDetails : AppCompatActivity() {
    private var associatedPlanList = ArrayList<usinessShopAssociatePlan>()
    private var productId = ""
    private var planId = ""
    private var businessId = ""
    private var productPrice = 0
    private var qty = 0
    private var qtyPlanId = ""

    val viewModel: EcomViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_shop)

        setSupportActionBar(toolbar)


        viewModel.businessShopDetailsResponse.observe(this, Observer {
            titleText.text = it.name
            usageCharges.text = "Usage Charges : Rs.${it.plans[0].price} / ${it.plans[0].name}"
            summary.text = it.description
            moreInfo.text = it.moreInfo
            Glide.with(this).load(it.productImage).into(image)
            productId = it._id
            planId = it.plans[0]._id
            businessId = it.businessId
            productPrice = it.plans[0].price
            qty = it.cartQuantity.quantity
            qtyPlanId = it.cartQuantity.planId

            associatedPlanList.addAll(it.associatedPlans)
            setQuantity(qty)
        })

        viewModel.selectedValue.observe(this, Observer {
            usageCharges.text = "Usage Charges : $it"


            for (data in associatedPlanList) {

                if ((data.price + "/" + data.name) == it) {
                    if (data._id == qtyPlanId) {
                        setQuantity(qty)
                    } else {
                        setQuantity(0)
                    }
                    planId = data._id
                }
            }


        })


        textLocation.text = intent.getStringExtra("title")

        addToCart.setOnClickListener {
            if (addToCart.text == resources.getString(R.string.title_add_cart)) {
                qty = 1
                viewModel.addTocard(businessId, productId, planId, qty)
            } else {
                startActivity(Intent(this@BusinessShopDetails, MyCartActivity::class.java))
            }
        }

        minus.setOnClickListener {
            if (qty == 1) {
                viewModel.deleteCard(productId, qty, 1)
            } else {
                qty -= 1
                viewModel.addTocard(businessId, productId, planId, qty)
            }

        }

        plus.setOnClickListener {
            qty += 1
            viewModel.addTocard(businessId, productId, planId, qty)
        }

        viewModel.message.observe(this, Observer {
            if (it.equals("Added successfully")) {
                qtyPlanId = planId
                quantity.text = qty.toString()
                addToCart.visibility = View.INVISIBLE
                topCL.visibility = View.VISIBLE
            }
            Toast.makeText(this@BusinessShopDetails, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.noItemInCart.observe(this, Observer {
            setQuantity(0)
        })

        changePlan.setOnClickListener {
            viewModel.showAssociatedPlan(this@BusinessShopDetails, associatedPlanList)
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }

        cart.setOnClickListener {
            startActivity(Intent(this@BusinessShopDetails, MyCartActivity::class.java))
        }
    }

    private fun setQuantity(qnty: Int) {
        if (qnty > 0) {
            addToCart.visibility = View.INVISIBLE
            topCL.visibility = View.VISIBLE
            quantity.text = qnty.toString()
        } else {
            addToCart.visibility = View.VISIBLE
            topCL.visibility = View.INVISIBLE
            quantity.text = qnty.toString()
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getParticularBusinessProduct(intent.getStringExtra("id"))
    }
}