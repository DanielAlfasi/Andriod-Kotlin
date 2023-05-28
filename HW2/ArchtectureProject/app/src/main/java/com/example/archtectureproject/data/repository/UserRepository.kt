package com.example.archtectureproject.data.repository

import android.app.Application
import com.example.archtectureproject.data.local.UserDao
import com.example.archtectureproject.data.local.ChoresDatabase
import com.example.archtectureproject.data.local.UsersDatabase
import com.example.archtectureproject.data.model.Chore
import com.example.archtectureproject.data.model.User

class UserRepository(application: Application) {

    private var userDao: UserDao?

    init {
        val db  = UsersDatabase.getDatabase(application)
        userDao = db.userDao()
    }

    fun getUser(userId: Int) = userDao?.getUser(userId)

    fun getFamily(familyId: Int) = userDao?.getFamily(familyId)

    fun addUser(user: User) {
        userDao?.addUser(user)
    }

    fun deleteChore(user: User) {
        userDao?.deleteUser(user)
    }

    fun deleteAll() {
        userDao?.deleteAll()
    }
}