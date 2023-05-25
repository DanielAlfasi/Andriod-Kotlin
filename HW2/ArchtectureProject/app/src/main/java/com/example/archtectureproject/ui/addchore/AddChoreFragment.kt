package com.example.archtectureproject.ui.addchore


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.archtectureproject.ui.ChoreViewModel
import com.example.archtectureproject.R
import com.example.archtectureproject.data.model.Chore
import com.example.archtectureproject.databinding.AddChoreLayoutBinding

class AddChoreFragment : Fragment() {

    private val viewModel : ChoreViewModel by activityViewModels()

    private var _binding : AddChoreLayoutBinding?  = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddChoreLayoutBinding.inflate(inflater,container,false)

        binding.finishBtn.setOnClickListener {
            val chore = Chore(binding.choreTitle.text.toString(), binding.choreDescription.text.toString(), binding.choreReward.text.toString().toInt(), binding.choreDueDate.toString().toInt())

            viewModel.addChore(chore)

            findNavController().navigate(R.id.action_addItemFragment_to_HomeFragment)
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}