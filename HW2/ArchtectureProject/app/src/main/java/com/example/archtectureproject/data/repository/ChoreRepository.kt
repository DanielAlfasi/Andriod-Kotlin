package com.example.archtectureproject.data.repository

import android.app.Application
import com.example.archtectureproject.data.local.ChoreDao
import com.example.archtectureproject.data.local.ChoresDatabase
import com.example.archtectureproject.data.model.Chore

class ChoreRepository(application: Application) {

    private var choreDao: ChoreDao?

    init {
        val db  = ChoresDatabase.getDatabase(application)
        choreDao = db.choreDao()
    }

    fun getChores() = choreDao?.getChores()

    fun addChore(chore: Chore) {
        choreDao?.addChore(chore)
    }

    fun deleteChore(chore: Chore) {
        choreDao?.deleteChore(chore)
    }

    fun deleteAll() {
        choreDao?.deleteAll()
    }

    fun sumUserChoresRewards(userId: Int, status: Boolean) : Int?
    {
        return choreDao?.sumUserChoresRewards(userId, status)
    }

    fun countUserChores(userId: Int) : Int?
    {
        return choreDao?.countUserChores(userId)
    }
}