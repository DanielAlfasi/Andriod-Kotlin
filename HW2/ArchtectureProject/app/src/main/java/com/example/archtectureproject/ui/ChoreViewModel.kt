package com.example.archtectureproject.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.archtectureproject.data.model.Chore
import com.example.archtectureproject.data.repository.ChoreRepository

class ChoreViewModel(application: Application)
    : AndroidViewModel(application) {


    private val repository = ChoreRepository(application)

    val chores : LiveData<List<Chore>>? = repository.getChores()

    private val _chosenChore = MutableLiveData<Chore>()
    val chosenChore : LiveData<Chore> get() = _chosenChore


    fun setChore(chore: Chore) {
        _chosenChore.value = chore
    }

    fun countUserChores(userId: Int) : Int?
    {
        return repository.countUserChores(userId)
    }

    fun sumUserChoresRewards(userId: Int, status : Boolean) : Int?
    {
        return repository.sumUserChoresRewards(userId, status)
    }

    fun addChore(chore: Chore) {
        repository.addChore(chore)
    }

    fun deleteChore(chore: Chore) {
        repository.deleteChore(chore)
    }

    fun deleteAll() {
        repository.deleteAll()
    }

}