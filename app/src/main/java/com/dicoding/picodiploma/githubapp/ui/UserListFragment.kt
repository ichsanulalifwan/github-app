package com.dicoding.picodiploma.githubapp.ui

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubapp.adapter.ListUserAdapter
import com.dicoding.picodiploma.githubapp.databinding.FragmentUserListBinding
import com.dicoding.picodiploma.githubapp.viewmodel.UserListViewModel

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var userListadapter: ListUserAdapter
    private lateinit var userListViewModel: UserListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userListViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserListViewModel::class.java)

        userListadapter = ListUserAdapter()
        userListadapter.notifyDataSetChanged()

        setupRecyclerView()
        observeData()

        binding.editUserSearch.setOnKeyListener { _, keyCode, event ->
            when {
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    val inputUsername = binding.editUserSearch.text.toString()

                    if (inputUsername.isEmpty()) return@setOnKeyListener true
                    showLoading(true)

                    userListViewModel.setUserList(inputUsername)

                    return@setOnKeyListener true
                }
                else -> false
            }
        }

        binding.userSearchField.setEndIconOnClickListener {

            val username = binding.userSearchField.editText?.text.toString()

            if (username.isEmpty()) return@setEndIconOnClickListener
            showLoading(true)

            userListViewModel.setUserList(username)
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            userRecyclerView.layoutManager = LinearLayoutManager(activity)
            userRecyclerView.adapter = userListadapter
            userRecyclerView.setHasFixedSize(true)
        }
    }

    private fun observeData() {
        userListViewModel.getUserList().observe(viewLifecycleOwner, { listUser ->
            if (listUser != null) {
                userListadapter.setData(listUser)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}