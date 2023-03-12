package com.messenger.ui.core.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.messenger.databinding.FragmentFriendsRequestsBinding
import com.messenger.domain.friends.FriendEntity
import com.messenger.domain.type.None
import com.messenger.presentation.viewModel.FriendsRequestsViewModel
import com.messenger.presentation.viewModel.FriendsViewModel
import com.messenger.ui.App
import com.messenger.ui.core.friends.FriendRequestsAdapter
import com.messenger.ui.fragment.BaseFragment


class FriendRequestsFragment : BaseFragment() {

    private val viewAdapter by lazy {
        FriendRequestsAdapter(::approveFriend, ::cancelFriend)
    }

    private val friendsViewModel: FriendsRequestsViewModel by lazy { viewModel {} }

    private var _bindings: FragmentFriendsRequestsBinding? = null
    val bindings: FragmentFriendsRequestsBinding
        get() = _bindings ?: throw RuntimeException("FragmentFriendsRequestsBinding")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindings = FragmentFriendsRequestsBinding.inflate(inflater, container, false)
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
        initSubscribes()
    }

    private fun setupBindings(){
        val lm = LinearLayoutManager(context)

        bindings.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = lm
            adapter = viewAdapter
        }

        bindings.swipeRefresh.setOnRefreshListener {
            friendsViewModel.refreshContent()
        }
    }

    private fun initSubscribes(){
        friendsViewModel.isRefreshing.observe(viewLifecycleOwner) {
            bindings.swipeRefresh.isRefreshing
        }

        friendsViewModel.apply {
            friendRequestsData.observe(viewLifecycleOwner){
                handleFriendRequests(it)
            }
            approveFriendData.observe(viewLifecycleOwner){
                handleFriendRequestAction(it)
            }
            cancelFriendData.observe(viewLifecycleOwner){
                handleFriendRequestAction(it)
            }
            failureData.observe(viewLifecycleOwner){
                handleFailure(it)
            }
            isLoading.observe(viewLifecycleOwner) {
                if (it)
                    showProgress()
                else
                    hideProgress()
            }
        }
    }

    private fun approveFriend(friend: FriendEntity){
        friendsViewModel.approveFriend(friend)
    }

    private fun cancelFriend(friend: FriendEntity){
        friendsViewModel.cancelFriend(friend)
    }

    override fun onResume() {
        super.onResume()
        friendsViewModel.getFriendRequests()
    }

    private fun handleFriendRequests(requests: List<FriendEntity>?) {
        if (requests != null) {
            viewAdapter.submitList(requests)
        }
    }

    private fun handleFriendRequestAction(none: None?) {
        friendsViewModel.getFriendRequests()
    }
}