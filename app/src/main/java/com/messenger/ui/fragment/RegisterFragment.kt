package com.messenger.ui.fragment

import android.os.Bundle
import android.view.View
import com.messenger.R
import com.messenger.databinding.FragmentRegisterBinding
import com.messenger.presentation.AccountViewModel
import com.messenger.ui.App

class RegisterFragment : BaseFragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding
        get() = _binding ?: throw RuntimeException("FragmentRegisterBinding == null")

    override val layoutId = R.layout.fragment_register
    override val titleToolbar = R.string.register

    private val accountViewModel: AccountViewModel by lazy { viewModel {  } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //App.appComponent.inject(this)

//        accountViewModel = viewModel {
//            onSuccess(registerData, ::handleRegister)
//            onFailure(failureData, ::handleFailure)
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        btnNewMembership.setOnClickListener {
//            register()
//        }
    }

//    private fun validateFields(): Boolean {
//        val allFields = arrayOf(etEmail, etPassword, etConfirmPassword, etusername)
//        var allValid = true
//        for (field in allFields) {
//            allValid = field.testValidity() && allValid
//        }
//        return allValid && validatePasswords()
//    }

//    private fun validatePasswords(): Boolean {
//        val valid = etPassword.text.toString() == etConfirmPassword.text.toString()
//        if (!valid) {
//            showMessage(getString(R.string.error_password_mismatch))
//        }
//        return valid
//    }

//    private fun register() {
//        hideSoftKeyboard()
//
//        val allValid = validateFields()
//
//        if (allValid) {
//            showProgress()
//
//            accountViewModel.register(
//                etEmail.text.toString(),
//                etusername.text.toString(),
//                etPassword.text.toString()
//            )
//        }
//    }

//    private fun handleRegister(none: None? = None()) {
//        hideProgress()
//        showMessage("Аккаунт создан")
//    }
}