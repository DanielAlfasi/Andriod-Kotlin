package com.example.archtectureproject.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.archtectureproject.data.model.Chore
import com.example.archtectureproject.data.model.User
import com.example.archtectureproject.data.model.UserWithChores
import com.example.archtectureproject.data.repository.ChoreRepository
import com.example.archtectureproject.data.repository.UserRepository


class UserAndChoreViewModel(application: Application)
    : AndroidViewModel(application){

    private val userRepository = UserRepository(application)
    private val choreRepository = ChoreRepository(application)

    private val chores : LiveData<List<Chore>> = choreRepository.getChores()
    private val users : LiveData<List<User>> = userRepository.getFamily(1)

    val userWithChores = MediatorLiveData<List<UserWithChores>>().apply {
        var usersData: List<User>? = null
        var choresData: List<Chore>? = null

        fun update() {
            if (usersData != null && choresData != null) {
                val userChoresData = usersData!!.map { user ->
                    UserWithChores(user).apply {
                        choresCount = choresData!!.count { chore -> chore.userId == user.id }
                        totalRewards = choresData!!.filter { chore -> chore.userId == user.id && chore.status }.sumBy { it.reward }
                    }
                }
                value = userChoresData
            }
        }

        addSource(users) { data ->
            usersData = data
            update()
        }

        addSource(chores) { data ->
            choresData = data
            update()
        }
    }
}

