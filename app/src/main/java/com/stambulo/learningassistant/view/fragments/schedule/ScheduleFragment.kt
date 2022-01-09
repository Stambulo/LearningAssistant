package com.stambulo.learningassistant.view.fragments.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stambulo.learningassistant.R
import com.stambulo.learningassistant.databinding.FragmentScheduleBinding
import com.stambulo.learningassistant.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment: Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return _binding?.root
    }
}
