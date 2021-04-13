package com.dicoding.picodiploma.consumerapp.ui

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.consumerapp.adapter.UserAdapter
import com.dicoding.picodiploma.consumerapp.databinding.FragmentUserListBinding
import com.dicoding.picodiploma.consumerapp.helper.MappingHelper
import com.dicoding.picodiploma.consumerapp.model.User
import com.google.android.material.snackbar.Snackbar

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var userListadapter: UserAdapter

    companion object {

        private const val AUTHORITY = "com.dicoding.picodiploma.githubapp"
        private const val SCHEME = "content"
        private const val TABLE_NAME = "favorite_user_table"

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
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
        userListadapter = UserAdapter()
        loadData()
    }

    private fun setupRecyclerView(list: List<User>) {
        userListadapter.apply {
            setData(list)
            notifyDataSetChanged()
            setOnItemClickListener(object : UserAdapter.OnItemClickListener {
                override fun onItemClicked(user: User) {
                    Toast.makeText(context, "User Selected", Toast.LENGTH_SHORT).show()
                }
            })
        }
        binding.apply {
            userRecyclerView.layoutManager = LinearLayoutManager(activity)
            userRecyclerView.adapter = userListadapter
            userRecyclerView.setHasFixedSize(true)
        }
    }

    private fun loadData() {
        showLoading(true)

        val contentResolver = context?.contentResolver
        val cursor = contentResolver?.query(
            CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val data = MappingHelper.mapCursorToArrayList(cursor)

        if (cursor != null && cursor.count > 0) {
            setupRecyclerView(data)
            showLoading(false)
        } else {
            showLoading(true)
            Snackbar.make(
                binding.userRecyclerView,
                "There's No Favorite User",
                Snackbar.LENGTH_LONG
            )
                .show()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}

