package com.example.archtectureproject.ui.allchores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.archtectureproject.data.model.Chore
import com.example.archtectureproject.databinding.ChoreLayoutBinding


class ChoreAdapter(val chores:List<Chore>, private val callback: ChoreListener) : RecyclerView.Adapter<ChoreAdapter.ChoreViewHolder>() {

    interface ChoreListener {
        fun onChoreClicked(index:Int)
        fun onChoreLongClick(index:Int)
    }

    inner class ChoreViewHolder(private val binding:ChoreLayoutBinding) :
        RecyclerView.ViewHolder(binding.root),View.OnClickListener,View.OnLongClickListener {

        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            callback.onChoreClicked(adapterPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            callback.onChoreLongClick(adapterPosition)
            return true
        }

        fun bind(chore: Chore) {
            binding.choreTitle.text = chore.title
            binding.choreDescription.text = chore.description
            binding.choreReward.text = chore.reward.toString()
            binding.choreDueDate.text = chore.date.toString()
        }
    }


    fun choreAt(position: Int) = chores[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ChoreViewHolder(ChoreLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ChoreViewHolder, position: Int) = holder.bind(chores[position])

    override fun getItemCount() = chores.size
}