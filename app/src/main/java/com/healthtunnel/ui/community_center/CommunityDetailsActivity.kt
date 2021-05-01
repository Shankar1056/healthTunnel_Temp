package com.healthtunnel.ui.community_center

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.databinding.ActivityCommunityDetailsBinding
import kotlinx.android.synthetic.main.activity_community_details.*

class CommunityDetailsActivity : AppCompatActivity() {

    val viewModel: CommunityViewModel by viewModels()
    private var myMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCommunityDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_community_details)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(toolbar)

        viewModel.clickedPosition.value = intent.getIntExtra("position", 1)

        val id = intent.getStringExtra("id")
        viewModel.getCommunityDetails(id)

        viewModel.communityResponseModel.observe(this, Observer {
            binding.model = it
        })
        toolbar.setNavigationOnClickListener {
            finish()
        }

        closeRequest.setOnClickListener {
            viewModel.closeRequest(id)
        }

        viewModel.successMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.closeActivity.observe(this, Observer {
            finish()
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        menu?.findItem(R.id.call)?.isVisible = intent.getIntExtra("position", 0) == 1
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.call) {
            checkCallPermission()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    private fun checkCallPermission() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CALL_PHONE),
                1
            )
        } else {
            callPatient()
        }


    }

    private fun callPatient() {
        startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:${viewModel.patientMobileNuber}")))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(this@CommunityDetailsActivity,
                            Manifest.permission.CALL_PHONE) ===
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        callPatient()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

}