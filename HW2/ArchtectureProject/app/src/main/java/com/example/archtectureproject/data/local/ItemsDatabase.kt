package com.example.archtectureproject.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.archtectureproject.data.model.Chore

@Database(entities = arrayOf(Chore::class), version = 1, exportSchema = false)
abstract class ChoresDatabase : RoomDatabase(){

    abstract fun choreDao() : ChoreDao

    companion object {

        @Volatile
        private var instance: ChoresDatabase? = null

        fun getDatabase(context: Context) = instance ?: synchronized(ChoresDatabase::class.java) {
            Room.databaseBuilder(context.applicationContext,
                ChoresDatabase::class.java,"items_database")
                .allowMainThreadQueries().build().also { instance = it }
        }
    }
}