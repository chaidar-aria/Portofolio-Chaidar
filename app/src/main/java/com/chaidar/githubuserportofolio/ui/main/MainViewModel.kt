package com.chaidar.githubuserportofolio.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chaidar.githubuserportofolio.database.FavoriteUser
import com.chaidar.githubuserportofolio.repository.FavoriteUserRepository

class MainViewModel(application: Application): ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavoriteUser()
}