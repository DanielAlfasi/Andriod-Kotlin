package com.example.archtectureproject.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.archtectureproject.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase(){

    abstract fun userDao() : UserDao

    companion object {

        @Volatile
        private var instance: UsersDatabase? = null

        fun getDatabase(context: Context) = instance ?: synchronized(UsersDatabase::class.java) {
            Room.databaseBuilder(context.applicationContext,
                UsersDatabase::class.java,"users_database")
                .allowMainThreadQueries().build().also { instance = it }
        }
    }
}