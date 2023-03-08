package com.messenger.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.messenger.R
import com.messenger.databinding.FragmentRegisterBinding
import com.messenger.domain.account.AccountEntity
import com.messenger.domain.type.None
import com.messenger.presentation.viewModel.AccountViewModel
import com.messenger.ui.App

class RegisterFragment : BaseFragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding
        get() = _binding ?: throw RuntimeException("FragmentRegisterBinding == null")

    override val titleToolbar = R.string.register

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        accountViewModel = viewModel { }

        accountViewModel.accountData.observe(viewLifecycleOwner){
            handleLogin(it)
        }
        accountViewModel.failureData.observe(viewLifecycleOwner){
            handleFailure(it)
        }
        accountViewModel.registerData.observe(viewLifecycleOwner){
            handleRegister(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnNewMembership.setOnClickListener {
                register()
            }

            btnAlreadyHaveAkk.setOnClickListener {
                activity?.finish()
            }
        }
    }

    private fun validateFields(): Boolean {
        val allFields = arrayOf(binding.etEmail, binding.etPassword, binding.etConfirmPassword, binding.etusername)
        var allValid = true
        for (field in allFields) {
            allValid = field.testValidity() && allValid
        }
        return allValid && validatePasswords()
    }

    private fun validatePasswords(): Boolean {
        val valid = binding.etPassword.text.toString() == binding.etConfirmPassword.text.toString()
        if (!valid) {
            showMessage(getString(R.string.error_password_mismatch))
        }
        return valid
    }

    private fun register() {
        hideSoftKeyboard()

        val allValid = validateFields()

        if (allValid) {
            showProgress()

            accountViewModel.register(

                binding.etEmail.text.toString(),
                binding.etusername.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

    private fun handleLogin(accountEntity: AccountEntity?) {
        hideProgress()
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToNavigationFragment())
    }

    private fun handleRegister(none: None? = None()) {
        accountViewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
    }
}