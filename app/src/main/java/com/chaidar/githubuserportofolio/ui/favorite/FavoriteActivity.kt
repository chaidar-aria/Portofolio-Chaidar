package com.chaidar.githubuserportofolio.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaidar.githubuserportofolio.database.FavoriteRoomDatabase
import com.chaidar.githubuserportofolio.databinding.ActivityFavoriteBinding
import com.chaidar.githubuserportofolio.ui.adapter.FavoriteAdapter

class FavoriteActivity : AppCompatActivity() {

    private var _activityFavorit: ActivityFavoriteBinding? = null

    private val binding get() = _activityFavorit

    private lateinit var adapter: FavoriteAdapter

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        _activityFavorit = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        progressBar = binding?.progressBar ?: ProgressBar(this)

        adapter = FavoriteAdapter(emptyList())

        binding?.rvCharacterFav?.layoutManager = LinearLayoutManager(this)
        binding?.rvCharacterFav?.setHasFixedSize(true)
        binding?.rvCharacterFav?.adapter = adapter

        val favoriteUserDao = FavoriteRoomDatabase.getDatabase(this).favoriteUserDao()
        favoriteUserDao.getAllFavoriteUser().observe(this) { favoriteUsers ->
            progressBar.visibility = if (favoriteUsers.isEmpty()) View.VISIBLE else View.GONE
            adapter.updateData(favoriteUsers)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavorit = null
    }
}