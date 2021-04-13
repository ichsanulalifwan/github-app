package com.dicoding.picodiploma.consumerapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.consumerapp.adapter.UserAdapter
import com.dicoding.picodiploma.consumerapp.databinding.FragmentUserListBinding

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var userListadapter: UserAdapter

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
        userListadapter.notifyDataSetChanged()

        setupRecyclerView()
        observeData()
    }

    private fun setupRecyclerView() {
        binding.apply {
            userRecyclerView.layoutManager = LinearLayoutManager(activity)
            userRecyclerView.adapter = userListadapter
            userRecyclerView.setHasFixedSize(true)
        }
    }

    private fun observeData() {
        //
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}