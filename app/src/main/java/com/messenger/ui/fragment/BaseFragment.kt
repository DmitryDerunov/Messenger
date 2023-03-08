package com.messenger.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.messenger.R
import com.messenger.domain.type.Failure
import com.messenger.ui.AppActivity
import com.messenger.ui.base
import javax.inject.Inject

abstract class BaseFragment() : Fragment() {


    open val titleToolbar = R.string.app_name
    open val showToolbar = true


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onResume() {
        super.onResume()

        base {
            if (showToolbar) supportActionBar?.show() else supportActionBar?.hide()
            supportActionBar?.title = getString(titleToolbar)
        }
    }

    open fun onBackPressed() {}


    fun showProgress() = base { progressStatus(View.VISIBLE) }

    fun hideProgress() = base { progressStatus(View.GONE) }


    fun hideSoftKeyboard() = base { hideSoftKeyboard() }


    fun handleFailure(failure: Failure?) = base { handleFailure(failure) }

    fun showMessage(message: String) = base { showMessage(message) }


    inline fun base(block: AppActivity.() -> Unit) {
        activity.base(block)
    }


    inline fun <reified T : ViewModel> viewModel(body: T.() -> Unit): T {
        val vm = ViewModelProvider(this, viewModelFactory)[T::class.java]
        vm.body()
        return vm
    }

    fun close() = findNavController().popBackStack()
}
