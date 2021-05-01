package com.healthtunnel.ui.webview

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.healthtunnel.R
import com.healthtunnel.ui.coupon.CouponListActivity
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        setSupportActionBar(toolbar)
        val t = intent.getStringExtra("title")
        if (t.isNullOrEmpty()) {
            finish()
        }
        textLocation.text = intent.getStringExtra("title")

        toolbar.setNavigationOnClickListener {
            finish()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webView.settings.safeBrowsingEnabled = true
        }
        webView.settings.javaScriptEnabled = true
        val webSettings: WebSettings = webView.settings
        webSettings.setJavaScriptEnabled(true)
        webSettings.setUseWideViewPort(true)
        webSettings.setLoadWithOverviewMode(true)
        webSettings.setDomStorageEnabled(true)
        webView.webViewClient = WebViewController(progress)
        webView.loadUrl(intent.getStringExtra("url"))
        webView.addJavascriptInterface(WebAppInterface(this), "Android");
        try {
            if (intent.getStringExtra("id").isNullOrEmpty()) {
                offers.visibility = View.GONE
            }
        } catch (e: Exception) {

        }

        offers.setOnClickListener {
            startActivity(
                Intent(
                    this@WebViewActivity,
                    CouponListActivity::class.java
                ).putExtra("id", intent.getStringExtra("id"))
            )
        }

       /* try {
            val explanatory_image = intent.getStringExtra("explanatory_image")
                displayFullScreenImage(explanatory_image)
        } catch (e : Exception){

        }*/

        full_screen_dialog.setOnClickListener {
            full_screen_dialog.visibility = View.GONE
            appBar.visibility = View.VISIBLE
        }

    }

    /*private fun displayFullScreenImage(explanatoryImage: String) {
        if (!explanatoryImage.isNullOrEmpty() && explanatoryImage != "null"){
             Glide.with(this).load(explanatoryImage).into(fullScreenImage)
            full_screen_dialog.visibility = View.VISIBLE
            appBar.visibility = View.GONE
        } else {
            full_screen_dialog.visibility = View.GONE
            appBar.visibility = View.VISIBLE
        }
    }*/

    class WebViewController(val progress: ProgressBar) : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progress.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progress.visibility = View.GONE
        }
    }
}

class WebAppInterface(webViewActivity: WebViewActivity) {
    @JavascriptInterface
    fun submitCorrect() {


    }
}
