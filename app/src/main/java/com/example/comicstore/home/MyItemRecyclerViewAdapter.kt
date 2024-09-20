package com.example.comicstore.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ComicStore.databinding.FragmentComicBinding
import com.example.comicstore.data.Comic

interface ComicItemListener {
    fun onItemSelected(position: Int)
}

class MyItemRecyclerViewAdapter(
    private val listener: ComicItemListener
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {
    private var values: List<Comic> = ArrayList()

    fun updateData(comicList: List<Comic>) {
        values = comicList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentComicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener.onItemSelected(position)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentComicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Comic) {
            binding.comicItem = item
            binding.executePendingBindings()
        }
    }
}
