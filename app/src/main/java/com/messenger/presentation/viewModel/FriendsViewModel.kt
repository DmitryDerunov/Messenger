package com.messenger.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.messenger.domain.friends.*
import com.messenger.domain.type.None
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    val getFriendsUseCase: GetFriends,
    val deleteFriendUseCase: DeleteFriend,
    val addFriendUseCase: AddFriend,
) : BaseViewModel() {

    private val _friendsData: MutableLiveData<List<FriendEntity>> = MutableLiveData()
    val friendsData: LiveData<List<FriendEntity>>
        get() = _friendsData

    private val _deleteFriendData: MutableLiveData<None> = MutableLiveData()
    val deleteFriendData: LiveData<None>
        get() = _deleteFriendData

    private val _addFriendData: MutableLiveData<None> = MutableLiveData()
    val addFriendData: LiveData<None>
        get() = _addFriendData


    init {
        viewModelScope.launch {
            loadContent()
        }
    }

    override suspend fun loadContent() {
        getFriends()
    }

    fun getFriends() {
        _isLoading.value = true
        getFriendsUseCase(None()) { it.either(::handleFailure, ::handleFriends) }
    }


    fun deleteFriend(friendEntity: FriendEntity) {
        _isLoading.value = true
        deleteFriendUseCase(friendEntity) { it.either(::handleFailure, ::handleDeleteFriend) }
    }

    fun addFriend(email: String) {
        _isLoading.value = true
        addFriendUseCase(AddFriend.Params(email)) { it.either(::handleFailure, ::handleAddFriend) }
    }


    private fun handleFriends(friends: List<FriendEntity>) {
        _isLoading.value = false
        _friendsData.value = friends
    }


    private fun handleDeleteFriend(none: None) {
        _isLoading.value = false
        _deleteFriendData.value = none
    }

    private fun handleAddFriend(none: None) {
        _isLoading.value = false
        _addFriendData.value = none
    }


    override fun onCleared() {
        super.onCleared()
        getFriendsUseCase.unsubscribe()
        deleteFriendUseCase.unsubscribe()
        addFriendUseCase.unsubscribe()
    }
}