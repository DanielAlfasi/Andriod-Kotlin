package com.example.archtectureproject.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.archtectureproject.data.model.Chore
import com.example.archtectureproject.data.repository.ChoreRepository
import kotlinx.coroutines.launch

class ChoreViewModel(application: Application)
    : AndroidViewModel(application) {


    private val repository = ChoreRepository(application)

    val chores : LiveData<List<Chore>> = repository.getChores()

    private val _chosenChore = MutableLiveData<Chore>()
    val chosenChore : LiveData<Chore> get() = _chosenChore


    fun setChore(chore: Chore) {
        _chosenChore.value = chore
    }

    fun addChore(chore: Chore) {
        viewModelScope.launch {
            repository.addChore(chore)
        }
    }

    fun deleteChore(chore: Chore) {
        viewModelScope.launch {
            repository.deleteChore(chore)
        }
    }

    fun updateUserCharge(choreId: Int, userId: Int) {
        viewModelScope.launch {
            repository.updateUserCharge(choreId, userId)
        }
    }

    fun updateChoreCompleted(choreId: Int, status: Boolean = true) {
        viewModelScope.launch {
            repository.updateChoreCompleted(choreId, status)
        }
    }


}