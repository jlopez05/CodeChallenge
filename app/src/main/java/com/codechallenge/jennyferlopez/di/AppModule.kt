package com.codechallenge.jennyferlopez.di

import android.content.Context
import com.codechallenge.jennyferlopez.data.local.AppDatabase
import com.codechallenge.jennyferlopez.data.local.PostsDao
import com.codechallenge.jennyferlopez.data.local.UsersDao
import com.codechallenge.jennyferlopez.data.remote.posts.PostRemoteDataSource
import com.codechallenge.jennyferlopez.data.remote.posts.PostService
import com.codechallenge.jennyferlopez.data.remote.users.UserRemoteDataSource
import com.codechallenge.jennyferlopez.data.remote.users.UserService
import com.codechallenge.jennyferlopez.data.repository.PostsRepository
import com.codechallenge.jennyferlopez.data.repository.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun providePostService(retrofit: Retrofit): PostService = retrofit.create(PostService::class.java)

    @Singleton
    @Provides
    fun providePostRemoteDataSource(postService: PostService) = PostRemoteDataSource(postService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun providePostDao(db: AppDatabase) = db.postDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: PostRemoteDataSource,
                          localDataSource: PostsDao) =
        PostsRepository(remoteDataSource, localDataSource)

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(userService: UserService) = UserRemoteDataSource(userService)

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideUserRepository(remoteDataSource: UserRemoteDataSource,
                          localDataSource: UsersDao) =
        UserRepository(remoteDataSource, localDataSource)

}