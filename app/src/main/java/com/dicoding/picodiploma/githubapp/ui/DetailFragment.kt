package com.dicoding.picodiploma.githubapp.ui

import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.dicoding.picodiploma.githubapp.databinding.ItemDetailBinding
import com.dicoding.picodiploma.githubapp.db.DatabaseContract
import com.dicoding.picodiploma.githubapp.model.User
import com.dicoding.picodiploma.githubapp.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {

    private lateinit var bindingDetail: FragmentDetailBinding
    private lateinit var bindingItem: ItemDetailBinding
    private lateinit var userDetailViewModel: DetailViewModel
    private lateinit var viewPager: ViewPager2
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
        bindingDetail = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return bindingDetail.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        userDetailViewModel.setUserDetail(args.username)

        showLoading(true)
        observeData()
        setupViewPager()
    }

    private fun observeData() {
        userDetailViewModel.getUserDetail().observe(viewLifecycleOwner, { detailuser ->
            if (detailuser != null) {
                bindingItem.apply {
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

    private fun onFabClicked(detailuser: User) {
        var statusFav = false

        setStatusFav(statusFav)

        bindingDetail.fabFav.setOnClickListener {
            val values = ContentValues()

            statusFav = !statusFav

            values.put(DatabaseContract.UserColoumns.USERNAME, detailuser.username)
            values.put(DatabaseContract.UserColoumns.NAME, detailuser.name)
            values.put(DatabaseContract.UserColoumns.LOCATION, detailuser.location)
            values.put(DatabaseContract.UserColoumns.COMPANY, detailuser.company)
            values.put(DatabaseContract.UserColoumns.AVATAR_URL, detailuser.avatar)

            setStatusFav(statusFav)

        }


    }

    private fun setupViewPager() {
        val detailPagerAdapter = DetailPagerAdapter(context as FragmentActivity)
        detailPagerAdapter.username = args.username
        viewPager = bindingDetail.viewPager
        viewPager.adapter = detailPagerAdapter
        val tabs: TabLayout = bindingDetail.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            bindingItem.detailProgressBar.visibility = View.VISIBLE
        } else {
            bindingItem.detailProgressBar.visibility = View.GONE
        }
    }

    private fun setStatusFav(statusFav: Boolean) {
        if (statusFav) {
            bindingDetail.fabFav.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            bindingDetail.fabFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }
}