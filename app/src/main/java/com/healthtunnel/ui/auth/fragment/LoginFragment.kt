package com.healthtunnel.ui.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.healthtunnel.R
import com.healthtunnel.databinding.FragmentLoginBinding
import com.healthtunnel.ui.auth.AuthViewModel


class LoginFragment : Fragment() {
    val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.message.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })

        // checkTrueCallerAvailabiliy()

        //initTrueCaller()
    }

    private fun checkTrueCallerAvailabiliy() {
        /* val a = TruecallerSDK.getInstance().isUsable()

         Toast.makeText(activity, a.toString(), Toast.LENGTH_SHORT).show()*/
    }

    private fun initTrueCaller() {
        /* if (TruecallerSDK.getInstance().isUsable()) {
             activity?.let { TruecallerSDK.getInstance().getUserProfile(it) }
         } else {

         }*/


    }
}