package com.example.archtectureproject.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.archtectureproject.data.model.Chore
import com.example.archtectureproject.data.model.User
import com.example.archtectureproject.data.repository.ChoreRepository
import com.example.archtectureproject.data.repository.UserRepository

class UserViewModel(application: Application)
    : AndroidViewModel(application) {


    private val repository = UserRepository(application)

    val users : LiveData<List<User>>? = repository.getFamily(1)

    private val _chosenUser = MutableLiveData<User>()
    val chosenUser : LiveData<User> get() = _chosenUser

    fun setUser(user: User) {
        _chosenUser.value = user
    }

    fun addUser(user: User) {
        repository.addUser(user)
    }

    fun deleteUser(user: User) {
        repository.deleteUser(user)
    }

    fun deleteAll() {
        repository.deleteAll()
    }

}