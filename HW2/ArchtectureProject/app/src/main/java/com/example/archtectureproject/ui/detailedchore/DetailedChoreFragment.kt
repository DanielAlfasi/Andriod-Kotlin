package com.example.archtectureproject.ui.detailedchore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.archtectureproject.data.utils.autoCleared
import com.example.archtectureproject.databinding.DetailedChoreLayoutBinding
import com.example.archtectureproject.ui.ChoreViewModel

class DetailedChoreFragment : Fragment() {

    private  var binding: DetailedChoreLayoutBinding by autoCleared()
    private val viewModel : ChoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailedChoreLayoutBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.chosenChore.observe(viewLifecycleOwner) {
            binding.choreTitle.text = it.title
            binding.choreDescription.text = it.description
            binding.choreReward.text = it.reward.toString()
            binding.choreDueDate.text = it.date.toString()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}