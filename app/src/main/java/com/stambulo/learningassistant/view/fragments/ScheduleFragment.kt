package com.stambulo.learningassistant.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stambulo.learningassistant.R
import com.stambulo.learningassistant.databinding.FragmentScheduleBinding
import com.stambulo.learningassistant.view.MainActivity

class ScheduleFragment: Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnBack.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_scheduleFragment_to_homeFragment)
        }
    }
}
