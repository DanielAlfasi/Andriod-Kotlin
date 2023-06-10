package com.example.archtectureproject.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.archtectureproject.data.model.User
import com.example.archtectureproject.data.repository.ChoreRepository
import com.example.archtectureproject.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application)
    : AndroidViewModel(application) {


    private val userRepository = UserRepository(application)
    private val choreRepository = ChoreRepository(application)

    val users : LiveData<List<User>> = userRepository.getFamily(1)

    private val _chosenUser = MutableLiveData<User>()
    val chosenUser : LiveData<User> get() = _chosenUser

    fun setUser(user: User) {
        _chosenUser.value = user
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.addUser(user)
        }
    }

    fun getUser(userId:Int) : User
    {
        var userToReturn = User("No", "Users" , 1, "")
        users.value?.let { userList ->
            for (user in userList)
            {
                if (user.id == userId)
                {
                    userToReturn = user
                }
            }
        }
        return userToReturn
    }

    fun getUserById(userId: Int): LiveData<User>? {
        return users.let { users ->
            Transformations.map(users) { userList ->
                userList.firstOrNull { it.id == userId }
            }
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            choreRepository.updateUserChargeUponUserRemoved(user.id)
            userRepository.deleteUser(user)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            userRepository.deleteAll()
        }
    }

}