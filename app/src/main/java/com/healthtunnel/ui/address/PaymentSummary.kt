package com.healthtunnel.ui.address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.healthtunnel.MainActivity
import com.healthtunnel.R
import com.healthtunnel.data.model.AddressResult
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_payment_summary.*
import org.json.JSONObject

class PaymentSummary : AppCompatActivity(), PaymentResultListener {
    private var amount: Int = 0
    val TAG = "Payment Summary"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_summary)

        setSupportActionBar(toolbar)

        val data = intent.getParcelableExtra<AddressResult>("address")
        deliveryAddress.text =
            "${data.flat}, ${data.address}, ${data.address}, ${data.city}, ${data.pincode}"

        toPay.text = ClsGeneral.getPreferences("total")
        totalAmount.text = ClsGeneral.getPreferences("total")
        amount = ClsGeneral.getPreferences("total").toInt()

        toolbar.setNavigationOnClickListener {
            finish()
        }

        edit.setOnClickListener {
            startActivity(Intent(this@PaymentSummary, AddAddressActivity::class.java)
                .putExtra("data", data))
        }
        paymentButton.setOnClickListener {
            startPayment("Payment")
        }
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
            options.put("amount", (amount * 100).toString())

            val prefill = JSONObject()
            prefill.put("email", ClsGeneral.getPreferences(Constant.EMAIL))
            prefill.put("contact", ClsGeneral.getPreferences(Constant.MOBILE))

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
            startActivity(Intent(this@PaymentSummary, MainActivity::class.java))
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