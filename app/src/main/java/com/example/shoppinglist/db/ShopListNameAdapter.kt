package com.example.shoppinglist.db

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ListNameItemBinding
import com.example.shoppinglist.entities.ShopListNameItem

class ShopListNameAdapter(private val listener: Listener) :
    ListAdapter<ShopListNameItem, ShopListNameAdapter.ItemHolder>(ItemComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListNameItemBinding.bind(view)
        fun setData(shopListNameItem: ShopListNameItem, listener: Listener) = with(binding) {
            tvListName.text = shopListNameItem.name
            tvTime.text = shopListNameItem.time
            val counterItem = "${shopListNameItem.checkItemCounter}/${shopListNameItem.countItem}"
            tvCounter.text = counterItem
            pBar.max = shopListNameItem.countItem
            pBar.progress = shopListNameItem.checkItemCounter
            itemView.setOnClickListener {
                listener.onClickItem(shopListNameItem)
            }
            imDelete.setOnClickListener {
                listener.deleteItem(shopListNameItem.id!!)
            }
            imEdit.setOnClickListener {
                listener.editItem(shopListNameItem)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_name_item, parent, false)
                )
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<ShopListNameItem>() {
        override fun areItemsTheSame(
            oldItem: ShopListNameItem,
            newItem: ShopListNameItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShopListNameItem,
            newItem: ShopListNameItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener {
        fun deleteItem(id: Int)
        fun editItem(nameItem: ShopListNameItem)
        fun onClickItem(nameItem: ShopListNameItem)
    }
}