package com.codechallenge.jennyferlopez.ui.posts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.codechallenge.jennyferlopez.data.entities.Posts
import com.codechallenge.jennyferlopez.databinding.ItemPostBinding

class PostsAdapter(private val listener: PostsItemListener) :
    RecyclerView.Adapter<PostsViewHolder>() {

    interface PostsItemListener {
        fun onClickedPost(postId: Int)
    }

    private val items = ArrayList<Posts>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<Posts>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding: ItemPostBinding =
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) =
        holder.bind(items[position])
}

class PostsViewHolder(
    private val itemBinding: ItemPostBinding,
    private val listener: PostsAdapter.PostsItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var posts: Posts

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    fun bind(item: Posts) {
        this.posts = item

        itemBinding.name.text = item.title
        itemBinding.speciesAndStatus.text = item.body
        itemBinding.imageNotRead.visibility = if (item.id <= 20 && !item.isRead) {
            View.VISIBLE
        } else {
            View.GONE
        }

        Glide.with(itemBinding.root)
            .load("https://ui-avatars.com/api/?name=${item.title.slice(0..2)}")
            .transform(CircleCrop())
            .into(itemBinding.image)

    }

    override fun onClick(v: View?) {
        listener.onClickedPost(posts.id)
    }

}

