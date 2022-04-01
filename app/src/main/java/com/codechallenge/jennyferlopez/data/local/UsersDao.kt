package com.codechallenge.jennyferlopez.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codechallenge.jennyferlopez.data.entities.Users

@Dao
interface UsersDao {

    @Query("SELECT * FROM users")
    fun getAllUsers() : LiveData<List<Users>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: Int): LiveData<Users>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(users: Users)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<Users>)

}