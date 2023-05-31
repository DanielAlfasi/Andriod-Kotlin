package com.example.archtectureproject.ui.addchore


import android.app.DatePickerDialog
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
import java.util.*

class AddChoreFragment : Fragment() {

    private val viewModel : ChoreViewModel by activityViewModels()

    private var _binding : AddChoreLayoutBinding?  = null
    private val binding get() = _binding!!
    private var date: Long = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddChoreLayoutBinding.inflate(inflater,container,false)

        binding.pickDateBtn.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(requireContext(),
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
                calendar.get(Calendar.DAY_OF_MONTH))

            // set the minimum date to current date
            datePickerDialog.datePicker.minDate = calendar.timeInMillis

            datePickerDialog.show()

        }

        binding.finishBtn.setOnClickListener {
            val chore = Chore(binding.choreTitle.text.toString(), binding.choreDescription.text.toString(), binding.choreReward.text.toString().toInt(), date)

            viewModel.addChore(chore)

            findNavController().navigate(R.id.action_addItemFragment_to_HomeFragment)
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun dateBtnClicked() {
        binding.pickDateBtn
    }
}