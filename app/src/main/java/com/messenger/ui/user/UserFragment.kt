package com.messenger.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.messenger.R
import com.messenger.databinding.FragmentFriendsListBinding
import com.messenger.databinding.FragmentUserBinding
import com.messenger.presentation.viewModel.FriendsViewModel
import com.messenger.ui.App
import com.messenger.ui.core.friends.AddFriendFragment
import com.messenger.ui.fragment.BaseFragment

class UserFragment : BaseFragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding: FragmentUserBinding
        get() = _binding ?: throw RuntimeException("FragmentNavigationBinding == null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}