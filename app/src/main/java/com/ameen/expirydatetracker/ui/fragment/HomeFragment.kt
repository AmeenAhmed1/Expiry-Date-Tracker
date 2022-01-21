package com.ameen.expirydatetracker.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ameen.expirydatetracker.adapter.ItemExpireAdapter
import com.ameen.expirydatetracker.data.ItemModel
import com.ameen.expirydatetracker.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var itemExpireAdapter: ItemExpireAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setupOnViewCreated() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        itemExpireAdapter = ItemExpireAdapter()
        binding.itemScannedRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = itemExpireAdapter
        }


        itemExpireAdapter.diff.submitList(
            arrayListOf(
                ItemModel(title = "First", category = "First"),
                ItemModel(title = "Second", category = "Second"),
                ItemModel(title = "Third", category = "Third"),
                ItemModel(title = "Fourth", category = "Fourth"),
            )
        )
    }
}