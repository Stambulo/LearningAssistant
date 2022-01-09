package com.stambulo.learningassistant.view.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.stambulo.learningassistant.databinding.ItemClassesAdditionalBinding
import com.stambulo.learningassistant.databinding.ItemClassesBinding
import com.stambulo.learningassistant.repository.ImageLoader
import com.stambulo.learningassistant.view.model.DataItemClasses

class ClassesAdapter(val clickListener: OnListItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var imageLoader: ImageLoader = ImageLoader()
    private val ITEM_REGULAR_TYPE = 1
    private val ITEM_ADDITIONAL_TYPE = 2
    private lateinit var onListItemClickListener: OnListItemClickListener
    private lateinit var bindingRegularClass: ItemClassesBinding
    private lateinit var bindingAdditionalClass: ItemClassesAdditionalBinding
    private var data: MutableList<DataItemClasses> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_REGULAR_TYPE -> {
                bindingRegularClass = ItemClassesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false)
                RegularViewHolder(bindingRegularClass)
            }
            ITEM_ADDITIONAL_TYPE -> {
                bindingAdditionalClass = ItemClassesAdditionalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false)
                AdditionalViewHolder(bindingAdditionalClass)
            }
            else -> throw ClassCastException("ClassesAdapter - $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is RegularViewHolder -> holder.bind(data[position])
            is AdditionalViewHolder -> holder.bind(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is DataItemClasses.Classes -> ITEM_REGULAR_TYPE
            is DataItemClasses.AdditionalClasses -> ITEM_ADDITIONAL_TYPE
            else -> 0
        }
    }

    fun update(lessonsList: List<DataItemClasses>){
        data.clear()
        data.addAll(lessonsList)
    }

    inner class RegularViewHolder(private val binding: ItemClassesBinding) :
        RecyclerView.ViewHolder(bindingRegularClass.root) {
        fun bind(data: DataItemClasses) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                if (data is DataItemClasses.Classes) {
                    binding.tvClasses.text = data.subject
                    binding.tvTime.text = "${data.startLesson}-${data.endLesson}"
                    binding.grOpenin.isVisible = data.openSkype
                    imageLoader.loadInto(data.icon, binding.imageView)
                    if (data.openSkype) binding.cardView.setOnClickListener{
                        clickListener.onItemClick()
                    }
                }
            }
        }
    }

    inner class AdditionalViewHolder(private val binding: ItemClassesAdditionalBinding) :
        RecyclerView.ViewHolder(bindingAdditionalClass.root) {
        fun bind(data: DataItemClasses) {
            if(layoutPosition != RecyclerView.NO_POSITION){
                if (data is DataItemClasses.AdditionalClasses){
                    binding.tvClasses.text = data.subject
                    binding.tvTime.text = "${data.startLesson}-${data.endLesson}"
                    binding.tvInfo.text = data.topic
                    binding.grOpenin.isVisible = data.openSkype
                    imageLoader.loadInto(data.icon, binding.imageView)
                    if (data.openSkype) binding.cardView.setOnClickListener{
                        clickListener.onItemClick()
                    }
                }
            }
        }
    }
}

interface OnListItemClickListener {
    fun onItemClick()
}
