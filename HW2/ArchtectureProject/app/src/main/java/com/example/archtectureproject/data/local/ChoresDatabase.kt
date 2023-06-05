package com.example.archtectureproject.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.archtectureproject.data.model.Chore
import com.example.archtectureproject.data.model.Converters

@Database(entities = [Chore::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ChoresDatabase : RoomDatabase(){

    abstract fun choreDao() : ChoreDao

    companion object {

        @Volatile
        private var instance: ChoresDatabase? = null

        fun getDatabase(context: Context) = instance ?: synchronized(ChoresDatabase::class.java) {
            Room.databaseBuilder(context.applicationContext,
                ChoresDatabase::class.java,"chores_database").build().also { instance = it }
        }
    }
}