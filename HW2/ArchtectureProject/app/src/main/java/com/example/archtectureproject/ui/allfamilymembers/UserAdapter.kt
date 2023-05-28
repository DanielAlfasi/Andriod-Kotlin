package com.example.archtectureproject.ui.allfamilymembers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.archtectureproject.data.model.Chore
import com.example.archtectureproject.data.model.User
import com.example.archtectureproject.databinding.ChoreLayoutBinding
import com.example.archtectureproject.databinding.UserLayoutBinding


class UserAdapter(val users:List<User>, private val callback: UserListener) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

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
            binding.userFirstname.text = user.firstName
            binding.userLastname.text = user.lastName
            binding.userNumOfCoins.text = "To be implemented"
        }
    }


    fun userAt(position: Int) = users[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(UserLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(users[position])

    override fun getItemCount() = users.size
}