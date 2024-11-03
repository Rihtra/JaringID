package com.example.tambakudangapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2 // Dua fragment: Stok dan Transaksi

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TransaksiFragment() // Transaksi Fragment
            1 -> StokFragment() // Stok Fragment
            else -> TransaksiFragment() // Default
        }
    }
}
