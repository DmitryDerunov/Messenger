package com.messenger.ui.core.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.messenger.R
import com.messenger.databinding.ItemFriendRequestBinding
import com.messenger.domain.friends.FriendEntity
import com.messenger.ui.core.GlideHelper

class FriendRequestsAdapter(
    private val onItemCLick: (FriendEntity) -> Unit,
    private val onAcceptRequestAction: (FriendEntity) -> Unit,
    private val onDeclineRequestAction: (FriendEntity) -> Unit
) : ListAdapter<FriendEntity, FriendRequestsAdapter.FriendRequestViewHolder>(
    FriendsRequestsDiffCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemFriendRequestBinding.inflate(inflater, parent, false)
        return FriendRequestViewHolder(itemBinding, onItemCLick, onAcceptRequestAction, onDeclineRequestAction)
    }

    override fun onBindViewHolder(holder: FriendRequestViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FriendRequestViewHolder(
        private val binding: ItemFriendRequestBinding,
        private val onItemCLick: (FriendEntity) -> Unit,
        private val onAcceptRequestAction: (FriendEntity) -> Unit,
        private val onDeclineRequestAction: (FriendEntity) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var friend: FriendEntity? = null

        init {
            binding.btnApprove.setOnClickListener {
                friend?.let{
                    onAcceptRequestAction(it)
                }
            }

            binding.btnCancel.setOnClickListener {
                friend?.let{
                    onDeclineRequestAction(it)
                }
            }

            binding.root.setOnClickListener {
                friend?.let(onItemCLick)
            }
        }

        fun bind(friend: FriendEntity) {
            this.friend = friend
            GlideHelper.loadImage(binding.root.context, friend.image, binding.imgPhoto, R.drawable.ic_account_circle)
            binding.tvName.text = friend.name
        }
    }

}

class FriendsRequestsDiffCallBack : DiffUtil.ItemCallback<FriendEntity>() {
    override fun areItemsTheSame(oldItem: FriendEntity, newItem: FriendEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FriendEntity, newItem: FriendEntity): Boolean {
        return oldItem == newItem
    }
}