package com.healthtunnel.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.ui.home.HomeViewModel
import com.healthtunnel.ui.home.adapter.PopularSearchAdapter
import com.healthtunnel.ui.webview.WebViewActivity
import kotlinx.android.synthetic.main.activity_service_category.*
import kotlinx.android.synthetic.main.activityhome_category.*
import kotlinx.android.synthetic.main.activityhome_category.toolbar

class HomeCategoryListActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityhome_category)
        setSupportActionBar(toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = intent.getStringExtra("title")

        viewModel.getBanners(0)

        viewModel.message.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.featureProductResponse.observe(this, Observer {
            if (it.size == 0) {
                pop_search_img.visibility = View.VISIBLE
                return@Observer
            }
            popularCatRv.adapter = PopularSearchAdapter(it, this@HomeCategoryListActivity, object :
                PopularSearchAdapter.OnItemClickListener {
                override fun onClick(pos: Int) {
                    startActivity(Intent(this@HomeCategoryListActivity,
                        WebViewActivity::class.java).putExtra("title",
                        resources.getString(R.string.title_popular_serchces))
                        .putExtra("url", it[pos].hyperLink)
                        .putExtra("explanatory_image", "")
                    )
                }

                override fun onCallClick(pos: Int) {
                }

            })
        })
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}