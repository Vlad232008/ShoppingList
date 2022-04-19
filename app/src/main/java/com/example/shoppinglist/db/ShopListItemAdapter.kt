package com.example.shoppinglist.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ShopListItemBinding
import com.example.shoppinglist.entities.ShopListItem

class ShopListItemAdapter(private val listener: Listener) :
    ListAdapter<ShopListItem, ShopListItemAdapter.ItemHolder>(ItemComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        return if (viewType == 0)
            ItemHolder.createShopItem(parent)
        else
            ItemHolder.createLibraryItem(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if (getItem(position).itemType == 0) {
            holder.setItemData(getItem(position), listener)
        } else {
            holder.setLibraryData(getItem(position), listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun setItemData(shopListNameItem: ShopListItem, listener: Listener){
            val binding = ShopListItemBinding.bind(view)
            binding.apply {
                tvName.text = shopListNameItem.name
                tvInfo.text = shopListNameItem.itemInfo
                tvInfo.visibility = infoVisibility(shopListNameItem)
                cBox.isChecked = shopListNameItem.itemChecked == 1
            }
        }

        fun setLibraryData(shopListNameItem: ShopListItem, listener: Listener){
            val binding = ShopListItemBinding.bind(view)
            binding.apply {

            }
        }

        private fun infoVisibility(shopListNameItem: ShopListItem): Int{
            return if (shopListNameItem.itemInfo?.isNullOrEmpty() == true) {
                View.GONE
            } else View.VISIBLE
        }

        companion object {
            fun createShopItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shop_list_item, parent, false)
                )
            }

            fun createLibraryItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.library_list_item, parent, false)
                )
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<ShopListItem>() {
        override fun areItemsTheSame(
            oldItem: ShopListItem,
            newItem: ShopListItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShopListItem,
            newItem: ShopListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener {
        fun deleteItem(id: Int)
        fun editItem(nameItem: ShopListItem)
        fun onClickItem(nameItem: ShopListItem)
    }
}
