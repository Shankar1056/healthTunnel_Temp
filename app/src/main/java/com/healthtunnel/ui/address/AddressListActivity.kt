package com.healthtunnel.ui.address

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.ui.address.adapter.AddressListAdapter
import kotlinx.android.synthetic.main.activity_address_list.*

class AddressListActivity : AppCompatActivity() {

    private val viewModel: AddressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list)

        setSupportActionBar(toolbar)


        floating_action_button.setOnClickListener {
            startActivity(Intent(this@AddressListActivity, AddAddressActivity::class.java))
        }
        no_list.setOnClickListener {
            startActivity(Intent(this@AddressListActivity, AddAddressActivity::class.java))
        }

        viewModel.addressResult.observe(this, Observer {
            if (it.size == 0) {
                no_list.visibility = View.VISIBLE
            } else {
                no_list.visibility = View.GONE
                addressListRv.adapter = AddressListAdapter(it, object :
                    AddressListAdapter.OnItemClickListener {
                    override fun onClick(pos: Int) {
                        startActivity(Intent(this@AddressListActivity, PaymentSummary::class.java)
                            .putExtra("address", it[pos]))
                    }

                })
            }
        })

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllAddress()
    }
}