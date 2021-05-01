package com.healthtunnel.ui.blood

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.healthtunnel.R
import com.healthtunnel.databinding.ActivityBloodRequestBinding
import com.healthtunnel.ui.community_center.CommunityCenteractivity
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.ImageQuality
import kotlinx.android.synthetic.main.activity_blood_request.*
import kotlinx.android.synthetic.main.activity_blood_request.fullScreenImage
import kotlinx.android.synthetic.main.activity_blood_request.full_screen_dialog
import kotlinx.android.synthetic.main.activity_blood_request.termsConditionCB
import kotlinx.android.synthetic.main.activity_blood_request.toolbar

class BloodRequestAcrivity : AppCompatActivity() {

    val viewModel: BloodViewModel by viewModels()
    private lateinit var options: Options
    private var returnValue = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityBloodRequestBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_blood_request)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(toolbar)

        viewModel.setContext(this@BloodRequestAcrivity)

        clickListener()
        //showFullScreenImage()

        termsConditionCB.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.termsCondition(
                isChecked
            )
        }

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.successMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            if (intent.getStringExtra("from").equals("home")) {
                startActivity(Intent(this@BloodRequestAcrivity,
                    CommunityCenteractivity::class.java))
            }
            finish()

        })

        toolbar.setNavigationOnClickListener {
            finish()
        }

        uploadAttechment.setOnClickListener {
            Pix.start(this@BloodRequestAcrivity, options)
        }

        initPixOptions()

    }

    private fun initPixOptions() {
        options = Options.init()
            .setRequestCode(100)
            .setCount(1)
            .setFrontfacing(false)
            .setImageQuality(ImageQuality.REGULAR)
            .setPreSelectedUrls(returnValue)
            .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
            .setPath("/akshay/new")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            100 -> {
                if (resultCode == Activity.RESULT_OK) {
                    returnValue = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                    Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show()

                }
            }


        }
    }
    private fun clickListener() {
        full_screen_dialog.setOnClickListener {
            full_screen_dialog.visibility = View.GONE
            appBar.visibility = View.VISIBLE
        }
    }

    private fun showFullScreenImage() {
        val explanatoryImage = intent.getStringExtra("explanatory_image")
        if (!explanatoryImage.isNullOrEmpty()) {
            Glide.with(this).load(explanatoryImage).into(fullScreenImage)
            appBar.visibility = View.GONE
            full_screen_dialog.visibility = View.VISIBLE
        } else {
            full_screen_dialog.visibility = View.GONE
            appBar.visibility = View.VISIBLE
        }
    }
}