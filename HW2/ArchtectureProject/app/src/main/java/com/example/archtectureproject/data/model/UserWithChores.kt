package com.example.archtectureproject.data.model

data class UserWithChores(
    val user: User,
    var choresCount: Int = 0,
    var totalRewards: Int = 0
)
