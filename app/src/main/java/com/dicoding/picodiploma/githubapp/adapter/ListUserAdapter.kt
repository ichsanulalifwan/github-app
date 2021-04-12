package com.dicoding.picodiploma.githubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubapp.model.User
import com.dicoding.picodiploma.githubapp.databinding.ItemUserBinding

class ListUserAdapter: RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener
    private val listUser = ArrayList<User>()

    fun setData(list: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding){
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(55, 55))
                    .into(imgAvatar)
                txtUsername.text = user.username
                todoCard.setOnClickListener {
                    onItemClickListener.onItemClicked(user)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(user: User)
    }
}
