package com.ameen.expirydatetracker.ui.fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ameen.expirydatetracker.adapter.ItemExpireAdapter
import com.ameen.expirydatetracker.databinding.FragmentHomeBinding
import com.ameen.expirydatetracker.ui.MainActivity
import com.ameen.expirydatetracker.viewmodel.ItemViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val TAG = "HomeFragment"

    private lateinit var itemExpireAdapter: ItemExpireAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setupOnViewCreated() {

        initRecyclerView()

        //initialized in the BaseFragment()
        itemViewModel.scannedItem.observe(this, Observer {
            itemExpireAdapter.diff.submitList(it)
        })
    }

    private fun initRecyclerView() {
        itemExpireAdapter = ItemExpireAdapter()
        binding.itemScannedRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = itemExpireAdapter
        }

        itemViewModel.getAllScannedItemLocally()

        Log.i(TAG, "initRecyclerView: Called")
    }
}