package com.messenger.ui.core.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.messenger.R
import com.messenger.databinding.FragmentFriendsBinding
import com.messenger.domain.friends.FriendEntity
import com.messenger.domain.type.None
import com.messenger.presentation.viewModel.FriendsViewModel
import com.messenger.ui.App
import com.messenger.ui.core.adapters.FriendsAdapter
import com.messenger.ui.fragment.BaseFragment

class FriendsFragment : BaseFragment() {

    private var _binding: FragmentFriendsBinding? = null
    private val binding: FragmentFriendsBinding
        get() = _binding ?: throw RuntimeException("FragmentNavigationBinding == null")

    private val friendsViewModel: FriendsViewModel by lazy { viewModel {} }

    override val titleToolbar = R.string.screen_friends

    private val tabTitles = listOf("Друзья", "Входящие запросы")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
    }

    private fun setupBindings() {

        binding.viewPager.adapter = FriendsAdapter(
            this,
            arrayListOf(
                FriendsListFragment(),
                FriendRequestsFragment(),
            )
        )

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}