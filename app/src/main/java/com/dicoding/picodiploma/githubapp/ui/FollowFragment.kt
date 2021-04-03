package com.dicoding.picodiploma.githubapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubapp.adapter.ListUserAdapter
import com.dicoding.picodiploma.githubapp.databinding.FragmentFollowBinding
import com.dicoding.picodiploma.githubapp.model.User

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var followerListadapter: ListUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followerListadapter = ListUserAdapter()
        followerListadapter.setOnItemClickListener(object : ListUserAdapter.OnItemClickListener{
            override fun onItemClicked(user: User) {
                Toast.makeText(context, "Follower Selected", Toast.LENGTH_SHORT).show()
            }
        })

        showLoading(true)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.apply {
            followRv.layoutManager = LinearLayoutManager(context)
            followRv.adapter = followerListadapter
            followRv.setHasFixedSize(true)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.followProgressBar.visibility = View.VISIBLE
        } else {
            binding.followProgressBar.visibility = View.GONE
        }
    }
}