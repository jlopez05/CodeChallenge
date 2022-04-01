package com.codechallenge.jennyferlopez.data.remote.users

import com.codechallenge.jennyferlopez.data.remote.BaseDataSource
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
): BaseDataSource() {

    suspend fun getUsers() = getResult { userService.getAllUsers() }
    suspend fun getUser(id: Int) = getResult { userService.getUser(id) }

}