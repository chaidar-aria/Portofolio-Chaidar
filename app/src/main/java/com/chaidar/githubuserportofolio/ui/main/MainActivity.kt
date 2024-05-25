package com.chaidar.githubuserportofolio.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chaidar.githubuserportofolio.data.response.GithubResponse
import com.chaidar.githubuserportofolio.data.retrofit.ApiConfig
import com.chaidar.githubuserportofolio.databinding.ActivityMainBinding
import com.chaidar.githubuserportofolio.ui.adapter.GithubAdapter
import com.chaidar.githubuserportofolio.ui.favorite.FavoriteActivity
import com.chaidar.githubuserportofolio.ui.setting.SettingActivity
import com.chaidar.githubuserportofolio.ui.setting.SettingPreferences
import com.chaidar.githubuserportofolio.ui.setting.SettingViewModel
import com.chaidar.githubuserportofolio.ui.setting.SettingViewModelFactory
import com.chaidar.githubuserportofolio.ui.setting.dataStore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var settingViewModel: SettingViewModel

    private lateinit var binding: ActivityMainBinding
    private lateinit var rickRecyclerView: RecyclerView
    private var currentQuery: String = "chaidar"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rickRecyclerView = binding.rvCharacter

        val pref = SettingPreferences.getInstance(application.dataStore)
        settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pref)
        )[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.fabFavorite.setOnClickListener {
            val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
            startActivity(intent)
        }

        binding.fabSetting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)

        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        performSearch(currentQuery)
    }


    private fun performSearch(query: String?) {
        binding.progressBar.visibility = View.VISIBLE

        ApiConfig.getService().searchUsers(query ?: "").enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                binding.progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val githubResponse = response.body()
                    val dataRick = githubResponse?.items
                    val githubAdapter = GithubAdapter(this@MainActivity, dataRick)

                    rickRecyclerView.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        setHasFixedSize(true)
                        adapter = githubAdapter
                    }
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

//    private fun applyTheme(isDarkModeActive: Boolean) {
//        val nightMode = if (isDarkModeActive) {
//            AppCompatDelegate.MODE_NIGHT_YES
//        } else {
//            AppCompatDelegate.MODE_NIGHT_NO
//        }
//        AppCompatDelegate.setDefaultNightMode(nightMode)
//    }
//
//
//    override fun onResume() {
//        super.onResume()
//
//        val isDarkModeActive = settingViewModel.getThemeSettings().value ?: false
//        applyTheme(isDarkModeActive)
//    }
}