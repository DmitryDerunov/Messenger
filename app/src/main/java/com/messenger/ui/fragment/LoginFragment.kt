package com.messenger.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.messenger.R
import com.messenger.databinding.FragmentLoginBinding
import com.messenger.domain.account.AccountEntity
import com.messenger.presentation.viewModel.AccountViewModel
import com.messenger.ui.App
import com.messenger.ui.DaggerAppComponent

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
    get() = _binding ?: throw RuntimeException("FragmentLoginBinding == null")

    override val titleToolbar = R.string.screen_login

    private val accountViewModel: AccountViewModel by lazy { viewModel () }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
        initSubscribes()
    }

    private fun setupBindings(){
        binding.apply {
            btnLogin.setOnClickListener {
                validateFields()
            }

            btnRegister.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
        }
    }

    private fun initSubscribes(){
        accountViewModel.failureData.observe(viewLifecycleOwner){
            handleFailure(it)
        }

        accountViewModel.accountData.observe(viewLifecycleOwner){
            renderAccount(it)
        }
    }

    private fun validateFields() {
        hideSoftKeyboard()
        val allFields = arrayOf(binding.etEmail, binding.etPassword)
        var allValid = true
        for (field in allFields) {
            allValid = field.testValidity() && allValid
        }
        if (allValid) {
            login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun login(email: String, password: String) {
        accountViewModel.login(email, password)
    }

    private fun renderAccount(account: AccountEntity?) {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavigationFragment())
    }
}