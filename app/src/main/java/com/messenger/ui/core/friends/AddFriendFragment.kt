package com.messenger.ui.core.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.messenger.R
import com.messenger.databinding.FragmentAddFriendBinding
import com.messenger.databinding.FragmentFriendsBinding
import com.messenger.presentation.viewModel.FriendsViewModel
import com.messenger.ui.App
import com.messenger.ui.core.adapters.FriendsAdapter
import com.messenger.ui.fragment.BaseFragment

class AddFriendFragment(private val friendsViewModel: FriendsViewModel) : BottomSheetDialogFragment() {

    private var _binding: FragmentAddFriendBinding? = null
    private val binding: FragmentAddFriendBinding
        get() = _binding ?: throw RuntimeException("FragmentNavigationBinding == null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBindings()
    }

    private fun setupBindings(){
        binding.btnAdd.setOnClickListener {
            friendsViewModel.addFriend(binding.etEmail.text.toString())
            dismiss()
        }
    }

}