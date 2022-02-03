package com.ameen.expirydatetracker.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ameen.expirydatetracker.data.ItemModel
import com.ameen.expirydatetracker.databinding.ItemScannedBinding

class ItemExpireAdapter() :
    RecyclerView.Adapter<ItemExpireAdapter.MyViewHolder>() {

    private val TAG = "ItemExpireAdapter"

    inner class MyViewHolder(val binding: ItemScannedBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var _binding: ItemScannedBinding? = null

    private val differCallBack = object : DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem == newItem
        }
    }

    val diff = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        _binding = ItemScannedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(_binding!!)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val sortedList = diff.currentList.sortedBy { it.expireDate }
        val currentItem = sortedList[position]

        currentItem?.apply {
            if (isExpired) holder.binding.daysLeftTextView.text = "Expire"
            else holder.binding.daysLeftTextView.text =
                "$daysLeft days left"
        }

        holder.binding.apply {
            titleTextView.text = currentItem.title
            categoryTextView.text = currentItem.category
            expireDateTextView.text = currentItem.expireDate
        }
    }

    override fun getItemCount(): Int = diff.currentList.size
}