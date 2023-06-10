package com.example.archtectureproject.ui.adduser


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.archtectureproject.R
import com.example.archtectureproject.data.model.User
import com.example.archtectureproject.databinding.AddUserLayoutBinding
import com.example.archtectureproject.ui.UserViewModel

class AddUserFragment : Fragment() {

    private val viewModel : UserViewModel by activityViewModels()

    private var _binding : AddUserLayoutBinding?  = null
    private val binding get() = _binding!!
    private var imageUri : Uri? = null

    private val pickItemLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            binding.resultImage.setImageURI(it)
            requireActivity().contentResolver.takePersistableUriPermission(it!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            imageUri = it
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // handle menu bar
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear() // Clear the menu first
                // Add menu items here
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.go_to_home -> {
                        findNavController().navigate(R.id.action_addUserFragment_to_homeFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}