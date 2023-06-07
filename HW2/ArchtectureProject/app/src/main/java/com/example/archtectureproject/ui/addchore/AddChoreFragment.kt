package com.example.archtectureproject.ui.addchore


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.archtectureproject.ui.ChoreViewModel
import com.example.archtectureproject.R
import com.example.archtectureproject.data.model.Chore
import com.example.archtectureproject.databinding.AddChoreLayoutBinding
import com.example.archtectureproject.ui.UserViewModel
import java.util.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.archtectureproject.data.model.User

class AddChoreFragment : Fragment() {

    private val viewModel: ChoreViewModel by activityViewModels()

    private var _binding: AddChoreLayoutBinding? = null
    private val binding get() = _binding!!
    private var date: Long = 0L
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var userSpinner: Spinner


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = AddChoreLayoutBinding.inflate(inflater, container, false)

        // handle date pick
        binding.pickDateBtn.setOnClickListener {
            dateBtnClicked()
        }
//
//        userSpinner =  binding.userPickSpinner
//
//        val allUsers = userViewModel.users
//
//        allUsers?.observe(viewLifecycleOwner, Observer { userList ->
//            allUsers.value
//            val userNames = userList.map { "${it.firstName} ${it.lastName}" }
//            val arrayAdapter = ArrayAdapter(
//                requireContext(),
//                android.R.layout.simple_spinner_item,
//                userNames
//            )
//            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            userSpinner.adapter = arrayAdapter
//
//        })
//
//        userSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val selectedUser = allUsers!![position]
//                val userPicked = selectedUser
//            }

        // handle finish button
        binding.finishBtn.setOnClickListener {
            finishBtnClicked()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // handle menu bar
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear() // Clear the menu first
                // Add menu items here
                menuInflater.inflate(R.menu.main_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.go_to_home -> {
                        findNavController().navigate(R.id.action_addChoreFragment_to_homeFragment)
                        true
                    }
                    else -> false
                }
            }
        })

    }


    // handle finish button function
    private fun finishBtnClicked() {

        if (date == 0L) {
            val builder = AlertDialog.Builder(requireContext())

            builder.setTitle(getString(R.string.date_not_picked_title).toString())
            builder.setMessage(getString(R.string.date_not_picked_desc).toString())
            builder.setPositiveButton(getString(R.string.date_not_picked_ok).toString()) { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = builder.create()
            alertDialog.show()

        }
        else if (binding.choreTitle.text.isNullOrBlank() and binding.choreReward.text.isNullOrBlank()) {
            binding.choreTitle.error = getString(R.string.field_not_null_error).toString()
            binding.choreReward.error = getString(R.string.field_not_null_error).toString()
        }
        else if (binding.choreTitle.text.isNullOrBlank()) {
            binding.choreTitle.error = getString(R.string.field_not_null_error).toString()
        }
        else if (binding.choreReward.text.isNullOrBlank()) {
            binding.choreReward.error = getString(R.string.field_not_null_error).toString()
        }
        else {
            val chore = Chore(
                binding.choreTitle.text.toString(),
                binding.choreDescription.text.toString(),
                binding.choreReward.text.toString().toInt(),
                date
            )

            viewModel.addChore(chore)

            findNavController().navigate(R.id.action_addChoreFragment_to_homeFragment)
        }

    }

    // handle date pick button
    private fun dateBtnClicked() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                // Create a Calendar object with the picked date
                val pickedDate = Calendar.getInstance().apply {
                    set(year, monthOfYear, dayOfMonth)
                }

                // Convert the picked date to a Date object
                date = pickedDate.timeInMillis
                pickedDate.set(Calendar.HOUR_OF_DAY, 0)
                pickedDate.set(Calendar.MINUTE, 0)
                pickedDate.set(Calendar.SECOND, 0)
                pickedDate.set(Calendar.MILLISECOND, 0)

                // show the picked date on screen
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                binding.dateText.text = selectedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // set the minimum date to current date
        datePickerDialog.datePicker.minDate = calendar.timeInMillis

        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}