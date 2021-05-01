package com.healthtunnel.ui.address

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.data.model.AddressResult
import com.healthtunnel.databinding.ActivityAddAddressBinding
import kotlinx.android.synthetic.main.activity_add_address.*

class AddAddressActivity : AppCompatActivity() {

    val viewModel: AddressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityAddAddressBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_add_address)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(toolbar)

        try {
            val data = intent.getParcelableExtra<AddressResult>("data")
            if (data != null) {
                viewModel.operation = "update"
                viewModel.addressId = data.id
                setData(data)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        viewModel.successMessage.observe(this, Observer {
            if (it == "Created successfully") {
                startActivity(Intent(this, AddressListActivity::class.java))
            } else if (it == "Updated successfully") {
                startActivity(Intent(this, AddressListActivity::class.java))
            }
        })

        viewModel.message.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        toolbar.setNavigationOnClickListener {
            finish()
        }

    }

    private fun setData(data: AddressResult?) {
        tvFullName.setText(data?.flat)
        tvAddress1.setText(data?.address)
        tvState.setText(data?.state)
        tvCity.setText(data?.city)
        tvPinCode.setText(data?.pincode)

        viewModel.model.flat = data?.flat
        viewModel.model.address = data?.address
        viewModel.model.state = data?.state
        viewModel.model.city = data?.city
        viewModel.model.pincode = data?.pincode
    }
}