package com.messenger.ui.core.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.messenger.R
import com.messenger.databinding.FragmentFriendsBinding
import com.messenger.databinding.FragmentFriendsListBinding
import com.messenger.domain.friends.FriendEntity
import com.messenger.domain.type.None
import com.messenger.presentation.viewModel.FriendsViewModel
import com.messenger.ui.App
import com.messenger.ui.fragment.BaseFragment

class FriendsListFragment : BaseFragment() {

    private val viewAdapter by lazy {
        FriendsListAdapter {
            showDeleteFriendDialog(it)
        }
    }

    private var _binding: FragmentFriendsListBinding? = null
    private val binding: FragmentFriendsListBinding
        get() = _binding ?: throw RuntimeException("FragmentNavigationBinding == null")

    private val friendsViewModel: FriendsViewModel by lazy { viewModel {} }

    override val titleToolbar = R.string.screen_friends

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
        initSubscribes()
    }

    private fun setupBindings(){
        val lm = LinearLayoutManager(context)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = lm
            adapter = viewAdapter
        }

        binding.addFriendBtn.setOnClickListener {
            AddFriendFragment(friendsViewModel).show(parentFragmentManager, "AddFriendTag")
        }
    }

    private fun initSubscribes(){

        friendsViewModel.friendsData.observe(viewLifecycleOwner) {
            handleFriends(it)
        }

        friendsViewModel.deleteFriendData.observe(viewLifecycleOwner) {
            handleDeleteFriend(it)
        }

        friendsViewModel.failureData.observe(viewLifecycleOwner) {
            handleFailure(it)
        }

        friendsViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it)
                showProgress()
            else
                hideProgress()
        }
    }


    private fun showDeleteFriendDialog(entity: FriendEntity) {
        requireActivity().let {
            AlertDialog.Builder(it)
                .setMessage(getString(R.string.delete_friend))

                .setPositiveButton(android.R.string.yes) { dialog, which ->
                    friendsViewModel.deleteFriend(entity)
                }

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
    }


    private fun handleFriends(friends: List<FriendEntity>?) {
        if (friends != null) {
            viewAdapter.submitList(friends)
        }
    }

    private fun handleDeleteFriend(none: None?) {
        friendsViewModel.getFriends()
    }
}