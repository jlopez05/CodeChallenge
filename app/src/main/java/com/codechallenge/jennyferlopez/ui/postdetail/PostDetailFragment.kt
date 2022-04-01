package com.codechallenge.jennyferlopez.ui.postdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.codechallenge.jennyferlopez.data.entities.Posts
import com.codechallenge.jennyferlopez.data.entities.Users
import com.codechallenge.jennyferlopez.databinding.PostDetailFragmentBinding
import com.codechallenge.jennyferlopez.utils.Constants
import com.codechallenge.jennyferlopez.utils.Resource
import com.codechallenge.jennyferlopez.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailFragment : Fragment() {

    private var binding: PostDetailFragmentBinding by autoCleared()
    private val viewModel: PostDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PostDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val idPost = it.getInt(Constants.ARGUMENT_ID_POST)
            viewModel.start(idPost)
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.posts.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindCharacter(it.data!!)
                    binding.progressBar.visibility = View.GONE
                    binding.postCl.visibility = View.VISIBLE
                }
                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.postCl.visibility = View.GONE
                }
            }
        })
        viewModel.user.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { user ->
                        bindUser(user)
                    }
                    binding.progressBar.visibility = View.GONE
                    binding.postCl.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.postCl.visibility = View.GONE
                }
            }
        })
    }

    private fun bindCharacter(posts: Posts) {
        viewModel.findUser(posts.userId)

        posts.isRead = true
        viewModel.changeIsRead(posts)

        binding.textTitle.text = posts.title
        binding.textContent.text = posts.body
    }

    private fun bindUser(user: Users) {
        binding.textAutor.text = user.name
        binding.textEmail.text = user.email
        Glide.with(binding.root)
            .load("https://ui-avatars.com/api/?name=${user.name}")
            .into(binding.image)
    }

}
