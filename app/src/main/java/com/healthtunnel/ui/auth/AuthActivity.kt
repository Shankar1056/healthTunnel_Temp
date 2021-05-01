package com.healthtunnel.ui.auth

import android.content.Intent
import android.os.Bundle
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
    //var smsVerifyCatcher: SmsVerifyCatcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        /* smsVerifyCatcher = SmsVerifyCatcher(
             this
         ) { message ->
             viewModel.parseCode(message)

         }*/

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

    override fun onStart() {
        super.onStart()
        //smsVerifyCatcher!!.onStart()
    }

    override fun onStop() {
        super.onStop()
        //smsVerifyCatcher!!.onStop()
    }

    /*override fun onRequestPermissionsResult(
        requestCode: Int,
       permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        smsVerifyCatcher!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }*/


    /* override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         TruecallerSDK.getInstance().onActivityResultObtained(this, resultCode, data)
     }*/
}