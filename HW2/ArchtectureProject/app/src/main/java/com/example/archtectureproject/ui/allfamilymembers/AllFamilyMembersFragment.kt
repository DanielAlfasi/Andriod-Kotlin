package com.example.archtectureproject.ui.allfamilymembers

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
import com.example.archtectureproject.databinding.AllFamilyMembersFragmentBinding
import com.example.archtectureproject.ui.ChoreViewModel
import com.example.archtectureproject.ui.UserAndChoreViewModel
import com.example.archtectureproject.ui.UserViewModel

class AllFamilyMembersFragment : Fragment() {

    private var _binding:AllFamilyMembersFragmentBinding? = null
    private val binding get() = _binding!!

    private val userViewModel : UserViewModel by activityViewModels()
    private val choreViewModel : ChoreViewModel by activityViewModels()
    private val userAndChoreViewModel : UserAndChoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AllFamilyMembersFragmentBinding.inflate(inflater,container,false)

        binding.flotaingAction.setOnClickListener {

           findNavController().navigate(R.id.action_allFamilyMembersFragment_to_addUserFragment)

        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                        findNavController().navigate(R.id.action_allFamilyMembersFragment_to_homeFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        userAndChoreViewModel.userWithChores?.observe(viewLifecycleOwner) {

            binding.recycler.adapter = UserAdapter(it ,object : UserAdapter.UserListener {
                override fun onUserClicked(index: Int) {
                    Toast.makeText(requireContext(),it[index].toString(),Toast.LENGTH_LONG).show()
                }

                override fun onUserLongClick(index: Int) {
                    // do nothing
                }
            })
        }
        binding.recycler.layoutManager = GridLayoutManager(requireContext(),1)


//        ItemTouchHelper(object : ItemTouchHelper.Callback() {
//
//            override fun getMovementFlags(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder
//            )= makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
//
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                TODO("Not yet implemented")
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                userViewModel.deleteUser((binding.recycler.adapter as UserAdapter)
//                    .userAt(viewHolder.adapterPosition))
//                binding.recycler.adapter!!.notifyItemRemoved(viewHolder.adapterPosition)
//            }
//        }).attachToRecyclerView(binding.recycler)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}