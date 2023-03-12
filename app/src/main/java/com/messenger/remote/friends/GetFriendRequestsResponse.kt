package com.messenger.remote.friends

import com.google.gson.annotations.SerializedName
import com.messenger.domain.friends.FriendEntity
import com.messenger.remote.core.BaseResponse

class GetFriendRequestsResponse (
    success: Int,
    message: String,
    @SerializedName("friend_requests")
    val friendsRequests: List<FriendEntity>
) : BaseResponse(success, message)