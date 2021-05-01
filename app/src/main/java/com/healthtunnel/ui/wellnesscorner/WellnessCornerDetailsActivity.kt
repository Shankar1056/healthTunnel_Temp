package com.healthtunnel.ui.wellnesscorner

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.ui.caterorywithtab.CategoryListingViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_wellness_corner_details.*

class WellnessCornerDetailsActivity : AppCompatActivity() {

    val viewModel: CategoryListingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wellness_corner_details)
        setSupportActionBar(toolbar)

        viewModel.getParticularWellnessArticle(intent.getStringExtra("id"))

        viewModel.wellnessArticleResponse.observe(this, Observer {
            heading.text = it.articleSubject
//            subject.text = it.articleSubject
            date.text = it.createdAt
            description.text =
                HtmlCompat.fromHtml(it.description, HtmlCompat.FROM_HTML_MODE_LEGACY);
            Glide.with(this).load(it.articleLogo).into(imageView)
        })

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}