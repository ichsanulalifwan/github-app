package com.dicoding.picodiploma.githubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubapp.databinding.ItemUserBinding
import com.dicoding.picodiploma.githubapp.db.entity.FavUser

class FavUserAdapter : RecyclerView.Adapter<FavUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener
    private val listFavUser = ArrayList<FavUser>()

    fun setData(list: List<FavUser>) {
        listFavUser.clear()
        listFavUser.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFavUser[position])
    }

    override fun getItemCount(): Int = listFavUser.size

    inner class ListViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavUser) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(55, 55))
                    .into(imgAvatar)
                txtUsername.text = user.username
                txtName.text = user.name
                txtSite.text = user.location
                todoCard.setOnClickListener {
                    onItemClickListener.onItemClicked(user)
                }
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClicked(user: FavUser)
    }
}