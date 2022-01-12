package com.stambulo.learningassistant.view.fragments.schedule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.stambulo.learningassistant.databinding.*
import com.stambulo.learningassistant.repository.ImageLoader
import com.stambulo.learningassistant.view.fragments.home.OnListItemClickListener
import com.stambulo.learningassistant.view.fragments.mvi.ClassesState
import com.stambulo.learningassistant.view.model.DataItemClasses
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ClassCastException
import javax.inject.Inject

class ScheduleAdapter(val clickListener: OnListItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var imageLoader: ImageLoader = ImageLoader()
    private lateinit var bindingDelimiter: ItemDelimiterBinding
    private lateinit var bindingClasses: ItemClassesRegularTimesortBinding
    private lateinit var bindingAdditional: ItemClassesAdditionalTimesortBinding
    private val DELIMITER_TYPE = 0
    private val CLASS_TYPE = 1
    private val ADDITIONAL_TYPE = 2
    private var data: MutableList<DataItemClasses> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            DELIMITER_TYPE -> {
                bindingDelimiter =
                ItemDelimiterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DelimiterViewHolder(bindingDelimiter)
            }
            CLASS_TYPE -> {
                bindingClasses =
                    ItemClassesRegularTimesortBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ClassesViewHolder(bindingClasses)
            }
            ADDITIONAL_TYPE -> {
                bindingAdditional =
                    ItemClassesAdditionalTimesortBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AdditionalViewHolder(bindingAdditional)
            }
            else -> throw ClassCastException("Wrong viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is DelimiterViewHolder -> holder.bind(data[position])
            is ClassesViewHolder -> holder.bind(data[position])
            is AdditionalViewHolder -> holder.bind(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is DataItemClasses.Delimiter -> DELIMITER_TYPE
            is DataItemClasses.Classes -> CLASS_TYPE
            is DataItemClasses.AdditionalClasses -> ADDITIONAL_TYPE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<DataItemClasses>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class DelimiterViewHolder(private val binding: ItemDelimiterBinding) :
        RecyclerView.ViewHolder(bindingDelimiter.root) {
        fun bind(data: DataItemClasses) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                if (data is DataItemClasses.Delimiter) {
                    binding.tvDate.text = "${data.startLesson}-${data.endLesson}"
                    binding.selectedPoint.isVisible = data.isActive == true
                    binding.notSelectedPoint.isVisible = data.isActive != true
                }
            }
        }
    }

    inner class ClassesViewHolder(private val binding: ItemClassesRegularTimesortBinding):
        RecyclerView.ViewHolder(bindingClasses.root){
        fun bind(data: DataItemClasses){
            if (layoutPosition != RecyclerView.NO_POSITION){
                if (data is DataItemClasses.Classes){
                    binding.tvClasses.text = data.subject
                    binding.tvTeacher.text = data.teacher
                    binding.grOpenin.isVisible = data.openSkype
                    imageLoader.loadInto(data.icon, binding.imageView)
                    if (data.openSkype) binding.cardView.setOnClickListener{
                        clickListener.onItemClick()
                    }
                }
            }
        }
    }

    inner class AdditionalViewHolder(private val binding: ItemClassesAdditionalTimesortBinding):
        RecyclerView.ViewHolder(bindingAdditional.root){
        fun bind(data: DataItemClasses){
            if (layoutPosition != RecyclerView.NO_POSITION){
                if (data is DataItemClasses.AdditionalClasses){
                    binding.tvClasses.text = data.subject
                    binding.tvTeacher.text = data.teacher
                    binding.tvInfo.text  = data.topic
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
