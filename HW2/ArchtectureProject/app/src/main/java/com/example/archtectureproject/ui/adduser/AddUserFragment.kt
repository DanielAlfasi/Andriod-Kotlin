package com.example.archtectureproject.ui.adduser


import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddUserLayoutBinding.inflate(inflater,container,false)

        binding.finishBtn.setOnClickListener {
            val user = User(binding.userFirstname.text.toString(), binding.userLastname.text.toString(), 1)

            viewModel.addUser(user)

            findNavController().navigate(R.id.action_addUserFragment_to_homeFragment)
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
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}