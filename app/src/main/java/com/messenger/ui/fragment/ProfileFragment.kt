package com.messenger.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.messenger.R
import com.messenger.databinding.FragmentProfileBinding
import com.messenger.domain.account.AccountEntity
import com.messenger.presentation.viewModel.AccountViewModel
import com.messenger.presentation.viewModel.MediaViewModel
import com.messenger.ui.App
import com.messenger.ui.core.GlideHelper

class ProfileFragment : BaseFragment() {

    override val titleToolbar = R.string.screen_account

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding ?: throw RuntimeException("FragmentNavigationBinding == null")

    private val accountViewModel: AccountViewModel by lazy { viewModel() }
    private val mediaViewModel: MediaViewModel by lazy { viewModel() }

    private var accountEntity: AccountEntity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
        initSubscribes()
    }

    private fun setupBindings(){
        binding.btnEdit.setOnClickListener {
            binding.root.clearFocus()
            val fieldsValid = validateFields()
            if (!fieldsValid) {
                return@setOnClickListener
            }

            val passwordsValid = validatePasswords()
            if (!passwordsValid) {
                return@setOnClickListener
            }

            val email = binding.etEmail.text.toString()
            val name = binding.etName.text.toString()
            val status = binding.etStatus.text.toString()
            val password = binding.etNewPassword.text.toString()

            accountEntity?.let {
                it.email = email
                it.name = name
                it.status = status

                if (password.isNotEmpty()) {
                    it.password = password
                }

                accountViewModel.editAccount(it)
            }
        }

        binding.imgPhoto.setOnClickListener {
            base {
//                navigator.showPickFromDialog(this) { fromCamera ->
//                    if (fromCamera) {
//                        mediaViewModel.createCameraFile()
//                    } else {
//                        navigator.showGallery(this)
//                    }
//                }
            }
        }
    }

    private fun initSubscribes(){
        accountViewModel.accountData.observe(viewLifecycleOwner){
            handleAccount(it)
        }
        accountViewModel.failureData.observe(viewLifecycleOwner){
            handleFailure(it)
        }
        accountViewModel.editAccountData.observe(viewLifecycleOwner){
            handleEditingAccount(it)
        }

        accountViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it)
                showProgress()
            else
                hideProgress()
        }

        mediaViewModel.cameraFileCreatedData.observe(viewLifecycleOwner){
            onCameraFileCreated(it)
        }
        mediaViewModel.pickedImageData.observe(viewLifecycleOwner){
            onImagePicked(it)
        }
        mediaViewModel.failureData.observe(viewLifecycleOwner){
            handleFailure(it)
        }


        mediaViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it)
                showProgress()
            else
                hideProgress()
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        mediaViewModel.onPickImageResult(requestCode, resultCode, data)
    }


    private fun validatePasswords(): Boolean {
        val currentPassword = binding.etCurrentPassword.text.toString()
        val newPassword = binding.etNewPassword.text.toString()

        return if (currentPassword.isNotEmpty() && newPassword.isNotEmpty()) {
            return if (currentPassword == accountEntity?.password) {
                true
            } else {
                showMessage(getString(R.string.error_wrong_password))
                false
            }
        } else if (currentPassword.isEmpty() && newPassword.isEmpty()) {
            true
        } else {
            showMessage(getString(R.string.error_empty_password))
            false
        }
    }

    private fun validateFields(): Boolean {
        hideSoftKeyboard()
        val allFields = arrayOf(binding.etEmail, binding.etName)
        var allValid = true
        for (field in allFields) {
            allValid = field.testValidity() && allValid
        }
        return allValid
    }

    private fun handleAccount(account: AccountEntity?) {
        accountEntity = account
        account?.let {
            GlideHelper.loadImage(requireActivity(), it.image, binding.imgPhoto)
            binding.etEmail.setText(it.email)
            binding.etName.setText(it.name)
            binding.etStatus.setText(it.status)

            binding.etCurrentPassword.setText("")
            binding.etNewPassword.setText("")
        }
    }


    private fun onCameraFileCreated(uri: Uri?) {
        base {
            if (uri != null) {
                //navigator.showCamera(this, uri)
            }
        }
    }

    private fun onImagePicked(pickedImage: MediaViewModel.PickedImage?) {
        if (pickedImage != null) {
            accountEntity?.image = pickedImage.string

            val placeholder = binding.imgPhoto.drawable
            Glide.with(this)
                .load(pickedImage.bitmap)
                .placeholder(placeholder)
                .error(placeholder)
                .into(binding.imgPhoto)
        }
    }

    private fun handleEditingAccount(account: AccountEntity?) {
        showMessage(getString(R.string.success_edit_user))
        accountViewModel.getAccount()
    }
}