package com.example.archtectureproject.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.archtectureproject.R
import com.example.archtectureproject.databinding.AllChoresFragmentBinding
import com.example.archtectureproject.databinding.HomeLayoutBinding
import com.example.archtectureproject.ui.ChoreViewModel
import com.example.archtectureproject.ui.allchores.ChoreAdapter

class HomeFragment : Fragment() {

    private var _binding: HomeLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel : ChoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeLayoutBinding.inflate(inflater,container,false)

        arguments?.getString("title")?.let {
            Toast.makeText(requireActivity(),it, Toast.LENGTH_SHORT).show()
        }
        binding.choresListButton.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_allItemsFragment)

        }

        binding.choresAddButton.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_addItemFragment)

        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}