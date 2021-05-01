package com.healthtunnel.ui.staticform

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.ui.coupon.CouponListActivity
import com.healthtunnel.ui.staticform.fragment.MedLifeFragment
import kotlinx.android.synthetic.main.activity_activity_available_search.toolbar
import kotlinx.android.synthetic.main.activity_form_type.*

class FormTypeActivity : AppCompatActivity() {

    val viewModel: StaticFormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_type)

        setSupportActionBar(toolbar)

        textLocation.text = intent.getStringExtra("title")
        offers.setOnClickListener {
            startActivity(Intent(this@FormTypeActivity, CouponListActivity::class.java).
            putExtra("id", intent.getStringExtra("id")))
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }

        goToMedLifeFragment(MedLifeFragment())

        viewModel.formMessage.observe(this, Observer {
            Toast.makeText(this@FormTypeActivity, it, Toast.LENGTH_SHORT).show()
            finish()
        })

    }

    private fun goToMedLifeFragment(medLifeFragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, medLifeFragment)
            .commit()
    }
}