package com.healthtunnel.ui.auth.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.databinding.FragmentVerifyOtpBinding
import com.healthtunnel.ui.auth.AuthViewModel
import kotlinx.android.synthetic.main.fragment_verify_otp.*

class VerifyOTPFragment : Fragment() {

    val viewMOdel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentVerifyOtpBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_verify_otp, container, false)
        binding.viewmodel = viewMOdel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewMOdel.newUser.observe(viewLifecycleOwner, Observer {
            viewMOdel.switchHomeOrSignup(it)
        })
    }
}