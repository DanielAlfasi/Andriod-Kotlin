package com.example.archtectureproject.ui.allchores

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.archtectureproject.R
import com.example.archtectureproject.data.model.Chore
import com.example.archtectureproject.databinding.ChoreLayoutBinding
import java.text.SimpleDateFormat
import java.util.*


class ChoreAdapter(private val chores:List<Chore>, private val callback: ChoreListener) : RecyclerView.Adapter<ChoreAdapter.ChoreViewHolder>() {

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

        @SuppressLint("SetTextI18n")
        fun bind(chore: Chore) {
            binding.choreTitle.text = chore.title
            // handle Date
            val date = Date(chore.date)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateString = dateFormat.format(date)
            binding.choreDueDate.text = "${itemView.context.getString(R.string.due_title)} $dateString"
        }
    }


    fun choreAt(position: Int) = chores[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ChoreViewHolder(ChoreLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ChoreViewHolder, position: Int) = holder.bind(chores[position])

    override fun getItemCount() = chores.size
}