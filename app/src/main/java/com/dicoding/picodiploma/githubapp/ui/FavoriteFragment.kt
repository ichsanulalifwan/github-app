package com.dicoding.picodiploma.githubapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubapp.adapter.FavUserAdapter
import com.dicoding.picodiploma.githubapp.databinding.FragmentFavoriteBinding
import com.dicoding.picodiploma.githubapp.viewmodel.FavUserViewModel

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favAdapter: FavUserAdapter
    private lateinit var favViewModel: FavUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favViewModel = ViewModelProvider(this).get(FavUserViewModel::class.java)

        favAdapter = FavUserAdapter()
        favAdapter.notifyDataSetChanged()

        setupRecyclerView()
        observeData()
    }

    private fun observeData() {
        favViewModel.getFavUserList()?.observe(viewLifecycleOwner, { listFavUser ->
            listFavUser?.let {
                favAdapter.setData(listFavUser)
            }
        })
    }

    private fun setupRecyclerView() {
        binding.apply {
            favoriteRv.layoutManager = LinearLayoutManager(activity)
            favoriteRv.adapter = favAdapter
            favoriteRv.setHasFixedSize(true)
        }
    }
}