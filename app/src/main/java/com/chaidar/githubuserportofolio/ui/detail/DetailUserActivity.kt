package com.chaidar.githubuserportofolio.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.chaidar.githubuserportofolio.R
import com.chaidar.githubuserportofolio.data.response.DetailResponse
import com.chaidar.githubuserportofolio.data.retrofit.ApiConfig
import com.chaidar.githubuserportofolio.database.FavoriteRoomDatabase
import com.chaidar.githubuserportofolio.database.FavoriteUser
import com.chaidar.githubuserportofolio.databinding.ActivityDetailUserBinding
import com.chaidar.githubuserportofolio.helper.ViewModelFactory
import com.chaidar.githubuserportofolio.ui.adapter.SectionsPagerAdapter
import com.chaidar.githubuserportofolio.ui.insert.FavoriteUserAddUpdateViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var _activityDetailUserBinding: ActivityDetailUserBinding? = null
    private val binding get() = _activityDetailUserBinding!!

    private lateinit var favoriteUserAddUpdateViewModel: FavoriteUserAddUpdateViewModel
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteUserAddUpdateViewModel = obtainViewModel(this@DetailUserActivity)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val login = intent.getStringExtra("login")

        bundle = Bundle()

        if (login != null) {
            progressBar.visibility = View.VISIBLE
            val service = ApiConfig.getService()
            service.getDetailUser(login).enqueue(object : Callback<DetailResponse> {
                override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        val detailResponse = response.body()

                        setupUI(detailResponse)
                        setupFavoriteButton(detailResponse)
                    } else {
                        // Handle unsuccessful response
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    // Handle failure
                }
            })
        }
    }

    private fun setupUI(detailResponse: DetailResponse?) {
        val ivProfile = findViewById<ImageView>(R.id.ivProfile)
        Glide.with(this@DetailUserActivity).load(detailResponse?.avatarUrl).into(ivProfile)

        val tvNamaDetail = findViewById<TextView>(R.id.rv_namaDetail)
        tvNamaDetail.text = detailResponse?.name

        val tvLoginName = findViewById<TextView>(R.id.rv_loginName)
        tvLoginName.text = "Username: ${detailResponse?.login}"

        val tvFollower = findViewById<TextView>(R.id.rv_follower)
        tvFollower.text = "Follower: ${detailResponse?.followers}"

        val tvFollowing = findViewById<TextView>(R.id.rv_following)
        tvFollowing.text = "Following: ${detailResponse?.following}"

        bundle.putString("keyData", detailResponse?.login)

        val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailUserActivity, bundle)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setupFavoriteButton(detailResponse: DetailResponse?) {
        val isFavoriteObserver = Observer<FavoriteUser?> { favoriteUser ->
            if (favoriteUser == null) {
                // Data null, tampilkan ikon favorite border
                binding.fab.setImageResource(R.drawable.baseline_favorite_border_24)
            } else {
                // Data tidak null, tampilkan ikon favorite full
                binding.fab.setImageResource(R.drawable.baseline_favorite_24)
            }
        }

        val favoriteUserDao = FavoriteRoomDatabase.getDatabase(this).favoriteUserDao()

        detailResponse?.login?.let { login ->
            favoriteUserDao.getFavoriteUserByUsername(login).observe(this@DetailUserActivity, isFavoriteObserver)
        }

        binding.fab.setOnClickListener {
            detailResponse?.login?.let {
                val loginFav = detailResponse.login
                val avatarUrlFav = detailResponse.avatarUrl
                val favoriteUser = FavoriteUser(username = loginFav, avatarUrl = avatarUrlFav)

                val isFavorite = favoriteUserAddUpdateViewModel.getFavoriteUserByUsername(loginFav)

                if (isFavorite) {
                    // User is already a favorite, delete it
                    favoriteUserAddUpdateViewModel.delete(favoriteUser)
                    showToast(getString(R.string.deleted))

                    // Set icon to favorite border
                    binding.fab.setImageResource(R.drawable.baseline_favorite_border_24)
                } else {
                    // User is not a favorite, insert it
                    favoriteUserAddUpdateViewModel.insert(favoriteUser)
                    showToast(getString(R.string.added))

                    // Set icon to favorite full
                    binding.fab.setImageResource(R.drawable.baseline_favorite_24)
                }
            }
        }
    }


    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserAddUpdateViewModel::class.java]
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailUserBinding = null
    }
}