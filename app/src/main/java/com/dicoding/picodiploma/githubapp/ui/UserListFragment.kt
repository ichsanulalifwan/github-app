package com.dicoding.picodiploma.githubapp.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubapp.R
import com.dicoding.picodiploma.githubapp.adapter.ListUserAdapter
import com.dicoding.picodiploma.githubapp.databinding.FragmentUserListBinding
import com.dicoding.picodiploma.githubapp.model.User
import com.dicoding.picodiploma.githubapp.viewmodel.UserListViewModel
import com.google.android.material.snackbar.Snackbar

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var userListadapter: ListUserAdapter
    private lateinit var userListViewModel: UserListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                val action = UserListFragmentDirections.actionUserListFragmentToSettingsActivity()
                findNavController().navigate(action)
            }

            R.id.fav_list -> {
                val action = UserListFragmentDirections.actionUserListFragmentToFavoriteFragment()
                findNavController().navigate(action)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userListViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserListViewModel::class.java)

        userListadapter = ListUserAdapter()
        userListadapter.notifyDataSetChanged()

        setupRecyclerView()
        observeData()
        userSearch()
        onItemSelected()
    }

    private fun userSearch() {
        binding.editUserSearch.setOnKeyListener { _, keyCode, event ->
            when {
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    val inputUsername = binding.editUserSearch.text.toString()

                    if (inputUsername.isEmpty()) return@setOnKeyListener true
                    showLoading(true)

                    userListViewModel.setUserList(context, inputUsername)

                    return@setOnKeyListener true
                }
                else -> false
            }
        }

        binding.userSearchField.setEndIconOnClickListener {

            val username = binding.userSearchField.editText?.text.toString()

            if (username.isEmpty()) return@setEndIconOnClickListener
            showLoading(true)

            userListViewModel.setUserList(context, username)
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
            if (listUser != null && listUser.isNotEmpty()) {
                userListadapter.setData(listUser)
                showLoading(false)
            } else {
                Toast.makeText(context, R.string.user_not_found, Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        })
    }

    private fun onItemSelected() {
        userListadapter.setOnItemClickListener(object : ListUserAdapter.OnItemClickListener {
            override fun onItemClicked(user: User) {
                val action = user.username?.let {
                    UserListFragmentDirections.actionUserListFragmentToDetailFragment(it)
                }
                action?.let {
                    findNavController().navigate(it)
                }
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