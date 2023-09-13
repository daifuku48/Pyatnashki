package com.haritdanabobataudasuda.pyatnashki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.haritdanabobataudasuda.pyatnashki.databinding.FragmentFinishBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FinishFragment : Fragment() {
    private var _binding : FragmentFinishBinding? = null
    private val binding
        get() = _binding!!
    private val vm : SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinishBinding.inflate(inflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.timer.text = vm.getTime()
        binding.textStart.setOnClickListener {
            vm.setTime("")
            findNavController().navigate(R.id.action_finishFragment_to_startFragment)
        }
    }
}