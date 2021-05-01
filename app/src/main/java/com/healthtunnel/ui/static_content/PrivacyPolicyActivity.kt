package com.healthtunnel.ui.static_content

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.healthtunnel.R
import kotlinx.android.synthetic.main.activity_privacy_policy.*

class PrivacyPolicyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        setSupportActionBar(toolbar)

        text.text = intent.getStringExtra("title")

        if (intent.getStringExtra("title") == resources.getString(R.string.menu_terms_condition)) {
            staticData.text =
                HtmlCompat.fromHtml(resources.getString(R.string.terms_condition_details),
                    HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        if (intent.getStringExtra("title") == resources.getString(R.string.menu_privacy_policy)) {
            staticData.text =
                HtmlCompat.fromHtml(resources.getString(R.string.privacy_policy_details),
                    HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}