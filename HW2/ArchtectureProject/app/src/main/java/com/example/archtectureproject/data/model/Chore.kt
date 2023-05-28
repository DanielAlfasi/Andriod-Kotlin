package com.example.archtectureproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "chores_table")
data class Chore(

    @ColumnInfo(name = "title")
    val title:String,

    @ColumnInfo(name = "description")
    val description:String,

    @ColumnInfo(name = "reward")
    val reward: Int,

    // Change to Date type
    @ColumnInfo(name = "due_date")
    val date: Int,

    @ColumnInfo(name = "user_in_charge_id")
    val userId: Int = -1,

    @ColumnInfo(name = "family_id")
    val familyId: Int = -1,

    @ColumnInfo(name = "status")
    val status: Boolean = false)

{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}


