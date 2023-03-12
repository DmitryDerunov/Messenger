package com.messenger.domain.friends

import com.messenger.domain.friends.FriendEntity
import com.messenger.domain.friends.FriendsRepository
import com.messenger.domain.interactor.UseCase
import com.messenger.domain.type.None
import javax.inject.Inject

class GetFriendRequests @Inject constructor(
    private val friendsRepository: FriendsRepository
) : UseCase<List<FriendEntity>, None>() {

    override suspend fun run(params: None) = friendsRepository.getFriendRequests()
}