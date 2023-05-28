package com.example.archtectureproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users_table")
data class User(

    @ColumnInfo(name = "first_name")
    val firstName:String,

    @ColumnInfo(name = "last_name")
    val lastName:String,

    @ColumnInfo(name = "family_id")
    val familyId: Int = 1)

{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}


