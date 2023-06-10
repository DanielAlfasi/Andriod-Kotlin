package com.example.archtectureproject.ui.allfamilymembers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.archtectureproject.R
import com.example.archtectureproject.data.model.UserWithChores
import com.example.archtectureproject.databinding.UserLayoutBinding



class UserAdapter(private val users:List<UserWithChores>, private val callback: UserListener) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
// Remove view models and change arg to a list that suits the adapter
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

        @SuppressLint("SetTextI18n")
        fun bind(userWithChore: UserWithChores) {
            binding.userName.text = "${userWithChore.user.firstName} ${userWithChore.user.lastName}"
            binding.userChores.text = "${binding.root.context.getString(R.string.number_of_chores_title)} ${userWithChore.choresCount}"
            binding.userNumOfCoins.text = "${binding.root.context.getString(R.string.reward_title)} ${userWithChore.totalRewards}"
            if (userWithChore.user.profileImg != "null") {
                Glide.with(binding.root).load(userWithChore.user.profileImg).circleCrop()
                    .into(binding.itemImage)
            } else {
                Glide.with(binding.root).load(R.drawable.user_img_final).circleCrop()
                    .into(binding.itemImage)
            }
        }
    }

    fun userAt(position: Int) = users[position].user

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(UserLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(users[position])

    override fun getItemCount() = users.size
}