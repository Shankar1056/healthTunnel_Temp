package com.healthtunnel.ui.profile

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbar)

        editTextEmail.setText(ClsGeneral.getPreferences(Constant.EMAIL))
        editTextName.setText(ClsGeneral.getPreferences(Constant.NAME))
        editTextMobile.setText(ClsGeneral.getPreferences(Constant.MOBILE))
        editTextGender.setText(ClsGeneral.getPreferences(Constant.GENDER))

        update.setOnClickListener {
            when {
                editTextName.text.isNullOrEmpty() -> {
                    Toast.makeText(
                        this@ProfileActivity,
                        resources.getString(R.string.required_name),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                editTextEmail.text.isNullOrEmpty() -> {
                    Toast.makeText(
                        this@ProfileActivity,
                        resources.getString(R.string.required_email),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                !Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text).matches() -> {
                    Toast.makeText(
                        this@ProfileActivity,
                        resources.getString(R.string.required_correct_name),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                editTextMobile.text.isNullOrEmpty() -> {
                    Toast.makeText(
                        this@ProfileActivity,
                        resources.getString(R.string.required_mobile),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    viewModel.updareProfile(
                        editTextEmail.text.toString(),
                        editTextName.text.toString(),
                        editTextMobile.text.toString(),
                        editTextGender.text.toString()
                    )
                }
            }
        }

        viewModel.message.observe(this, Observer {
            Toast.makeText(this@ProfileActivity, it, Toast.LENGTH_SHORT).show()
            finish()
        })

        toolbar.setNavigationOnClickListener {
            finish()
        }

        editTextGender.setOnClickListener {
            if (update.isVisible)
                viewModel.showGenderDialog(this@ProfileActivity)
        }

        edit.setOnClickListener {
            editTextName.isFocusableInTouchMode = true
            editTextName.isFocusable = true
            editTextEmail.isFocusableInTouchMode = true
            editTextEmail.isFocusable = true
            editTextMobile.isFocusableInTouchMode = true
            editTextMobile.isFocusable = true
            editTextGender.isClickable = true
            update.visibility = View.VISIBLE
        }

        viewModel.selectedGender.observe(this, Observer {
            editTextGender.setText(it)
        })
    }
}