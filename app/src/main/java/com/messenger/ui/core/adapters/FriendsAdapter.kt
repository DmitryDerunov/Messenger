package com.messenger.ui.core.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FriendsAdapter(rootFragment: Fragment, private val fragmentList: ArrayList<Fragment>): FragmentStateAdapter(rootFragment) {


    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}