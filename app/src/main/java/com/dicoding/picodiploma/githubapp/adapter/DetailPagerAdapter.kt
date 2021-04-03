package com.dicoding.picodiploma.githubapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.picodiploma.githubapp.ui.FollowFragment

class DetailPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    var username: String? = null

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(username, position)
    }
}