package com.example.archtectureproject.ui.detailedchore

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.archtectureproject.R
import com.example.archtectureproject.data.utils.autoCleared
import com.example.archtectureproject.databinding.DetailedChoreLayoutBinding
import com.example.archtectureproject.ui.ChoreViewModel
import com.example.archtectureproject.data.model.User
import com.example.archtectureproject.data.repository.UserRepository
import com.example.archtectureproject.ui.UserViewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailedChoreFragment : Fragment() {

    private  var binding: DetailedChoreLayoutBinding by autoCleared()
    private val choreViewModel : ChoreViewModel by activityViewModels()
    private val userViewModel : UserViewModel by activityViewModels()
    private lateinit var userSpinner: Spinner
    private var selectedUserId = -1

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

        // ask marko about this!!
        // handle menu bar
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                //menu.clear() // Clear the menu first
                // Add menu items here
                menuInflater.inflate(R.menu.main_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.go_to_home -> {
                        findNavController().navigate(R.id.action_detailedChoreFragment_to_homeFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        userSpinner = binding.userSpinner

        val allUsers = userViewModel.users
        val userVM = userViewModel

        choreViewModel.chosenChore.observe(viewLifecycleOwner) {
            binding.choreTitle.text = it.title
            binding.choreDescription.text = it.description
            binding.choreReward.text = "${getString(R.string.reward_title).toString()} ${it.reward.toString()}"

            // handle Date
            val date = Date(it.date)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateString = dateFormat.format(date)
            binding.choreDueDate.text = "${getString(R.string.due_title).toString()} $dateString"

            // handle user assigned to chore
            if (it.userId == -1) {
                binding.assignedTo.text = getString(R.string.not_assigned).toString()
            }
            else {
                val user = userVM.getUser(it.userId)
                binding.assignedTo.text = "${user?.firstName ?: ""} ${user?.lastName ?: ""}"
            }



            allUsers?.observe(viewLifecycleOwner, Observer { userList ->
                allUsers.value
                val userNames = userList.map { "${it.firstName} ${it.lastName}" }
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    userNames
                )
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                userSpinner.adapter = arrayAdapter
                handleUserPick(allUsers.value)
                binding.applyUserChange.setOnClickListener {
                    completeUserPick()
                }
            })

            // handle chore completion
            if (choreViewModel.chosenChore.value?.status!!) {
                binding.completeButton.text = getString(R.string.chore_completed)
                binding.completeButton.isEnabled = false
                binding.applyUserChange.isEnabled = false
            }

            binding.completeButton.setOnClickListener {
                completeChore()
            }
        }

        }

    private fun handleUserPick(userList: List<User>?) {
        userSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedUser = userList!![position]
                selectedUserId = selectedUser.id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun completeUserPick(){
        choreViewModel.updateUserCharge(choreViewModel.chosenChore.value!!.id, selectedUserId)
        val user = userViewModel.getUser(selectedUserId)
        binding.assignedTo.text = "${user?.firstName ?: ""} ${user?.lastName ?: ""}"
    }

    private fun completeChore() {
        binding.completeButton.text = getString(R.string.chore_completed)
        binding.completeButton.isEnabled = false
        choreViewModel.updateChoreCompleted(choreViewModel.chosenChore.value!!.id)
        binding.applyUserChange.isEnabled = false

    }

    }
