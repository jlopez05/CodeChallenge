package com.codechallenge.jennyferlopez.data.remote.users

import com.codechallenge.jennyferlopez.data.entities.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users")
    suspend fun getAllUsers(): Response<List<Users>>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<Users>

}