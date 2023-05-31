package com.example.archtectureproject.ui.allfamilymembers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.archtectureproject.data.model.Chore
import com.example.archtectureproject.data.model.User
import com.example.archtectureproject.data.repository.ChoreRepository
import com.example.archtectureproject.data.repository.UserRepository
import com.example.archtectureproject.databinding.ChoreLayoutBinding
import com.example.archtectureproject.databinding.UserLayoutBinding
import com.example.archtectureproject.ui.ChoreViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserAdapter(val users:List<User> , private val choreViewModel: ChoreViewModel ,private val callback: UserListener) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    interface UserListener {
        fun onUserClicked(index:Int)
        fun onUserLongClick(index:Int)
    }

    inner class UserViewHolder(private val binding:UserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root),View.OnClickListener,View.OnLongClickListener {

        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            callback.onUserClicked(adapterPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            callback.onUserLongClick(adapterPosition)
            return true
        }

        fun bind(user: User) {
            binding.userName.text = "${user.firstName} ${user.lastName}"
            binding.userChores.text = "Chores : ${choreViewModel.countUserChores(user.id)}"
            binding.userNumOfCoins.text = "Coins : ${choreViewModel.sumUserChoresRewards(user.id, true)}"
        }
    }


    fun userAt(position: Int) = users[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(UserLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(users[position])

    override fun getItemCount() = users.size
}