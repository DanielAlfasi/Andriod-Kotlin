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
    suspend fun addChore(chore: Chore)

    @Delete
    suspend fun deleteChore(vararg chore: Chore)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateChore(chore: Chore)

    @Query("SELECT * from chores_table ORDER BY due_date ASC")
    fun getChores() : LiveData<List<Chore>>

    @Query("SELECT * from chores_table WHERE id = :choreId")
    suspend fun getChore(choreId: Int) : Chore

    @Query("SELECT COUNT(*) FROM chores_table WHERE user_in_charge_id = :userId")
    suspend fun countUserChores(userId: Int): Int

    @Query("UPDATE chores_table SET user_in_charge_id = :userId WHERE id = :choreId")
    suspend fun updateUserCharge(choreId: Int, userId: Int)

    @Query("UPDATE chores_table SET user_in_charge_id = -1 WHERE user_in_charge_id = :userId")
    suspend fun updateUserChargeUponUserRemoved(userId: Int)

    @Query("UPDATE chores_table SET status = :status WHERE id = :choreId")
    suspend fun updateChoreCompleted(choreId: Int, status: Boolean = true)

    @Query("SELECT SUM(reward) FROM chores_table WHERE user_in_charge_id = :userId AND status = :status")
    suspend fun sumUserChoresRewards(userId: Int, status : Boolean): Int

    @Query("DELETE from chores_table")
    suspend fun deleteAll()
}