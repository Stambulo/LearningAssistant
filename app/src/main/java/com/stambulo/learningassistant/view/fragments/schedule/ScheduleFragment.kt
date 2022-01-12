package com.stambulo.learningassistant.view.fragments.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.stambulo.learningassistant.databinding.FragmentScheduleBinding
import com.stambulo.learningassistant.view.fragments.home.OnListItemClickListener
import com.stambulo.learningassistant.view.fragments.mvi.ClassesIntent
import com.stambulo.learningassistant.view.fragments.mvi.ClassesState
import com.stambulo.learningassistant.view.model.DataItemClasses
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScheduleFragment : Fragment() {
    private val viewModel: ScheduleViewModel by viewModels()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { ScheduleAdapter(clickListener) }
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupViewModel()
        observeViewModel()
    }

    private fun setupUI() { binding.rvClasses.adapter = adapter }

    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.classesIntent.send(ClassesIntent.FetchClasses)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is ClassesState.Idle -> {}
                    is ClassesState.Error -> { showError(it.error) }
                    is ClassesState.Loading -> { showLooading() }
                    is ClassesState.ClassesSuccess -> { showSuccess(it.classes) }
                }
            }
        }
    }

    private fun showSuccess(classes: List<DataItemClasses>){
        if (!classes.isNullOrEmpty()){
            binding.apply {
                progress.visibility = View.GONE
                tvDataAbsent.visibility = View.GONE
                rvClasses.visibility = View.VISIBLE
            }
            adapter.updateData(classes)
        } else {
            binding.apply {}
        }
        binding.rvClasses.scrollToPosition(viewModel.currentClass)
    }

    private fun showLooading(){
        binding.apply {
            progress.visibility = View.VISIBLE
            tvDataAbsent.visibility = View.GONE
            rvClasses.visibility = View.GONE
        }
    }

    private fun showError(error: String?) {
        binding.apply {
            progress.visibility = View.GONE
            tvDataAbsent.visibility = View.VISIBLE
            rvClasses.visibility = View.GONE
            Snackbar.make(root, error.toString(), Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val clickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick() {
                Toast.makeText(context, "Открываем Skype", Toast.LENGTH_SHORT).show()
            }
        }
}
