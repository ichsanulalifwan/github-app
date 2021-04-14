package com.dicoding.picodiploma.githubapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubapp.adapter.ListUserAdapter
import com.dicoding.picodiploma.githubapp.databinding.FragmentFollowBinding
import com.dicoding.picodiploma.githubapp.model.User
import com.dicoding.picodiploma.githubapp.viewmodel.FollowViewModel

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var followListadapter: ListUserAdapter
    private lateinit var followViewModel: FollowViewModel

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String?, index: Int): FollowFragment {
            val fragment = FollowFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        followViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowViewModel::class.java)

        if (username != null) {
            index?.let { followViewModel.setFollowList(context, username, it) }
        }

        followViewModel.getFollowList().observe(viewLifecycleOwner, {
            if (it != null) {
                followListadapter.setData(it)
                showLoading(false)
            }
        })

        followListadapter = ListUserAdapter()
        followListadapter.setOnItemClickListener(object : ListUserAdapter.OnItemClickListener {
            override fun onItemClicked(user: User) {
                when (index) {
                    0 -> Toast.makeText(context, "Follower Selected", Toast.LENGTH_SHORT).show()
                    1 -> Toast.makeText(context, "Following Selected", Toast.LENGTH_SHORT).show()
                }
            }
        })

        showLoading(true)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.apply {
            followRv.layoutManager = LinearLayoutManager(context)
            followRv.adapter = followListadapter
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