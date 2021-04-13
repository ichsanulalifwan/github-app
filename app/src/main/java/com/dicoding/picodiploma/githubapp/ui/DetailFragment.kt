package com.dicoding.picodiploma.githubapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubapp.R
import com.dicoding.picodiploma.githubapp.adapter.DetailPagerAdapter
import com.dicoding.picodiploma.githubapp.databinding.FragmentDetailBinding
import com.dicoding.picodiploma.githubapp.model.User
import com.dicoding.picodiploma.githubapp.viewmodel.DetailViewModel
import com.dicoding.picodiploma.githubapp.viewmodel.FavUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var userDetailViewModel: DetailViewModel
    private lateinit var favUserViewModel: FavUserViewModel
    private lateinit var viewPager: ViewPager2
    private val args by navArgs<DetailFragmentArgs>()
    private var favState: Boolean = false

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

        userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)

        this.context?.let { userDetailViewModel.setUserDetail(it, args.username) }

        favUserViewModel = ViewModelProvider(this).get(FavUserViewModel::class.java)
        favUserViewModel.getFavUser(args.username)

        showLoading(true)
        observeData()
        setupViewPager()
        observeDatabase()
    }

    private fun observeData() {
        userDetailViewModel.getUserDetail().observe(viewLifecycleOwner, { detailuser ->
            if (detailuser != null) {
                binding.itemDetail.apply {
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
                onFabClicked(detailuser)
                showLoading(false)
            }
        })
    }

    private fun setupViewPager() {
        val detailPagerAdapter = DetailPagerAdapter(context as FragmentActivity)
        detailPagerAdapter.username = args.username
        viewPager = binding.viewPager
        viewPager.adapter = detailPagerAdapter
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    // progrees bar visibility when data loaded
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.itemDetail.detailProgressBar.visibility = View.VISIBLE
        } else {
            binding.itemDetail.detailProgressBar.visibility = View.GONE
        }
    }

    // Check if user already added in favorite user
    private fun observeDatabase() {
        favUserViewModel.getFavUser(args.username)?.observe(viewLifecycleOwner, { currentUser ->
            if (currentUser != null) {
                favState = true
                setStatusFav(favState)
            } else {
                favState = false
                setStatusFav(favState)
            }
        })
    }

    // insert and delete operations when fab clicked
    private fun onFabClicked(detailuser: User) {
        binding.fabFav.setOnClickListener {
            observeDatabase()

            if (!favState) {
                favUserViewModel.addFavUser(detailuser)
                Toast.makeText(
                    context,
                    "${args.username} Added to Favorite",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                favUserViewModel.deleteFavUser(detailuser)
                Toast.makeText(
                    context,
                    "${args.username} Deleted to Favorite",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    // change fab icon if user added or deleted
    private fun setStatusFav(statusFav: Boolean) {
        when (statusFav) {
            true -> binding.fabFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            false -> binding.fabFav.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
    }
}