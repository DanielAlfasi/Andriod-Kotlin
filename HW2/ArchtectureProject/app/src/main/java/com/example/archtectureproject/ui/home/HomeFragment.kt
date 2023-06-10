package com.example.archtectureproject.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.archtectureproject.R
import com.example.archtectureproject.databinding.HomeLayoutBinding

class HomeFragment : Fragment() {

    private var _binding: HomeLayoutBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = HomeLayoutBinding.inflate(inflater,container,false)

        arguments?.getString("title")?.let {
            Toast.makeText(requireActivity(),it, Toast.LENGTH_SHORT).show()
        }
        binding.choresListButton.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_allChoresFragment)

        }

        binding.choresAddButton.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_addChoreFragment)

        }

        binding.familyMembersButton.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_allFamilyMembersFragment)

        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}