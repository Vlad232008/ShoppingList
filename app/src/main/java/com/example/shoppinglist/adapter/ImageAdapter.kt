package com.example.shoppinglist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class ImageAdapter(/*private val listener: Listener,*/ private val names: MutableList<String>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.imViewImage)
        var delete:Button = itemView.findViewById(R.id.imClose)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageURI(names[position].toUri())
        holder.imageView.setOnClickListener {
            //listener.onClickItem(position)
        }
        holder.delete.setOnClickListener {
            //listener.deleteItem(position)
        }
    }

    override fun getItemCount() = names.size

    interface Listener {
        fun deleteItem(id: Int)
        fun onClickItem(position: Int)
    }
}