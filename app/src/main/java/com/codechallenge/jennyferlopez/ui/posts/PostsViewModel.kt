package com.codechallenge.jennyferlopez.ui.posts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.codechallenge.jennyferlopez.data.repository.PostsRepository

class PostsViewModel @ViewModelInject constructor(
    private val repository: PostsRepository
) : ViewModel() {

    val posts = repository.getPosts()
}
