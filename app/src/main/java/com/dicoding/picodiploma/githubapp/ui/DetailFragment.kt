package com.dicoding.picodiploma.githubapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubapp.R
import com.dicoding.picodiploma.githubapp.databinding.FragmentDetailBinding
import com.dicoding.picodiploma.githubapp.viewmodel.DetailViewModel

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var userDetailViewModel: DetailViewModel
    private val args by navArgs<DetailFragmentArgs>()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_followers,
            R.string.tab_text_following
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        userDetailViewModel.setUserDetail(args.username)

        showLoading(true)
        observeData()
    }

    private fun observeData() {
        userDetailViewModel.getUserDetail().observe(viewLifecycleOwner, { detailuser ->
            if (detailuser != null) {
                binding.apply {
                    Glide.with(this@DetailFragment)
                        .load(detailuser.avatar)
                        .apply(RequestOptions().override(100, 100))
                        .into(imageView)
                    txtName.text = detailuser.name
                    txtUsername.text = detailuser.username
                    txtRepository.text = detailuser.repository
                    txtFollowers.text = detailuser.followers
                    txtFollowing.text = detailuser.following
                    txtLocationD.text = detailuser.location
                    txtCompany.text = detailuser.company
                }
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.detailProgressBar.visibility = View.VISIBLE
        } else {
            binding.detailProgressBar.visibility = View.GONE
        }
    }
}