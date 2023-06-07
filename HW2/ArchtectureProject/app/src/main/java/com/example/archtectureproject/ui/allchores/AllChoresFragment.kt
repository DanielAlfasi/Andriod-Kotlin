package com.example.archtectureproject.ui.allchores

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
import com.example.archtectureproject.ui.ChoreViewModel
import com.example.archtectureproject.R
import com.example.archtectureproject.databinding.AllChoresFragmentBinding

class AllChoresFragment : Fragment() {

    private var _binding : AllChoresFragmentBinding? = null
    private val binding get() = _binding!!
    private val choreViewModel : ChoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AllChoresFragmentBinding.inflate(inflater,container,false)

        arguments?.getString("title")?.let {
            Toast.makeText(requireActivity(),it,Toast.LENGTH_SHORT).show()
        }
        binding.flotaingAction.setOnClickListener {

           findNavController().navigate(R.id.action_allChoresFragment_to_addChoreFragment)

        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // handle menu bar
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.main_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.go_to_home -> {
                        findNavController().navigate(R.id.action_allChoresFragment_to_homeFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        choreViewModel.chores?.observe(viewLifecycleOwner) {

            binding.recycler.adapter = ChoreAdapter(it, object : ChoreAdapter.ChoreListener {
                override fun onChoreClicked(index: Int) {
                    choreViewModel.setChore(it[index])
                    findNavController().navigate(R.id.action_allChoresFragment_to_detailedChoreFragment)
                }

                override fun onChoreLongClick(index: Int) {
                    TODO("Not yet implemented")
                }
            })
        }
        binding.recycler.layoutManager = GridLayoutManager(requireContext(),1)


        ItemTouchHelper(object : ItemTouchHelper.Callback() {

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            )= makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                choreViewModel.deleteChore((binding.recycler.adapter as ChoreAdapter)
                    .choreAt(viewHolder.adapterPosition))
                binding.recycler.adapter!!.notifyItemRemoved(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(binding.recycler)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}