package com.rajith.codetest.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rajith.codetest.NewsApp
import com.rajith.codetest.R
import com.rajith.codetest.databinding.FragmentProfileBinding
import com.rajith.codetest.di.DaggerNewsComponent
import com.rajith.codetest.util.Constant
import com.rajith.codetest.util.Constant.IS_REGISTERED
import com.rajith.codetest.view.base.BaseFragment
import com.rajith.codetest.viewmodel.ProfileViewModel
import javax.inject.Inject


class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    @Inject
    lateinit var factory: ProfileViewModel.Factory
    override val vm: ProfileViewModel by viewModels(factoryProducer = { factory })

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    @Inject
    lateinit var editor: SharedPreferences.Editor

    override fun getLayoutResourceId() = R.layout.fragment_profile

    override fun initDaggerComponent() {
        DaggerNewsComponent
            .builder()
            .coreComponent(NewsApp.coreComponent(requireContext()))
            .build()
            .inject(this)
    }


    companion object {

        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener { vm.onLoginClicked(
                binding.tlUsername.editText?.text.toString(),
                binding.tlPassword.editText?.text.toString()
            )
        }
        binding.btnLogout.setOnClickListener { showRegisterView() }

        //checking wether user registered or not by sharedPreferences data
        if (sharedPreferences.getBoolean(Constant.IS_REGISTERED, false)) {
            showRegisteredView()
        } else {
            showRegisterView()
        }


    }

    override fun onStart() {
        super.onStart()
        vm.isRegisteredSuccess.observe(this, Observer<Boolean> { aBoolean ->
            if (aBoolean) {
                Toast.makeText(
                    activity,
                    getString(R.string.txt_register_success),
                    Toast.LENGTH_SHORT
                ).show()
                //save user data to sharedPreferences
                editor.putBoolean(IS_REGISTERED, true)
                editor.putString(Constant.EMAIL, binding.tlUsername.editText?.text.toString())
                editor.putString(Constant.PASSWORD, binding.tlPassword.editText?.text.toString())
                editor.commit()
                showRegisteredView()
            } else {
                Toast.makeText(activity, getString(R.string.txt_register_error), Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }

    private fun showRegisteredView() {
        val email = sharedPreferences.getString(Constant.EMAIL, null)
        binding.tvRegister.text = getString(R.string.txt_welcome)
        binding.tlUsername.visibility = View.GONE
        binding.tlPassword.visibility = View.GONE
        binding.btnRegister.visibility = View.GONE
        binding.tvWelcome.visibility = View.VISIBLE
        binding.btnLogout.visibility = View.VISIBLE
        binding.tvWelcome.text = getString(R.string.txt_registered_with, email)
    }

    private fun showRegisterView() {
        //remove user data from sharedPreferences
        editor.putBoolean(IS_REGISTERED, false)
        editor.putString(Constant.EMAIL,null)
        editor.putString(Constant.PASSWORD, null)
        editor.commit()
        binding.tvRegister.text = getString(R.string.txt_join_us)
        binding.tlUsername.visibility = View.VISIBLE
        binding.tlPassword.visibility = View.VISIBLE
        binding.btnRegister.visibility = View.VISIBLE
        binding.tvWelcome.visibility = View.GONE
        binding.btnLogout.visibility = View.GONE
        binding.tvWelcome.text = ""

    }

}