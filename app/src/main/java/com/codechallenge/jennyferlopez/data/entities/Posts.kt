package com.codechallenge.jennyferlopez.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Posts(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    var isRead: Boolean = false
)