package com.healthtunnel.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.healthtunnel.MainActivity
import com.healthtunnel.R
import com.healthtunnel.ui.auth.fragment.IntroductionFragment
import com.healthtunnel.ui.auth.fragment.LoginFragment
import com.healthtunnel.ui.auth.fragment.SignUpFragment
import com.healthtunnel.ui.auth.fragment.VerifyOTPFragment

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_auth)

        val viewModel: AuthViewModel by viewModels()
        goToIntroFragment(IntroductionFragment())

        viewModel.buttonClickedOperation.observe(this, Observer {
            when (it) {
                AuthViewModel.ButtonClickedOperation.LOGIN -> goToIntroFragment(LoginFragment())
                AuthViewModel.ButtonClickedOperation.OTP -> goToIntroFragment(VerifyOTPFragment())
                AuthViewModel.ButtonClickedOperation.SIGNP -> goToIntroFragment(SignUpFragment())
                AuthViewModel.ButtonClickedOperation.HOME -> goToHomeScreen()
            }
        })
    }

    private fun goToHomeScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    private fun goToIntroFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
            .addToBackStack(null)
        transaction.commit()
    }
}