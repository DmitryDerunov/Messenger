package com.messenger.ui.core.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.messenger.R
import com.messenger.databinding.ItemFriendBinding
import com.messenger.databinding.ItemFriendRequestBinding
import com.messenger.domain.friends.FriendEntity
import com.messenger.ui.core.BaseAdapter

class FriendsListAdapter(private val onDeleteItemCLick: (FriendEntity) -> Unit) :
    ListAdapter<FriendEntity, FriendsListAdapter.FriendViewHolder>(FriendsDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemFriendBinding.inflate(inflater, parent, false)
        return FriendViewHolder(itemBinding, onDeleteItemCLick)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class FriendViewHolder(
        private val binding: ItemFriendBinding,
        private val onDeleteItemCLick: (FriendEntity) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var friend: FriendEntity? = null

        init {
            binding.btnRemove.setOnClickListener {
                friend?.let {
                    onDeleteItemCLick(it)
                }
            }
        }

        fun bind(friend: FriendEntity) {
            this.friend = friend
            binding.tvName.text = friend.name
            binding.tvStatus.text = friend.status
        }
    }
}

class FriendsDiffCallBack : DiffUtil.ItemCallback<FriendEntity>() {
    override fun areItemsTheSame(oldItem: FriendEntity, newItem: FriendEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FriendEntity, newItem: FriendEntity): Boolean {
        return oldItem == newItem
    }
}