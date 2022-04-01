package com.codechallenge.jennyferlopez.data.repository

import com.codechallenge.jennyferlopez.data.entities.Posts
import com.codechallenge.jennyferlopez.data.local.PostsDao
import com.codechallenge.jennyferlopez.data.remote.posts.PostRemoteDataSource
import com.codechallenge.jennyferlopez.utils.performGetOperation
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val remoteDataSource: PostRemoteDataSource,
    private val localDataSource: PostsDao
) {

    fun getPost(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getPost(id) },
        networkCall = { remoteDataSource.getPost(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getPosts() = performGetOperation(
        databaseQuery = { localDataSource.getAllPosts() },
        networkCall = { remoteDataSource.getPosts() },
        saveCallResult = { localDataSource.insertAll(it) }
    )

    fun changeIsRead(post: Posts) {
        localDataSource.update(post)
    }

}