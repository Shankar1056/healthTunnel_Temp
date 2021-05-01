package com.healthtunnel.ui

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.healthtunnel.MainActivity
import com.healthtunnel.R
import com.healthtunnel.ui.auth.AuthActivity
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


       /* val info: PackageInfo
        try {
            info = packageManager.getPackageInfo("com.healthtunnel", PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                var md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something: String = String(Base64.encode(md.digest(), 0))
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something)
            }
        } catch (e1: PackageManager.NameNotFoundException) {
            Log.e("name not found", e1.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.e("no such an algorithm", e.toString())
        } catch (e: Exception) {
            Log.e("exception", e.toString())
        }*/


        Handler().postDelayed(Runnable {
            if (ClsGeneral.getPreferences(Constant.EMAIL).isNullOrEmpty()) {
                startActivity(Intent(this, AuthActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
//                startActivity(Intent(this, RemainderListActivity::class.java))
            }

            finish()
        }, 3000)


    }
}