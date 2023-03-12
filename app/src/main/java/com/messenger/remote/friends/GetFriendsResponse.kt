package com.messenger.remote.friends

import com.messenger.domain.friends.FriendEntity
import com.messenger.remote.core.BaseResponse


class GetFriendsResponse (
    success: Int,
    message: String,
    val friends: List<FriendEntity>
) : BaseResponse(success, message)