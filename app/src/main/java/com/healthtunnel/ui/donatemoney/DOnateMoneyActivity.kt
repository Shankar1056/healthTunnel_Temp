package com.healthtunnel.ui.donatemoney

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.healthtunnel.R
import com.healthtunnel.data.model.DonateCancerMoney
import com.healthtunnel.ui.donatemoney.adapter.DonateCancerAdapter
import com.healthtunnel.ui.donatemoney.adapter.DonateMedicineAdapter
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_donate_money.*
import kotlinx.android.synthetic.main.fragment_introduction.dotsIndicator
import kotlinx.android.synthetic.main.fragment_introduction.imageViewPager
import org.json.JSONObject

class DOnateMoneyActivity : AppCompatActivity(), PaymentResultListener {
    val TAG: String = DOnateMoneyActivity::class.toString()
    private var selectedAmount = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_money)

        Checkout.preload(applicationContext)
        val imagesList: ArrayList<Int> = ArrayList()

        imagesList.add(R.mipmap.patient)
        imagesList.add(R.mipmap.patient)
        imagesList.add(R.mipmap.patient)

        imageViewPager.adapter = DonateMoneyPagerAdapter(this, imagesList)
        dotsIndicator.setViewPager(imageViewPager)
        imageViewPager.adapter?.registerDataSetObserver(dotsIndicator.dataSetObserver)

        payNow_medicine.setOnClickListener {
            startPayment(getString(R.string.title_donate_for_medicine))
        }

        payNow.setOnClickListener {
            startPayment(getString(R.string.title_donate_for_credit))
        }


        donateCV.adapter = DonateCancerAdapter(getAmountAndDetails(), object :
            DonateCancerAdapter.OnItemClickListener {
            override fun onClick(pos: Int) {
                selectedAmount = getAmountAndDetails()[pos].amount.toString().substring(3)
                Toast.makeText(this@DOnateMoneyActivity, selectedAmount, Toast.LENGTH_SHORT).show()

            }

        })


        donateMedicine.adapter = DonateMedicineAdapter(getAmountAndDetailsMedicine(), object :
            DonateMedicineAdapter.OnItemClickListener {
            override fun onClick(pos: Int) {
                selectedAmount = getAmountAndDetailsMedicine()[pos].amount.toString().substring(3)
                Toast.makeText(this@DOnateMoneyActivity, selectedAmount, Toast.LENGTH_SHORT).show()


            }

        })

//        donateCV.adapter = donateCancerAdapter

    }

    private fun getAmountAndDetails(): java.util.ArrayList<DonateCancerMoney> {
        val modelList = ArrayList<DonateCancerMoney>()

        val model = DonateCancerMoney()
        val model1 = DonateCancerMoney()
        val model2 = DonateCancerMoney()
        val model3 = DonateCancerMoney()

        model.amount = getString(R.string.title_700)
        model.details = getString(R.string.title_700_details)
        model.image = R.drawable.ic_card_giftcard
        modelList.add(model)

        model1.amount = getString(R.string.title_1500)
        model1.details = getString(R.string.title_1500_details)
        model1.image = R.drawable.ic_card_giftcard
        modelList.add(model1)

        model2.amount = getString(R.string.title_300)
        model2.details = getString(R.string.title_3000_details)
        model2.image = R.drawable.ic_card_giftcard
        modelList.add(model2)

        model3.amount = getString(R.string.title_7000)
        model3.details = getString(R.string.title_7000_details)
        model3.image = R.drawable.ic_card_giftcard
        modelList.add(model3)

        return modelList
    }

    private fun getAmountAndDetailsMedicine(): java.util.ArrayList<DonateCancerMoney> {
        val modelList = ArrayList<DonateCancerMoney>()

        val model = DonateCancerMoney()
        val model1 = DonateCancerMoney()
        val model2 = DonateCancerMoney()
        val model3 = DonateCancerMoney()

        model.amount = getString(R.string.title_700_medicine)
        model.details = getString(R.string.title_700_medicine_details)
        model.image = R.drawable.ic_card_giftcard
        modelList.add(model)

        model1.amount = getString(R.string.title_1500_medicine)
        model1.details = getString(R.string.title_1500_medicine_details)
        model1.image = R.drawable.ic_card_giftcard
        modelList.add(model1)

        model2.amount = getString(R.string.title_3000_medicine)
        model2.details = getString(R.string.title_3000_medicine_details)
        model2.image = R.drawable.ic_card_giftcard
        modelList.add(model2)

        model3.amount = getString(R.string.title_7000_medicine)
        model3.details = getString(R.string.title_7000_medicine_details)
        model3.image = R.drawable.ic_card_giftcard
        modelList.add(model3)

        return modelList
    }

    private fun startPayment(description: String) {
        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name", "Health Tunnel")
            options.put("description", description)
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("currency", "INR")
            options.put("amount", (selectedAmount.toInt()*100).toString())

            val prefill = JSONObject()
            prefill.put("email", "")
            prefill.put("contact", "")

            options.put("prefill", prefill)
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        try {
            Toast.makeText(this, "Payment Successful $razorpayPaymentId", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e(TAG, "Exception in onPaymentSuccess", e)
        }
    }

    override fun onPaymentError(errorCode: Int, response: String?) {
        try {
            Toast.makeText(this, "Payment failed $errorCode \n $response", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e(TAG, "Exception in onPaymentSuccess", e)
        }
    }
}