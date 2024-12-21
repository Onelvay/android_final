package com.example.wishlistfinal.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.wishlistfinal.data.model.Book
import com.example.wishlistfinal.databinding.ItemBookBinding

class BooksAdapter(
    private val onAddToWishlist: (Book) -> Unit,
    private val onItemClick: (Book) -> Unit
) : ListAdapter<Book, BooksAdapter.BookViewHolder>(BookDiffCallback()) {

    inner class BookViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }
        
        fun bind(book: Book) {
            binding.apply {
                tvTitle.text = book.title
                tvAuthor.text = book.authors

                // Load image with Glide
                Glide.with(ivThumbnail.context)
                    .load(book.imageUrl.replace("http://", "https://"))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivThumbnail)

                btnAdd.setOnClickListener {
                    onAddToWishlist(book)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
        holder.itemView.setOnClickListener {
            onItemClick(book)
        }
    }
}

class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
        oldItem == newItem
}