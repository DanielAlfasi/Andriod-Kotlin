package com.example.archtectureproject.ui.adduser


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.archtectureproject.ui.ChoreViewModel
import com.example.archtectureproject.R
import com.example.archtectureproject.data.model.Chore
import com.example.archtectureproject.data.model.User
import com.example.archtectureproject.databinding.AddChoreLayoutBinding
import com.example.archtectureproject.databinding.AddUserLayoutBinding
import com.example.archtectureproject.ui.UserViewModel

class AddUserFragment : Fragment() {

    private val viewModel : UserViewModel by activityViewModels()

    private var _binding : AddUserLayoutBinding?  = null
    private val binding get() = _binding!!
    private var imageUri : Uri? = null

    val pickItemLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            binding.resultImage.setImageURI(it)
            requireActivity().contentResolver.takePersistableUriPermission(it!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            imageUri = it
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddUserLayoutBinding.inflate(inflater,container,false)

        binding.finishBtn.setOnClickListener {
            val user = User(binding.userFirstname.text.toString(), binding.userLastname.text.toString(), 1, imageUri.toString())

            viewModel.addUser(user)

            findNavController().navigate(R.id.action_addUserFragment_to_allFamilyMembersFragment)
        }
        binding.imageBtn.setOnClickListener {
            pickItemLauncher.launch(arrayOf("image/*"))
        }
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}