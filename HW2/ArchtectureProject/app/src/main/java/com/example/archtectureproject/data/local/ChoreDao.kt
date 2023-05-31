package com.example.archtectureproject.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.archtectureproject.data.model.Chore

@Dao
interface ChoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addChore(chore: Chore)

    @Delete
    fun deleteChore(vararg chore: Chore)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateChore(chore: Chore)

    @Query("SELECT * from chores_table ORDER BY due_date ASC")
    fun getChores() : LiveData<List<Chore>>

    @Query("SELECT * from chores_table WHERE id = :choreId")
    fun getChore(choreId: Int) : Chore

    @Query("SELECT COUNT(*) FROM chores_table WHERE user_in_charge_id = :userId")
    fun countUserChores(userId: Int): Int

    @Query("SELECT SUM(reward) FROM chores_table WHERE user_in_charge_id = :userId AND status = :status")
    fun sumUserChoresRewards(userId: Int, status : Boolean): Int

    @Query("DELETE from chores_table")
    fun deleteAll()
}