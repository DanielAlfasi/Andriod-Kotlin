package com.example.archtectureproject.ui.detailedchore

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.archtectureproject.R
import com.example.archtectureproject.data.utils.autoCleared
import com.example.archtectureproject.databinding.DetailedChoreLayoutBinding
import com.example.archtectureproject.ui.ChoreViewModel
import com.example.archtectureproject.data.model.User
import com.example.archtectureproject.data.repository.UserRepository
import com.example.archtectureproject.ui.UserViewModel

class DetailedChoreFragment : Fragment() {

    private  var binding: DetailedChoreLayoutBinding by autoCleared()
    private val choreViewModel : ChoreViewModel by activityViewModels()
    private val userViewModel : UserViewModel by activityViewModels()
    private lateinit var userRepository: UserRepository
    private lateinit var userSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailedChoreLayoutBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userSpinner = binding.userSpinner

        userRepository = UserRepository(requireActivity().application)

        choreViewModel.chosenChore.observe(viewLifecycleOwner) {
            binding.choreTitle.text = it.title
            binding.choreDescription.text = it.description
            binding.choreReward.text = "${getString(R.string.reward_title).toString()} ${it.reward.toString()}"
            binding.choreDueDate.text = "${getString(R.string.due_title).toString()} ${it.date.toString()}"

            if (it.userId == -1) {
                binding.assignedTo.text = getString(R.string.not_assigned).toString()
            }
            else {
                val user = userRepository.getUser(it.userId)
                binding.assignedTo.text = "${user?.firstName ?: ""} ${user?.lastName ?: ""}"
            }

            val allUsers = userViewModel.users

            allUsers?.observe(viewLifecycleOwner, Observer { userList ->
                val userNames = userList.map { "${it.firstName} ${it.lastName}" }
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    userNames
                )
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                userSpinner.adapter = arrayAdapter
            })
        }

        }

    }
