package com.ameen.expirydatetracker.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ameen.expirydatetracker.adapter.ItemExpireAdapter
import com.ameen.expirydatetracker.databinding.FragmentExpireBinding

class ExpireFragment : BaseFragment<FragmentExpireBinding>() {

    private lateinit var itemExpireAdapter: ItemExpireAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentExpireBinding
        get() = FragmentExpireBinding::inflate

    override fun setupOnViewCreated() {
        initRecyclerView()

        //initialized in the BaseFragment()
        itemViewModel.expiredItem.observe(this, Observer {
            itemExpireAdapter.diff.submitList(it)
        })
    }

    private fun initRecyclerView() {
        itemExpireAdapter = ItemExpireAdapter()
        binding.itemExpireRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = itemExpireAdapter
        }

        itemViewModel.getAllExpiredItemLocally()
    }
}