package com.messenger.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.messenger.domain.friends.*
import com.messenger.domain.type.None
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendsRequestsViewModel @Inject constructor(
    val getFriendRequestsUseCase: GetFriendRequests,
    val approveFriendRequestUseCase: ApproveFriendRequest,
    val cancelFriendRequestUseCase: CancelFriendRequest
) : BaseViewModel()  {

    private val _friendRequestsData: MutableLiveData<List<FriendEntity>> = MutableLiveData()
    val friendRequestsData: LiveData<List<FriendEntity>>
        get() = _friendRequestsData

    private val _approveFriendData: MutableLiveData<None> = MutableLiveData()
    val approveFriendData: LiveData<None>
        get() = _approveFriendData

    private val _cancelFriendData: MutableLiveData<None> = MutableLiveData()
    val cancelFriendData: LiveData<None>
        get() = _cancelFriendData

    init {
        viewModelScope.launch {
            loadContent()
        }
    }

    override suspend fun loadContent() {
        getFriendRequests()
    }

    fun getFriendRequests() {
        _isLoading.value = true
        getFriendRequestsUseCase(None()) { it.either(::handleFailure, ::handleFriendRequests) }
    }

    fun approveFriend(friendEntity: FriendEntity) {
        _isLoading.value = true
        approveFriendRequestUseCase(friendEntity) { it.either(::handleFailure, ::handleApproveFriend) }
    }

    fun cancelFriend(friendEntity: FriendEntity) {
        _isLoading.value = true
        cancelFriendRequestUseCase(friendEntity) { it.either(::handleFailure, ::handleCancelFriend) }
    }

    private fun handleFriendRequests(friends: List<FriendEntity>) {
        _isLoading.value = false
        _friendRequestsData.value = friends
    }

    private fun handleApproveFriend(none: None) {
        _isLoading.value = false
        _approveFriendData.value = none
    }

    private fun handleCancelFriend(none: None) {
        _isLoading.value = false
        _cancelFriendData.value = none
    }


    override fun onCleared() {
        super.onCleared()
        getFriendRequestsUseCase.unsubscribe()
        approveFriendRequestUseCase.unsubscribe()
        cancelFriendRequestUseCase.unsubscribe()
    }
}