package com.codechallenge.jennyferlopez.data.repository

import com.codechallenge.jennyferlopez.data.local.UsersDao
import com.codechallenge.jennyferlopez.data.remote.users.UserRemoteDataSource
import com.codechallenge.jennyferlopez.utils.performGetOperation
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UsersDao
    )  {

    fun getUsers() = performGetOperation(
        databaseQuery = { localDataSource.getAllUsers() },
        networkCall = { remoteDataSource.getUsers() },
        saveCallResult = { localDataSource.insertAll(it) }
    )

    fun getUser(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getUser(id) },
        networkCall = { remoteDataSource.getUser(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

}