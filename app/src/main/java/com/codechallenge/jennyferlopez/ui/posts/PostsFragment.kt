package com.codechallenge.jennyferlopez.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codechallenge.jennyferlopez.R
import com.codechallenge.jennyferlopez.databinding.PostsFragmentBinding
import com.codechallenge.jennyferlopez.utils.Constants
import com.codechallenge.jennyferlopez.utils.Resource
import com.codechallenge.jennyferlopez.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment(), PostsAdapter.PostsItemListener {

    private var binding: PostsFragmentBinding by autoCleared()
    private val viewModel: PostsViewModel by viewModels()
    private lateinit var adapter: PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PostsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = PostsAdapter(this)
        binding.postsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.postsRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.posts.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    override fun onClickedPost(postId: Int) {
        findNavController().navigate(
            R.id.action_postsFragment_to_postDetailFragment,
            bundleOf(Constants.ARGUMENT_ID_POST to postId)
        )
    }
}
