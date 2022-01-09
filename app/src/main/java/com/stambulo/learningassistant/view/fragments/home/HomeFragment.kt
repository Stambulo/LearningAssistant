package com.stambulo.learningassistant.view.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.stambulo.learningassistant.databinding.FragmentHomeBinding
import com.stambulo.learningassistant.model.Homework
import com.stambulo.learningassistant.view.model.DataItemClasses
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val classesAdapter by lazy(LazyThreadSafetyMode.NONE) { ClassesAdapter(clickListener) }
    private val homeworkAdapter by lazy(LazyThreadSafetyMode.NONE) { HomeworkAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()
        observeViewModel()
        setupUI()
    }

    private fun setupViewModels() {
        lifecycleScope.launch {
            viewModel.classesIntent.send(ClassesIntent.FetchClasses)
            viewModel.homeworkIntent.send(HomeworkIntent.FetchHomework)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is ClassesState.Idle -> {}
                    is ClassesState.Error -> {showClassesError(it)}
                    is ClassesState.Loading -> {showClassesLoading()}
                    is ClassesState.ClassesSuccess -> {renderClasses(it.classes)}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.homeworkState.collect {
                when (it) {
                    is HomeworkState.Idle -> {}
                    is HomeworkState.Error -> {showHomeworkError(it)}
                    is HomeworkState.Loading -> {showHomeworkLoading()}
                    is HomeworkState.HomeworkSuccess -> {renderHomework(it.homework)}
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderClasses(classes: List<DataItemClasses>) {
        if (!classes.isNullOrEmpty()) {
            classes.let {
                binding.progressBar.visibility = View.GONE
                binding.tvNoData.visibility = View.GONE
                binding.tvClasses.text = it.size.toString()
                binding.rvClasses.visibility = View.VISIBLE
                binding.rvClasses.scrollToPosition(viewModel.currentClass)
                classesAdapter.update(it)
                classesAdapter.notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderHomework(homework: List<Homework>) {
        if (!homework.isNullOrEmpty()) {
            homework.let {
                binding.progressBarHomework.visibility = View.GONE
                binding.tvNoDataHomework.visibility = View.GONE
                binding.rvHomework.visibility = View.VISIBLE
                homeworkAdapter.updateData(it)
                homeworkAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun showClassesLoading(){
        binding.apply {
            progressBar.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE
        }
    }

    private fun showHomeworkLoading(){
        binding.apply {
            progressBarHomework.visibility = View.VISIBLE
            tvNoDataHomework.visibility = View.GONE
        }
    }

    private fun showClassesError(result: ClassesState.Error){
        binding.apply {
            progressBar.visibility = View.GONE
            tvNoData.visibility = View.VISIBLE
            Snackbar.make(root, result.error ?: "", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun showHomeworkError(error: HomeworkState.Error){
        binding.apply {
            progressBarHomework.visibility = View.GONE
            tvNoDataHomework.visibility = View.VISIBLE
        }
    }

    private fun setupUI() {
        binding.rvClasses.adapter = classesAdapter
        binding.rvHomework.adapter = homeworkAdapter
    }

    private val clickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick() {
                Toast.makeText(context, "Открываем Skype", Toast.LENGTH_SHORT).show()
            }
        }
}
