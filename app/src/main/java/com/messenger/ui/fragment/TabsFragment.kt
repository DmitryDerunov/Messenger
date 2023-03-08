package com.messenger.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.messenger.R
import com.messenger.databinding.FragmentTabsBinding
import com.messenger.presentation.viewModel.AccountViewModel
import com.messenger.utils.mainActivity

class TabsFragment : BaseFragment() {

    private val _binding: FragmentTabsBinding? = null
    private val binding: FragmentTabsBinding
    get() = _binding ?: throw RuntimeException("FragmentNavigationBinding == null")

    private lateinit var accountViewModel: AccountViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController

        mainActivity().bottomNavigationView = binding.bottomNavigationView
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }
}