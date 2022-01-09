package com.stambulo.learningassistant.view.fragments.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stambulo.learningassistant.databinding.ItemHomeworkBinding
import com.stambulo.learningassistant.model.Homework
import com.stambulo.learningassistant.repository.ImageLoader

class HomeworkAdapter : RecyclerView.Adapter<HomeworkAdapter.ItemViewHolder>() {
    var imageLoader: ImageLoader = ImageLoader()
    private lateinit var bindingItem: ItemHomeworkBinding
    private var data: MutableList<Homework> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeworkAdapter.ItemViewHolder {
        bindingItem = ItemHomeworkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(bindingItem)
    }

    override fun onBindViewHolder(holder: HomeworkAdapter.ItemViewHolder, position: Int) {
        holder.bind(data = data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<Homework>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: ItemHomeworkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Homework){
            if(layoutPosition != RecyclerView.NO_POSITION){
                binding.tvTime.text = data.Date
                binding.tvTheme.text = data.subject
                binding.tvHomework.text = data.info
                imageLoader.loadInto(data.icon, binding.imageView)
            }
        }
    }
}
