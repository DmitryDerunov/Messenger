package com.messenger.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.messenger.R
import com.messenger.databinding.FragmentChatsBinding
import com.messenger.databinding.FragmentTabsBinding
import com.messenger.presentation.viewModel.AccountViewModel
import com.messenger.utils.mainActivity

class TabsFragment : BaseFragment() {

    private var _binding: FragmentTabsBinding? = null
    private val binding: FragmentTabsBinding
    get() = _binding ?: throw RuntimeException("FragmentNavigationBinding == null")

    private lateinit var accountViewModel: AccountViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController

        mainActivity().bottomNavigationView = binding.bottomNavigationView
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }
}