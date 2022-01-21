package com.ameen.expirydatetracker.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ameen.expirydatetracker.databinding.FragmentExpireBinding

class ExpireFragment : BaseFragment<FragmentExpireBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentExpireBinding
        get() = FragmentExpireBinding::inflate

    override fun setupOnViewCreated() {
    }
}