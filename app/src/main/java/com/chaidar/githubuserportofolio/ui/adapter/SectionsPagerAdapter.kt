package com.chaidar.githubuserportofolio.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chaidar.githubuserportofolio.ui.detail.FollowerFragment
import com.chaidar.githubuserportofolio.ui.detail.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val bundle: Bundle) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val followerFragment = FollowerFragment()
                followerFragment.arguments = bundle
                followerFragment
            }
            1 -> {
                val followingFragment = FollowingFragment()
                followingFragment.arguments = bundle
                followingFragment
            }
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}