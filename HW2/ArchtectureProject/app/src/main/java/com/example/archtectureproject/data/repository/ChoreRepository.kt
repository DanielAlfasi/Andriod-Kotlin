package com.example.archtectureproject.data.repository

import android.app.Application
import com.example.archtectureproject.data.local.ChoreDao
import com.example.archtectureproject.data.local.ChoresDatabase
import com.example.archtectureproject.data.model.Chore

class ChoreRepository(application: Application) {

    private var choreDao: ChoreDao

    init {
        val db  = ChoresDatabase.getDatabase(application)
        choreDao = db.choreDao()
    }

    fun getChores() = choreDao.getChores()

    suspend fun addChore(chore: Chore) {
        choreDao.addChore(chore)
    }

    suspend fun deleteChore(chore: Chore) {
        choreDao.deleteChore(chore)
    }

    suspend fun deleteAll() {
        choreDao.deleteAll()
    }

    suspend fun updateUserCharge(choreId: Int, userId: Int) {
        choreDao.updateUserCharge(choreId, userId)
    }

    suspend fun updateChoreCompleted(choreId: Int, status: Boolean = true) {
        choreDao.updateChoreCompleted(choreId, status)
    }
}