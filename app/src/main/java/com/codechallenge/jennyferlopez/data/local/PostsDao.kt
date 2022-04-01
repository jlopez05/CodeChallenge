package com.codechallenge.jennyferlopez.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.codechallenge.jennyferlopez.data.entities.Posts

@Dao
interface PostsDao {

    @Query("SELECT * FROM posts")
    fun getAllPosts() : LiveData<List<Posts>>

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPost(id: Int): LiveData<Posts>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(posts: Posts)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<Posts>)

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(Post: Posts)

}